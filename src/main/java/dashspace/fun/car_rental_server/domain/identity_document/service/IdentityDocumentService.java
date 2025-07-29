package dashspace.fun.car_rental_server.domain.identity_document.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.app.dto.response.PageResponse;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.domain.auth.service.AuthenticationService;
import dashspace.fun.car_rental_server.domain.identity_document.constant.IdentityDocumentStatus;
import dashspace.fun.car_rental_server.domain.identity_document.dto.IdentityDocumentDto;
import dashspace.fun.car_rental_server.domain.identity_document.dto.IdentityDocumentImages;
import dashspace.fun.car_rental_server.domain.identity_document.dto.request.HostRegistrationRequest;
import dashspace.fun.car_rental_server.domain.identity_document.dto.request.IdentityDocumentRejectionRequest;
import dashspace.fun.car_rental_server.domain.identity_document.entity.IdentityDocument;
import dashspace.fun.car_rental_server.domain.identity_document.mapper.IdentityDocumentMapper;
import dashspace.fun.car_rental_server.domain.identity_document.repository.IdentityDocumentRepository;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import dashspace.fun.car_rental_server.infrastructure.service.ImageStorageService;
import dashspace.fun.car_rental_server.infrastructure.util.PaginationUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentityDocumentService {

    private final HostRegistrationVerificationService verificationService;
    private final ImageStorageService imageStorageService;
    private final AuthenticationService authService;

    private final IdentityDocumentMapper mapper;

    private final IdentityDocumentRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public IdentityDocumentDto register(HostRegistrationRequest request,
                                        IdentityDocumentImages images,
                                        Integer userId) {

        validateRequest(request, userId);

        var identityDocument = createIdentityDocument(request, images, userId);
        return mapper.toDto(identityDocument);
    }

    private void validateRequest(HostRegistrationRequest request, Integer userId) {
        validateEmailIsAvailable(request.email(), userId);
        verificationService.validateHostRegistrationOtp(request.email(), request.otpCode());
    }

    private void validateEmailIsAvailable(String email, Integer userId) {
        if (isEmailOwnedByUser(email, userId)) {
            return;
        }
        validateEmailNotInUse(email);
    }

    private boolean isEmailOwnedByUser(String email, Integer userId) {
        return userRepository.existsByEmailAndId(email, userId);
    }

    private void validateEmailNotInUse(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private IdentityDocument createIdentityDocument(HostRegistrationRequest request,
                                                    IdentityDocumentImages images,
                                                    Integer userId) {

        String nationalIdFrontUrl = imageStorageService.upload(images.nationalIdFront());
        String selfieWithNationalIdUrl = imageStorageService.upload(images.selfieWithNationalId());
        User userRef = userRepository.getReferenceById(userId);

        var identityDocument = IdentityDocument.builder()
                .owner(userRef)
                .fullName(request.fullName())
                .nationalIdNumber(request.nationalIdNumber())
                .issueDate(request.issueDate())
                .expiryDate(request.expiryDate())
                .idFrontImageUrl(nationalIdFrontUrl)
                .selfieWithIdUrl(selfieWithNationalIdUrl)
                .build();

        return repository.save(identityDocument);
    }

    public IdentityDocumentDto getIdentityDocument(Integer identityDocumentId) {
        return repository.findById(identityDocumentId)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("identity_document.not_found"));
    }

    @Transactional(readOnly = true)
    public PageResponse<IdentityDocumentDto> getIdentityDocuments(int page, int size, String... sortExpressions) {
        page = PaginationUtil.toZeroBasedPageIndex(page);
        Sort sort = PaginationUtil.buildSort(sortExpressions);

        return findIdentityDocumentsWithSort(page, size, sort);
    }

    private PageResponse<IdentityDocumentDto> findIdentityDocumentsWithSort(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IdentityDocument> documents = repository.findAll(pageable);
        List<IdentityDocumentDto> documentDtos = documents.stream()
                .map(mapper::toDto)
                .toList();

        return PageResponse.<IdentityDocumentDto>builder()
                .items(documentDtos)
                .pageNo(page)
                .pageSize(size)
                .totalPage(documents.getTotalPages())
                .build();
    }

    @Transactional
    public IdentityDocumentDto approveIdentityDocument(Integer identityDocumentId, Integer verifiedById) {
        var verifiedBy = userRepository.getReferenceById(verifiedById);

        var document = tryGetIdentityDocument(identityDocumentId);
        document.setStatus(IdentityDocumentStatus.VERIFIED);
        document.setVerifiedAt(Instant.now());
        document.setVerifiedBy(verifiedBy);
        repository.save(document);

        authService.assignHostRoleToUser(document.getOwner().getId());
        return mapper.toDto(document);
    }

    public IdentityDocumentDto rejectIdentityDocument(Integer identityDocumentId,
                                                      IdentityDocumentRejectionRequest request,
                                                      Integer verifiedById) {

        var verifiedBy = userRepository.getReferenceById(verifiedById);

        var document = tryGetIdentityDocument(identityDocumentId);
        document.setStatus(IdentityDocumentStatus.REJECTED);
        document.setRejectionReason(request.rejectionReason());
        document.setVerifiedAt(Instant.now());
        document.setVerifiedBy(verifiedBy);
        repository.save(document);

        return mapper.toDto(document);
    }

    private IdentityDocument tryGetIdentityDocument(Integer identityDocumentId) {
        return repository.findById(identityDocumentId)
                .orElseThrow(() -> new EntityNotFoundException("identity_document.not_found"));
    }
}
