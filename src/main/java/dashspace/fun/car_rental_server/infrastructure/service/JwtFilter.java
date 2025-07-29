package dashspace.fun.car_rental_server.infrastructure.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.infrastructure.constant.DefaultValue;
import dashspace.fun.car_rental_server.infrastructure.constant.SecurityPaths;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (requiresAuthentication(request)) {
            authenticate(request);
        }
        filterChain.doFilter(request, response);
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        String targetPath = request.getServletPath();
        return !SecurityPaths.isPublicPath(targetPath);
    }

    private void authenticate(HttpServletRequest request) {
        String jwt = extractJwt(request);
        String username = jwtService.extractUsername(jwt);
        if (shouldSkipUser(username)) {
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
            setAuthenticationContext(userDetails, request);
        }
    }

    private String extractJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateAuthenticationHeader(authHeader);
        return authHeader.substring(7);
    }

    private void validateAuthenticationHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(DefaultValue.BEARER)) {
            throw new BusinessException(ResponseCode.TOKEN_MISSING);
        }
    }

    private boolean shouldSkipUser(String username) {
        return username == null || SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void setAuthenticationContext(UserDetails userDetails, HttpServletRequest request) {
        AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
