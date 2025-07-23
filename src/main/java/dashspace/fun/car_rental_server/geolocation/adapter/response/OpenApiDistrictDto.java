package dashspace.fun.car_rental_server.geolocation.adapter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenApiDistrictDto(
        String name,
        Integer code,

        @JsonProperty("division_type")
        String divisionType,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<OpenApiWardDto> wards
) {
}
