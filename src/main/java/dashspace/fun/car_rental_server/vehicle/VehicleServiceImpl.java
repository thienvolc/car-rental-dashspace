package dashspace.fun.car_rental_server.vehicle;

import dashspace.fun.car_rental_server.common.util.ImageStorageService;
import dashspace.fun.car_rental_server.common.util.PaginationUtils;
import dashspace.fun.car_rental_server.exception.BusinessException;
import dashspace.fun.car_rental_server.exception.ErrorCode;
import dashspace.fun.car_rental_server.location.Location;
import dashspace.fun.car_rental_server.location.LocationDto;
import dashspace.fun.car_rental_server.location.LocationMapper;
import dashspace.fun.car_rental_server.location.LocationRepository;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.user.UserRepository;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.vehicle.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.request.RejectVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.response.AdminViewVehicleDto;
import dashspace.fun.car_rental_server.vehicle.response.SearchVehicleDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static dashspace.fun.car_rental_server.exception.ErrorCode.VEHICLE_ALREADY_APPROVED;
import static dashspace.fun.car_rental_server.exception.ErrorCode.VEHICLE_ALREADY_REJECTED;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleMapper vehicleMapper;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final LocationMapper locationMapper;
    private final ImageStorageService imageStorageService;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public void registerVehicle(Integer userId, CreateVehicleRequest request,
                                @Nullable MultipartFile... vehicleImages) {

        validateLicensePlateAvailability(request.licensePlate());

        User userRef = this.userRepository.getReferenceById(userId);
        Vehicle vehicle = this.vehicleMapper.toVehicle(userRef, request);
        Location location = createVehicleLocation(userRef, vehicle, request.location());

        vehicle.setImages(createVehicleImages(vehicle, vehicleImages));
        vehicle.setLocation(location);

        this.vehicleRepository.save(vehicle);
    }

    private void validateLicensePlateAvailability(String licensePlate) {
        if (this.vehicleRepository.existsByLicensePlate(licensePlate)) {
            throw new BusinessException(ErrorCode.LICENSE_PLATE_ALREADY_EXISTS);
        }
    }

    private Location createVehicleLocation(User userRef, Vehicle vehicleRef,
                                           LocationDto locationDto) {

        Location location = this.locationMapper.toLocation(userRef, vehicleRef,
                locationDto);
        return this.locationRepository.save(location);
    }

    private List<VehicleImage> createVehicleImages(Vehicle vehicleRef,
                                                   @Nullable MultipartFile... images) {

        if (!hasImages(images)) {
            return List.of();
        }

        AtomicInteger order = new AtomicInteger(0);

        return Stream.of(images)
                .filter(img -> img != null && !img.isEmpty())
                .map(img -> VehicleImage.builder()
                        .vehicle(vehicleRef)
                        .imageUrl(this.imageStorageService.upload(img))
                        .sortOrder(order.getAndIncrement())
                        .build())
                .toList();
    }

    private boolean hasImages(@Nullable MultipartFile... vehicleImages) {
        return vehicleImages != null && vehicleImages.length > 0;
    }

    @Override
    public List<AdminViewVehicleDto> getAllAdminViewVehiclesWithSortByMultipleColumns(
            int pageNo, int pageSize, String... sortBys) {

        Sort sort = PaginationUtils.buildSort(sortBys);
        return findAllVehiclesWithSort(pageNo, pageSize, sort).stream()
                .map(this.vehicleMapper::toAdminViewVehicleDto)
                .toList();
    }

    private Page<Vehicle> findAllVehiclesWithSort(int pageNo,
                                                  int pageSize,
                                                  Sort sort) {

        pageNo = PaginationUtils.toZeroBasedPageIndex(pageNo);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return this.vehicleRepository.findAll(pageable);
    }

    @Override
    public AdminViewVehicleDto getAdminViewVehicle(@NonNull Integer vehicleId) {
        return this.vehicleRepository.findById(vehicleId)
                .map(this.vehicleMapper::toAdminViewVehicleDto)
                .orElseThrow(() -> new EntityNotFoundException("vehicle.not_found"));
    }

    @Override
    public List<HostVehicleDto> getAllHostVehicles(int pageNo, int pageSize) {
        return findAllVehicles(pageNo, pageSize).stream()
                .map(this.vehicleMapper::toHostVehicleDto)
                .toList();
    }

    private Page<Vehicle> findAllVehicles(int pageNo, int pageSize) {
        return findAllVehiclesWithSort(pageNo, pageSize, Sort.unsorted());
    }

    @Override
    public HostVehicleDto getHostVehicle(Integer vehicleId) {
        return this.vehicleRepository.findById(vehicleId)
                .map(this.vehicleMapper::toHostVehicleDto)
                .orElseThrow(() -> new EntityNotFoundException("vehicle.not_found"));
    }

    @Override
    public List<SearchVehicleDto> searchAllVehiclesWithSortByMultipleColumns(
            int pageNo, int pageSize, String... sortBys) {

        Sort sort = PaginationUtils.buildSort(sortBys);
        return findAllVehiclesWithSort(pageNo, pageSize, sort).stream()
                .map(this.vehicleMapper::toSearchVehicleDto)
                .toList();
    }

    @Override
    public void approveVehicle(Integer vehicleId, Integer verifiedById) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("vehicle.not_found"));

        if (vehicle.getApprovalStatus() != VehicleApprovalStatus.PENDING) {
            throw new BusinessException(VEHICLE_ALREADY_APPROVED);
        }

        vehicle.setApprovalStatus(VehicleApprovalStatus.APPROVED);
        vehicle.setVerifiedBy(this.userRepository.getReferenceById(verifiedById));
        this.vehicleRepository.save(vehicle);
    }

    @Override
    public void rejectVehicle(Integer vehicleId, Integer verifiedById, RejectVehicleRequest request) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("vehicle.not_found"));

        if (vehicle.getApprovalStatus() != VehicleApprovalStatus.PENDING) {
            throw new BusinessException(VEHICLE_ALREADY_REJECTED);
        }

        vehicle.setApprovalStatus(VehicleApprovalStatus.REJECTED);
        vehicle.setRejectionReason(request.rejectionReason());
        vehicle.setVerifiedBy(this.userRepository.getReferenceById(verifiedById));
        this.vehicleRepository.save(vehicle);
    }
}
