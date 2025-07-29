package dashspace.fun.car_rental_server.domain.identity_document.repository;

import dashspace.fun.car_rental_server.domain.identity_document.entity.IdentityDocument;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IdentityDocumentRepository extends CrudRepository<IdentityDocument, Integer> {
    Page<IdentityDocument> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"owner"})
    Optional<IdentityDocument> findById(@NonNull Integer id);
}
