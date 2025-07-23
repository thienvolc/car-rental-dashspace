package dashspace.fun.car_rental_server.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CloudinaryUtils {

    private CloudinaryUtils() {
        /* Prevent instantiation */
    }

    private static final Pattern CLOUDINARY_PUBLIC_ID_PATTERN = Pattern.compile(
            "/upload/(?:v\\d+/)?(.+?)\\.[a-zA-Z0-9]+$");

    public static String tryExtractPublicIdFromImageUrl(String url)
            throws IllegalArgumentException {

        Matcher matcher = CLOUDINARY_PUBLIC_ID_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Invalid url: " + url);
    }
}
