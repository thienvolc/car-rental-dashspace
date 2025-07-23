package dashspace.fun.car_rental_server.geolocation;

import dashspace.fun.car_rental_server.geolocation.response.DistrictDto;
import dashspace.fun.car_rental_server.geolocation.response.ProvinceDto;
import dashspace.fun.car_rental_server.geolocation.response.WardDto;

import java.util.List;

public interface GeolocationService {
    List<ProvinceDto> getAllProvinces();
    List<DistrictDto> getDistrictsByProvince(int provinceCode);
    List<WardDto> getWardsByDistrict(int districtCode);
}
