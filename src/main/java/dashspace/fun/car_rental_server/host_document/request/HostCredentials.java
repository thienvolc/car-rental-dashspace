package dashspace.fun.car_rental_server.host_document.request;

public record HostCredentials(
        String email,
        String phoneNumber,
        String fullName,
        String nationalIdNumber
) {}
