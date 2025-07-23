package dashspace.fun.car_rental_server.common;

import dashspace.fun.car_rental_server.common.util.I18nUtils;
import jakarta.annotation.Nullable;

public record ApplicationResponse(
        @Nullable Object data,
        String message,
        boolean success
) {

    public static ApplicationResponse success(String messageKey) {
        return success(null, messageKey);
    }

    public static ApplicationResponse success(Object data, String messageKey) {
        return new ApplicationResponse(data, I18nUtils.get(messageKey), true);
    }
}
