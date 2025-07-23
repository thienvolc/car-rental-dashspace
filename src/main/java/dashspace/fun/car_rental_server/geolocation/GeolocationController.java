package dashspace.fun.car_rental_server.geolocation;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.geolocation.response.DistrictDto;
import dashspace.fun.car_rental_server.geolocation.response.ProvinceDto;
import dashspace.fun.car_rental_server.geolocation.response.WardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geolocations")
@Tag(name = "Vietnam Geolocation")
public class GeolocationController {

    private final GeolocationService geolocationService;

    @Operation(summary = "Get all provinces in Vietnam")
    @GetMapping("/provinces")
    public ResponseEntity<ApplicationResponse> getAllProvinces() {
        List<ProvinceDto> response = this.geolocationService.getAllProvinces();
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "all.provinces.get.success"));
    }

    @Operation(summary = "Get all district in specific province")
    @GetMapping("/districts/{provinceCode}")
    public ResponseEntity<ApplicationResponse> getDistrictsByProvince(
            @PathVariable int provinceCode) {

        List<DistrictDto> response = this.geolocationService.getDistrictsByProvince(
                provinceCode);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "districts.get.success"));
    }

    @Operation(summary = "Get all wards in specific district")
    @GetMapping("/wards/{districtCode}")
    public ResponseEntity<ApplicationResponse> getWardsByDistrict(
            @PathVariable int districtCode) {

        List<WardDto> response = this.geolocationService.getWardsByDistrict(
                districtCode);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "wards.get.success"));
    }
}
