package dashspace.fun.car_rental_server.infrastructure.adapter;

import dashspace.fun.car_rental_server.app.dto.geolocation.DistrictDto;
import dashspace.fun.car_rental_server.app.dto.geolocation.ProvinceDto;
import dashspace.fun.car_rental_server.app.dto.geolocation.WardDto;
import dashspace.fun.car_rental_server.infrastructure.adapter.dto.OpenApiDistrictDto;
import dashspace.fun.car_rental_server.infrastructure.adapter.dto.OpenApiProvinceDto;
import dashspace.fun.car_rental_server.infrastructure.config.RestTemplateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VNProvincesOpenApiAdapter {

    private static final String BASE_URL = "https://provinces.open-api.vn/api";
    private static final int DEPTH = 2;

    private final RestTemplateClient restTemplateClient;

    public List<ProvinceDto> getAllProvinces() throws RestClientException {
        List<OpenApiProvinceDto> provinces = restTemplateClient.getList(BASE_URL);

        return provinces.stream()
                .map(p -> new ProvinceDto(p.name(), p.code(), p.divisionType()))
                .toList();
    }

    public List<DistrictDto> getDistrictsByProvince(int provinceCode) throws RestClientException {
        String url = BASE_URL + "/p/" + provinceCode + "?depth=" + DEPTH;
        OpenApiProvinceDto response = restTemplateClient.get(url, OpenApiProvinceDto.class);
        if (response == null) {
            return List.of();
        }

        return response.districts().stream()
                .map(d -> new DistrictDto(d.name(), d.code(), d.divisionType()))
                .toList();
    }

    public List<WardDto> getWardsByDistrictCode(int districtCode) throws RestClientException {
        String url = BASE_URL + "/d/" + districtCode + "?depth=" + DEPTH;
        OpenApiDistrictDto response = restTemplateClient.get(url, OpenApiDistrictDto.class);
        if (response == null) {
            return List.of();
        }

        return response.wards().stream()
                .map(w -> new WardDto(w.name(), w.code()))
                .toList();
    }
}
