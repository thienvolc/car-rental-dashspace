package dashspace.fun.car_rental_server.verification.request;

public record VerifyOtpRequest(
        String recepient,
        String code
) {
}
