package dashspace.fun.car_rental_server.geolocation.adapter;

import dashspace.fun.car_rental_server.common.util.RestTemplateClient;
import dashspace.fun.car_rental_server.geolocation.adapter.response.OpenApiDistrictDto;
import dashspace.fun.car_rental_server.geolocation.adapter.response.OpenApiProvinceDto;
import dashspace.fun.car_rental_server.geolocation.response.DistrictDto;
import dashspace.fun.car_rental_server.geolocation.response.ProvinceDto;
import dashspace.fun.car_rental_server.geolocation.response.WardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VietNamProvincesOpenApiAdapter {

    private static final String BASE_URL = "https://provinces.open-api.vn/api";
    private static final int DEPTH = 2;

    private final RestTemplateClient restClient;

    public List<ProvinceDto> getAllProvinces() throws RestClientException {
        List<OpenApiProvinceDto> provinces = this.restClient.getList(BASE_URL,
                new ParameterizedTypeReference<>() {});

        return provinces.stream()
                .map(p -> new ProvinceDto(p.name(), p.code(), p.divisionType()))
                .toList();
    }

    public List<DistrictDto> getDistrictsByProvince(int provinceCode)
            throws RestClientException {

        String url = BASE_URL + "/p/" + provinceCode + "?depth=" + DEPTH;
        OpenApiProvinceDto response = this.restClient.get(url, OpenApiProvinceDto.class);

        if (response == null) {
            return List.of();
        }

        return response.districts().stream()
                .map(d -> new DistrictDto(d.name(), d.code(), d.divisionType()))
                .toList();
    }

    public List<WardDto> getWardsByDistrictCode(int districtCode)
            throws RestClientException {

        String url = BASE_URL + "/d/" + districtCode + "?depth=" + DEPTH;
        OpenApiDistrictDto response = this.restClient.get(url, OpenApiDistrictDto.class);

        if (response == null) {
            return List.of();
        }

        return response.wards().stream()
                .map(w -> new WardDto(w.name(), w.code()))
                .toList();
    }
}
