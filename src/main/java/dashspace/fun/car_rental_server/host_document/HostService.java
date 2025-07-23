package dashspace.fun.car_rental_server.host_document;

import dashspace.fun.car_rental_server.host_document.request.HostRegistrationRequest;
import dashspace.fun.car_rental_server.host_document.response.IdentityDocumentDto;
import lombok.NonNull;

import java.util.List;

public interface HostService {
    void register(Integer userId, HostRegistrationRequest request);

    IdentityDocumentDto getIdentityDocument(@NonNull Integer documentId);
    List<IdentityDocumentDto> getAllIdentityDocumentsWithSortByMultipleColumns(
            int pageNo, int pageSize, String... sortBys);
}
