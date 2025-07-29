package dashspace.fun.car_rental_server.domain.verification.util;

import dashspace.fun.car_rental_server.infrastructure.util.I18nUtil;

import java.security.SecureRandom;

public class OtpGenerator {

    private static final int MAX_LENGTH = 10;
    private static final int MIN_LENGTH = 1;

    private static final SecureRandom random = new SecureRandom();

    public static String generate6Digit() {
        return generateFromLength(6);
    }

    public static String generateFromLength(int length) {
        validateLengthInValidRange(length);

        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    private static void validateLengthInValidRange(int length) {
        if (length < MIN_LENGTH) {
            throw new IllegalArgumentException(I18nUtil.get("otp.length.too_small"));
        }

        if (length > MAX_LENGTH) {
            throw new IllegalArgumentException(I18nUtil.get("opt.length.too_large"));
        }
    }
}
