package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.domain.geolocation.service.VNGeolocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geolocations")
@Tag(name = "Vietnam Geolocation")
public class GeolocationController {

    private final VNGeolocationService service;
    private final ResponseFactory responseFactory;

    @Operation(summary = "Get all provinces in Vietnam")
    @GetMapping("/provinces")
    public ResponseDto getAllProvinces() {
        var response = service.getAllProvinces();
        return responseFactory.success(response);
    }

    @Operation(summary = "Get all district in specific province")
    @GetMapping("/districts/{provinceCode}")
    public ResponseDto getDistrictsByProvince(@PathVariable int provinceCode) {
        var response = service.getDistrictsByProvince(provinceCode);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get all wards in specific district")
    @GetMapping("/wards/{districtCode}")
    public ResponseDto getWardsByDistrict(@PathVariable int districtCode) {
        var response = service.getWardsByDistrict(districtCode);
        return responseFactory.success(response);
    }
}
