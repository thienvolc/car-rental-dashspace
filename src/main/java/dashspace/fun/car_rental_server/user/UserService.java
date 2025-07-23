package dashspace.fun.car_rental_server.user;

import dashspace.fun.car_rental_server.user.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.user.request.ResetForgotPasswordRequest;
import dashspace.fun.car_rental_server.user.response.DrivingLicenseDto;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void resetForgotPassword(@Valid ResetForgotPasswordRequest request);
    DrivingLicenseDto createDrivingLicense(Integer userId,
                                           CreateDrivingLicenseRequest request);
}
