package dashspace.fun.car_rental_server.auth;

import dashspace.fun.car_rental_server.auth.request.AuthenticationRequest;
import dashspace.fun.car_rental_server.auth.request.RefreshRequest;
import dashspace.fun.car_rental_server.auth.request.RenterRegistrationRequest;
import dashspace.fun.car_rental_server.auth.response.AuthenticationResponse;
import dashspace.fun.car_rental_server.auth.request.ClientDeviceInfomation;

public interface AuthenticationService {

    void registerRenter(RenterRegistrationRequest request);

    AuthenticationResponse login(AuthenticationRequest request,
                                 ClientDeviceInfomation deviceInfo);

    AuthenticationResponse refreshToken(RefreshRequest request);

    void logoutAll(Integer userId);
}
