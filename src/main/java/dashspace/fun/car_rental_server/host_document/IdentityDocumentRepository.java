package dashspace.fun.car_rental_server.host_document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IdentityDocumentRepository extends CrudRepository<IdentityDocument, Integer> {
    Page<IdentityDocument> findAll(Pageable pageable);
}
