package dashspace.fun.car_rental_server.security;

import dashspace.fun.car_rental_server.exception.BusinessException;
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

import static dashspace.fun.car_rental_server.exception.ErrorCode.TOKEN_MISSING;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtHandler jwtHandler;
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
        String jwt = tryExtractJwt(request);
        String username = this.jwtHandler.extractUsername(jwt);

        if (shouldSkip(username)) {
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (this.jwtHandler.isTokenValid(jwt, userDetails.getUsername())) {
            setAuthenticationContext(userDetails, request);
        }
    }

    private String tryExtractJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new BusinessException(TOKEN_MISSING);
    }

    private boolean shouldSkip(String username) {
        return username == null ||
                SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void setAuthenticationContext(UserDetails userDetails,
                                          HttpServletRequest request) {

        AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
