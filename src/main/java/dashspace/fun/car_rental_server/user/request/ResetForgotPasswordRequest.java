package dashspace.fun.car_rental_server.user.request;


public record ResetForgotPasswordRequest(
        String email,
        String newPassword,
        String otpCode
) {}
