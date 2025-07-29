package dashspace.fun.car_rental_server.infrastructure.util;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    @SneakyThrows
    public static PrivateKey loadPrivateKey(String pemPath) {
        final String key = readKeyString(pemPath)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    @SneakyThrows
    public static PublicKey loadPublicKey(String pemPath) {
        String key = readKeyString(pemPath)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    private static String readKeyString(String path) throws IOException {
        @Cleanup InputStream is = KeyUtil.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new IllegalArgumentException("Key not found: " + path);
        }
        return new String(is.readAllBytes());
    }
}
