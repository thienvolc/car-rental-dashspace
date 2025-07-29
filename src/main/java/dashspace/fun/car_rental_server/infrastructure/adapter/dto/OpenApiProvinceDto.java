package dashspace.fun.car_rental_server.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenApiProvinceDto(
        String name,
        Integer code,

        @JsonProperty("division_type")
        String divisionType,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<OpenApiDistrictDto> districts
) {
}
