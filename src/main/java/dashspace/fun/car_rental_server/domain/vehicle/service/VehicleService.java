package dashspace.fun.car_rental_server.domain.vehicle.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.app.dto.response.PageResponse;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.domain.location.entity.Location;
import dashspace.fun.car_rental_server.domain.location.service.LocationService;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import dashspace.fun.car_rental_server.domain.vehicle.constant.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.domain.vehicle.dto.ModerationViewVehicleDto;
import dashspace.fun.car_rental_server.domain.vehicle.dto.SearchVehicleDto;
import dashspace.fun.car_rental_server.domain.vehicle.dto.VehicleDto;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.RejectVehicleRequest;
import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import dashspace.fun.car_rental_server.domain.vehicle.entity.VehicleImage;
import dashspace.fun.car_rental_server.domain.vehicle.mapper.VehicleMapper;
import dashspace.fun.car_rental_server.domain.vehicle.repository.VehicleRepository;
import dashspace.fun.car_rental_server.infrastructure.util.PaginationUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static dashspace.fun.car_rental_server.domain.common.constant.ResponseCode.VEHICLE_ALREADY_APPROVED;
import static dashspace.fun.car_rental_server.domain.common.constant.ResponseCode.VEHICLE_ALREADY_REJECTED;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final UserRepository userRepository;

    private final VehicleMapper mapper;
    private final LocationService locationService;
    private final VehicleImageService vehicleImageService;

    @Transactional
    public VehicleDto register(CreateVehicleRequest request,
                               @NonNull MultipartFile[] images,
                               Integer userId) {

        validateLicensePlateIsAvailable(request.licensePlate());

        var vehicle = mapper.toVehicle(request);
        var userRef = userRepository.getReferenceById(userId);
        Location location = locationService.createLocation(request.location(), userId);
        List<VehicleImage> vehicleImages = vehicleImageService.createVehicleImages(vehicle, images);

        vehicle.setOwner(userRef);
        vehicle.setLocation(location);
        vehicle.setImages(vehicleImages);

        repository.save(vehicle);
        return mapper.toDto(vehicle);
    }

    private void validateLicensePlateIsAvailable(String licensePlate) {
        if (repository.existsByLicensePlate(licensePlate)) {
            throw new BusinessException(ResponseCode.LICENSE_PLATE_ALREADY_EXISTS);
        }
    }

    public List<VehicleDto> getAllVehiclesByUserId(Integer userId) {
        var userRef = userRepository.getReferenceById(userId);
        return repository.findByOwner(userRef).stream()
                .map(mapper::toDto)
                .toList();
    }

    public VehicleDto getVehicleById(Integer vehicleId) {
        var vehicle = tryFindVehicleById(vehicleId);
        return mapper.toDto(vehicle);
    }

    public Vehicle tryFindVehicleById(Integer vehicleId) {
        return repository.findById(vehicleId)
                .orElseThrow(() -> new BusinessException(ResponseCode.VEHICLE_NOT_FOUND));
    }

    public PageResponse<ModerationViewVehicleDto> getModerationViewVehicles(int page, int size, String... sortExpressions) {
        page = PaginationUtil.toZeroBasedPageIndex(page);
        Sort sort = PaginationUtil.buildSort(sortExpressions);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Vehicle> vehicles = repository.findAll(pageable);
        List<ModerationViewVehicleDto> vehicleDtos = vehicles.stream()
                .map(mapper::toModerationViewVehicleDto)
                .toList();

        return PageResponse.fromPageAndItems(vehicles, vehicleDtos);
    }

    public ModerationViewVehicleDto getModerationViewVehicle(Integer vehicleId) {
        var vehicle = tryFindVehicleById(vehicleId);
        return mapper.toModerationViewVehicleDto(vehicle);
    }

    public PageResponse<SearchVehicleDto> searchVehicles(int page, int size, String... sortExpressions) {
        page = PaginationUtil.toZeroBasedPageIndex(page);
        Sort sort = PaginationUtil.buildSort(sortExpressions);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Vehicle> vehicles = repository.findAll(pageable);
        List<SearchVehicleDto> vehicleDtos = vehicles.stream()
                .map(mapper::toSearchVehicleDto)
                .toList();

        return PageResponse.fromPageAndItems(vehicles, vehicleDtos);
    }

    public ModerationViewVehicleDto approveVehicle(Integer vehicleId, Integer verifiedById) {
        Vehicle vehicle = tryFindVehicleById(vehicleId);

        if (vehicle.getApprovalStatus() != VehicleApprovalStatus.PENDING) {
            throw new BusinessException(VEHICLE_ALREADY_APPROVED);
        }

        vehicle.setApprovalStatus(VehicleApprovalStatus.APPROVED);
        vehicle.setVerifiedBy(userRepository.getReferenceById(verifiedById));
        repository.save(vehicle);

        return mapper.toModerationViewVehicleDto(vehicle);
    }

    public ModerationViewVehicleDto rejectVehicle(Integer vehicleId, RejectVehicleRequest request, Integer verifiedById) {
        var vehicle = tryFindVehicleById(vehicleId);

        if (vehicle.getApprovalStatus() != VehicleApprovalStatus.PENDING) {
            throw new BusinessException(VEHICLE_ALREADY_REJECTED);
        }

        vehicle.setApprovalStatus(VehicleApprovalStatus.REJECTED);
        vehicle.setRejectionReason(request.rejectionReason());
        vehicle.setVerifiedBy(userRepository.getReferenceById(verifiedById));
        repository.save(vehicle);

        return mapper.toModerationViewVehicleDto(vehicle);
    }

    public boolean isVehicleOwnedByUser(Integer vehicleId, Integer userId) {
        var userRef = userRepository.getReferenceById(userId);
        return repository.existsByOwnerAndId(userRef, vehicleId);
    }
}
