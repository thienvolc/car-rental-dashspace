package dashspace.fun.car_rental_server.app.service;

import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.app.dto.response.v2.Meta;
import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ResponseFactory {

    private final String appName = "car-rental-server";

    public ResponseDto success(ResponseCode responseCode) {
        var meta = Meta.builder()
                .serviceId(appName)
                .status(responseCode.getCode())
                .message(responseCode.getDefaultMessage())
                .build();
        return new ResponseDto(meta, null);
    }

    public ResponseDto success(Object data) {
        var meta = Meta.builder()
                .serviceId(appName)
                .status(ResponseCode.SUCCESS.getCode())
                .build();
        return new ResponseDto(meta, data);
    }
}
