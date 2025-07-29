package dashspace.fun.car_rental_server.domain.geolocation.service;

import dashspace.fun.car_rental_server.app.dto.geolocation.DistrictDto;
import dashspace.fun.car_rental_server.app.dto.geolocation.ProvinceDto;
import dashspace.fun.car_rental_server.app.dto.geolocation.WardDto;
import dashspace.fun.car_rental_server.infrastructure.adapter.VNProvincesOpenApiAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VNGeolocationService {

    private final VNProvincesOpenApiAdapter adapter;

    public List<ProvinceDto> getAllProvinces() {
        return adapter.getAllProvinces();
    }

    public List<DistrictDto> getDistrictsByProvince(int provinceCode) {
        return adapter.getDistrictsByProvince(provinceCode);
    }

    public List<WardDto> getWardsByDistrict(int districtCode) {
        return adapter.getWardsByDistrictCode(districtCode);
    }
}
