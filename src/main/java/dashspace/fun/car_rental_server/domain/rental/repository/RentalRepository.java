package dashspace.fun.car_rental_server.domain.rental.repository;

import dashspace.fun.car_rental_server.domain.rental.entity.Rental;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    @SuppressWarnings("NullableProblems")
    @EntityGraph(attributePaths = {"vehicle", "renter"})
    Optional<Rental> findFullById(Integer id);
}
