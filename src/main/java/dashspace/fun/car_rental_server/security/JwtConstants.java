package dashspace.fun.car_rental_server.security;

public abstract class JwtConstants {

    private JwtConstants() {
        /* Prevent instantiation */
    }

    public static final String TOKEN_TYPE = "token_type";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
}
