package dashspace.fun.car_rental_server.infrastructure.constant;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

public class SecurityPaths {

    private static final AntPathMatcher matcher = new AntPathMatcher();
    public static final String[] PUBLIC_PATHS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**",

            "/actuator/**",

            "/verification/**",
            "/auth/login",
            "/auth/register",
            "/auth/refresh",

            "/driving-licenses/me",

            "/geolocations/**",
    };
    public static final String[] USER_PATHS = {
            "/auth/logout/all",

            "/hosts/register"
    };
    public static final String[] HOST_PATHS = {
            "/vehicles/me",
    };
    public static final String[] ADMIN_PATHS = {
            "/vehicles/**",
            "/driving-licenses/**",
            "/users/**",
            "/hosts/**",
            "/admin/**",
            "/reports/**",
            "/datacenter/**",
    };

    public static boolean isPublicPath(String path) {
        return Arrays.stream(SecurityPaths.PUBLIC_PATHS)
                .anyMatch(pattern -> SecurityPaths.matcher.match(pattern, path));
    }
}
