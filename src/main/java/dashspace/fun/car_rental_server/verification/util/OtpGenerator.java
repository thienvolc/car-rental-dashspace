package dashspace.fun.car_rental_server.verification.util;

import dashspace.fun.car_rental_server.common.util.I18nUtils;

import java.security.SecureRandom;

public abstract class OtpGenerator {

    private OtpGenerator() {
        /* prevent instantiation */
    }

    private static final int MAX_LENGTH = 10;
    private static final int MIN_LENGTH = 1;

    private static final SecureRandom random = new SecureRandom();

    public static String generate6Digit() {
        return generateFromLength(6);
    }

    public static String generateFromLength(int length) {
        ensureLengthInValidRange(length);

        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    private static void ensureLengthInValidRange(int length) {
        ensureLengthNotTooSmall(length);
        ensureLengthNotTooLarge(length);
    }

    private static void ensureLengthNotTooSmall(int length) {
        if (length < MIN_LENGTH) {
            throw new IllegalArgumentException(I18nUtils.get("otp.length.too_small"));
        }
    }

    private static void ensureLengthNotTooLarge(int length) {
        if (length > MAX_LENGTH) {
            throw new IllegalArgumentException(I18nUtils.get("opt.length.too_large"));
        }
    }
}
