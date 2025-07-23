package dashspace.fun.car_rental_server.host_document.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record HostRegistrationRequest(
        MultipartFile nationalIdFront,
        MultipartFile selfieWithNationalId,
        HostCredentials credentials
) {}
