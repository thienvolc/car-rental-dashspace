package dashspace.fun.car_rental_server.domain.verification.dto.request;

public record VerifyOtpRequest(
        String recepient,
        String code
) {
}
