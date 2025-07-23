package dashspace.fun.car_rental_server.geolocation.impl;

import dashspace.fun.car_rental_server.geolocation.GeolocationService;
import dashspace.fun.car_rental_server.geolocation.adapter.VietNamProvincesOpenApiAdapter;
import dashspace.fun.car_rental_server.geolocation.response.DistrictDto;
import dashspace.fun.car_rental_server.geolocation.response.ProvinceDto;
import dashspace.fun.car_rental_server.geolocation.response.WardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VnGeolocationService implements GeolocationService {

    private final VietNamProvincesOpenApiAdapter adapter;

    @Override
    public List<ProvinceDto> getAllProvinces() {
        return this.adapter.getAllProvinces();
    }

    @Override
    public List<DistrictDto> getDistrictsByProvince(int provinceCode) {
        return this.adapter.getDistrictsByProvince(provinceCode);
    }

    @Override
    public List<WardDto> getWardsByDistrict(int districtCode) {
        return this.adapter.getWardsByDistrictCode(districtCode);
    }
}
