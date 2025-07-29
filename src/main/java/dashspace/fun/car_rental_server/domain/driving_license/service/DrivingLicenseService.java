package dashspace.fun.car_rental_server.domain.driving_license.service;

import dashspace.fun.car_rental_server.app.dto.response.PageResponse;
import dashspace.fun.car_rental_server.domain.driving_license.constant.DrivingLicenseStatus;
import dashspace.fun.car_rental_server.domain.driving_license.dto.DrivingLicenseDto;
import dashspace.fun.car_rental_server.domain.driving_license.dto.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.domain.driving_license.dto.request.RejectDrivingLicenseRequest;
import dashspace.fun.car_rental_server.domain.driving_license.entity.DrivingLicense;
import dashspace.fun.car_rental_server.domain.driving_license.mapper.DrivingLicenseMapper;
import dashspace.fun.car_rental_server.domain.driving_license.repository.DrivingLicenseRepository;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import dashspace.fun.car_rental_server.infrastructure.service.ImageStorageService;
import dashspace.fun.car_rental_server.infrastructure.util.PaginationUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrivingLicenseService {

    private final ImageStorageService imageStorageService;

    private final DrivingLicenseMapper mapper;

    private final UserRepository userRepository;
    private final DrivingLicenseRepository repository;

    public DrivingLicenseDto createDrivingLicense(CreateDrivingLicenseRequest request) {
        String frontImageUrl = imageStorageService.upload(request.frontImage());
        String backImageUrl = imageStorageService.upload(request.backImage());
        var userRef = userRepository.getReferenceById(request.userId());

        var drivingLicense = mapper.toDrivingLicense(request);
        drivingLicense.setFrontImageUrl(frontImageUrl);
        drivingLicense.setBackImageUrl(backImageUrl);
        drivingLicense.setOwner(userRef);

        repository.save(drivingLicense);
        return mapper.toDto(drivingLicense);
    }

    public PageResponse<DrivingLicenseDto> getAllDrivingLicenses(int page, int size) {
        page = PaginationUtil.toZeroBasedPageIndex(page);
        Pageable pageable = PageRequest.of(page, size);
        Page<DrivingLicense> drivingLicenses = repository.findAll(pageable);
        var drivingLicenseDtos = drivingLicenses.stream()
                .map(mapper::toDto)
                .toList();

        return PageResponse.fromPageAndItems(drivingLicenses, drivingLicenseDtos);
    }

    public DrivingLicenseDto getDrivingLicenseById(Integer drivingLicenseId) {
        var drivingLicense = tryGetDrivingLicenseById(drivingLicenseId);
        return mapper.toDto(drivingLicense);
    }

    public DrivingLicenseDto approveDrivingLicense(Integer drivingLicenseId, Integer verifiedById) {
        var verifiedBy = userRepository.getReferenceById(verifiedById);

        var drivingLicense = tryGetDrivingLicenseById(drivingLicenseId);
        drivingLicense.setStatus(DrivingLicenseStatus.VERIFIED);
        drivingLicense.setVerifiedBy(verifiedBy);

        repository.save(drivingLicense);
        return mapper.toDto(drivingLicense);
    }

    public DrivingLicenseDto rejectDrivingLicense(Integer drivingLicenseId,
                                                  RejectDrivingLicenseRequest request,
                                                  Integer verifiedById) {

        var verifiedBy = userRepository.getReferenceById(verifiedById);

        var drivingLicense = tryGetDrivingLicenseById(drivingLicenseId);
        drivingLicense.setStatus(DrivingLicenseStatus.REJECTED);
        drivingLicense.setVerifiedBy(verifiedBy);
        drivingLicense.setRejectionReason(request.rejectionReason());

        repository.save(drivingLicense);
        return mapper.toDto(drivingLicense);
    }

    public DrivingLicense tryGetDrivingLicenseById(Integer drivingLicenseId) {
        return repository.findById(drivingLicenseId)
                .orElseThrow(() -> new EntityNotFoundException("driving_license.not_found"));
    }
}
