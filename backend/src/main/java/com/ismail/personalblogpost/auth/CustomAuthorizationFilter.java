package com.ismail.personalblogpost.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final JwToken jwToken;

    public CustomAuthorizationFilter(JwToken jwToken) {
        this.jwToken = jwToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(JwToken.AUTHORIZATION);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return ;
        }
        try {
            var payload  = jwToken.verifyAccessToken(accessToken) ;
            log.info(payload.getClaim("authorities").toString());
            var strAuthorities = payload.getClaim("authorities").asArray(String.class) ;
            var grantedAuthorities =Arrays.stream(strAuthorities).map(SimpleGrantedAuthority::new).toList() ;
            log.info("token payload ->{}",payload);
            var token = new UsernamePasswordAuthenticationToken(payload.getSubject(),null,grantedAuthorities) ;
            SecurityContextHolder.getContext().setAuthentication(token);

        }
        catch (Exception exception) {
            log.info(exception.getMessage());
            log.info(exception.getClass().toString());
            SecurityContextHolder.clearContext();
        }
        finally {
            filterChain.doFilter(request,response);
        }


    }
}
