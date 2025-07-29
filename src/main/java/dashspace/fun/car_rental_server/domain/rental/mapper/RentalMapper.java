package dashspace.fun.car_rental_server.domain.rental.mapper;

import dashspace.fun.car_rental_server.domain.rental.dto.response.RentalDto;
import dashspace.fun.car_rental_server.domain.rental.dto.response.RentalStatusResponse;
import dashspace.fun.car_rental_server.domain.rental.entity.Rental;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public RentalDto toDto(Rental rental) {
        return RentalDto.builder()
                .id(rental.getId())
                .requestId(rental.getRentalCode())
                .vehicleId(rental.getVehicle().getId())
                .userId(rental.getRenter().getId())
                .pickupDate(rental.getPickupDate())
                .returnDate(rental.getReturnDate())
                .status(rental.getStatus())
                .pickupLocation(rental.getPickupLocation())
                .returnLocation(rental.getReturnLocation())
                .totalAmount(rental.getTotalAmount())
                .depositAmount(rental.getDepositAmount())
                .note(rental.getNote())
                .build();
    }

    public RentalStatusResponse toRentalStatusResponse(Rental rental) {
        return RentalStatusResponse.builder()
                .rentalId(rental.getId())
                .status(rental.getStatus())
                .userId(rental.getRenter().getId())
                .vehicleId(rental.getVehicle().getId())
                .build();
    }
}
