package dashspace.fun.car_rental_server.infrastructure.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudinaryUtil {

    private static final String URL_PATTERN = "/upload/(?:v\\d+/)?(.+?)\\.[a-zA-Z0-9]+$";

    public static String extractPublicIdFromImageUrl(String url) throws IllegalArgumentException {
        Matcher matcher = Pattern.compile(URL_PATTERN).matcher(url);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid url: " + url);
        }
        return matcher.group(1);
    }
}
