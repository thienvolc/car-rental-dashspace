package dashspace.fun.car_rental_server.user;

import dashspace.fun.car_rental_server.auth.request.RenterRegistrationRequest;
import dashspace.fun.car_rental_server.user.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.user.request.DrivingLicenseCredentials;
import dashspace.fun.car_rental_server.user.response.DrivingLicenseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(RenterRegistrationRequest request) {
        return User.builder()
                .email(request.email())
                .username(request.email())
                .passwordHash(this.passwordEncoder.encode(request.password()))
                .build();
    }

    public CreateDrivingLicenseRequest toCreateDrivingLicenseRequest(
            DrivingLicenseCredentials credentials, MultipartFile frontImage,
            MultipartFile backImage) {

        return CreateDrivingLicenseRequest.builder()
                .credentials(credentials)
                .frontImage(frontImage)
                .backImage(backImage)
                .build();
    }

    public DrivingLicense toDrivingLicense(DrivingLicenseCredentials credentials,
                                           String frontImageUrl, String backImageUrl) {

        return DrivingLicense.builder()
                .backImageUrl(backImageUrl)
                .frontImageUrl(frontImageUrl)
                .licenseNumber(credentials.licenseNumber())
                .fullName(credentials.fullName())
                .issueDate(credentials.issueDate())
                .build();
    }

    public DrivingLicenseDto toDrivingLicenseDto(DrivingLicense dl) {
        return DrivingLicenseDto.builder()
                .backImageUrl(dl.getBackImageUrl())
                .frontImageUrl(dl.getFrontImageUrl())
                .licenseNumber(dl.getLicenseNumber())
                .fullName(dl.getFullName())
                .issueDate(dl.getIssueDate())
                .expiryDate(dl.getExpiryDate())
                .status(dl.getStatus())
                .build();
    }
}
