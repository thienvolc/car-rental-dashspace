package dashspace.fun.car_rental_server.verification.response;

public record VerificationResponse(
        boolean verified,
        String message
) {
}
