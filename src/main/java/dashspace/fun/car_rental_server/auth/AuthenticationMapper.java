package dashspace.fun.car_rental_server.auth;

import dashspace.fun.car_rental_server.auth.request.AuthenticationRequest;
import dashspace.fun.car_rental_server.auth.request.ClientDeviceInfomation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationMapper {

    public ClientDeviceInfomation mergeHostDeviceInfo(
            AuthenticationRequest request, HttpServletRequest servletRequest) {

        return ClientDeviceInfomation.builder()
                .deviceInfo(request.deviceInfo())
                .ipAddress(servletRequest.getRemoteAddr())
                .userAgent(servletRequest.getHeader("User-Agent"))
                .build();
    }
}
