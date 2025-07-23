package dashspace.fun.car_rental_server.host_document;

import dashspace.fun.car_rental_server.common.util.ImageStorageService;
import dashspace.fun.car_rental_server.common.util.PaginationUtils;
import dashspace.fun.car_rental_server.exception.BusinessException;
import dashspace.fun.car_rental_server.host_document.request.HostCredentials;
import dashspace.fun.car_rental_server.host_document.request.HostRegistrationRequest;
import dashspace.fun.car_rental_server.host_document.response.IdentityDocumentDto;
import dashspace.fun.car_rental_server.role.Role;
import dashspace.fun.car_rental_server.role.RoleName;
import dashspace.fun.car_rental_server.role.RoleRepository;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.user.UserRepository;
import dashspace.fun.car_rental_server.verification.VerificationService;
import dashspace.fun.car_rental_server.common.util.message.contact.Channel;
import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dashspace.fun.car_rental_server.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {
    private final UserRepository userRepository;
    private final VerificationService verificationService;
    private final HostMapper hostMapper;
    private final IdentityDocumentRepository identityDocumentRepository;
    private final ImageStorageService imageStorageService;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void register(Integer userId, HostRegistrationRequest request) {
        validateEmailAvailibity(userId, request.credentials().email());
        validateHostRegistrationVerification(request.credentials());
        createNewIdentityDocument(userId, request);
        assignHostRoleToUser(userId);
    }

    @Override
    public IdentityDocumentDto getIdentityDocument(@NonNull Integer documentId) {
        return this.identityDocumentRepository.findById(documentId)
                .map(this.hostMapper::toIdentityDocumentDto)
                .orElseThrow(() -> new EntityNotFoundException("identity.document.not_found"));
    }

    private void validateEmailAvailibity(Integer userId, String email) {
        if (!verifyEmailOwnership(userId, email)) {
            validateEmailNotInUse(email);
        }
    }

    private boolean verifyEmailOwnership(Integer userId, String email) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(EXISTING_USER_NOT_FOUND));
        return user.getEmail().equals(email);
    }

    private void validateEmailNotInUse(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateHostRegistrationVerification(HostCredentials credentials) {
        MessageContact target = new MessageContact(credentials.email(), Channel.EMAIL);
        if (!this.verificationService.isHostRegistrationOtpValid(target)) {
            throw new BusinessException(EMAIL_NOT_VERIFIED);
        }
    }

    private void createNewIdentityDocument(Integer userId,
                                           HostRegistrationRequest request) {

        User userRef = this.userRepository.getReferenceById(userId);
        String nationalIdFrontUrl = this.imageStorageService.upload(
                request.nationalIdFront());
        String selfieWithNationalIdUrl = this.imageStorageService.upload(
                request.selfieWithNationalId());
        IdentityDocument doc = this.hostMapper.toIdentityDocument(userRef,
                request.credentials(), nationalIdFrontUrl, selfieWithNationalIdUrl);
        identityDocumentRepository.save(doc);
    }

    private void assignHostRoleToUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(EXISTING_USER_NOT_FOUND));
        Role userRole = this.roleRepository.findByName(RoleName.HOST.name())
                .orElseThrow(() -> new EntityNotFoundException("role.host.not_found"));
        user.setRoles(List.of(userRole));
        userRole.setUsers(List.of(user));
        this.roleRepository.save(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IdentityDocumentDto> getAllIdentityDocumentsWithSortByMultipleColumns(
            int pageNo, int pageSize, String... sortBys) {

        Sort sort = PaginationUtils.buildSort(sortBys);
        return findAllIdentityDocumentsWithSort(pageNo, pageSize, sort);
    }

    private List<IdentityDocumentDto> findAllIdentityDocumentsWithSort(int pageNo,
                                                                       int pageSize,
                                                                       Sort sort) {

        pageNo = PaginationUtils.toZeroBasedPageIndex(pageNo);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<IdentityDocument> docs = this.identityDocumentRepository.findAll(pageable);
        return docs.stream()
                .map(this.hostMapper::toIdentityDocumentDto)
                .toList();

    }
}
