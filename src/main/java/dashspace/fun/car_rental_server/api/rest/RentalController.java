package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.app.util.RequestUtil;
import dashspace.fun.car_rental_server.domain.rental.dto.request.RentalRequest;
import dashspace.fun.car_rental_server.domain.rental.service.RentalService;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Rental Management")
public class RentalController {

    private final RentalService service;
    private final ResponseFactory responseFactory;

    @Operation(summary = "Rent a vehicle")
    @PostMapping("/rentals")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto rent(@Valid @RequestBody RentalRequest request,
                            HttpServletRequest httpServletRequest,
                            @AuthenticationPrincipal User currentUser) {

        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        var response = service.rent(request, currentUser.getId(), ipAddress);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get rentals status")
    @GetMapping("/rentals/{rentalId}/status")
    public ResponseDto getRentalStatus(@PathVariable Integer rentalId) {
        var response = service.getRentalStatus(rentalId);
        return responseFactory.success(response);
    }
}
