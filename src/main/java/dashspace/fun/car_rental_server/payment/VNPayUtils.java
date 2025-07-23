package dashspace.fun.car_rental_server.payment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class VNPayUtils {

    private VNPayUtils() {
        /* Prevent instantiation */
    }

    private static final Random random = new Random();

    public static String getRandomNumber(int length) {
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < length; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }

    public static String hmacSHA512(String hashSecret, String data) {
        SecretKeySpec secretKey = new SecretKeySpec(hashSecret.getBytes(UTF_8), "HmacSHA512");
        Mac mac;

        try {
            mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] hmacBytes = mac.doFinal(data.getBytes(UTF_8));
        return bytesToHex(hmacBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b & 0xFF));
        }
        return hexString.toString();
    }
}
