package com.ismail.personalblogpost.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismail.personalblogpost.exception.ApiExceptionControllerAdvice;
import com.ismail.personalblogpost.exception.ApiExceptionControllerAdvice.ExceptionMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class CustomUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwToken jwToken;

    public CustomUsernameAndPasswordAuthFilter(AuthenticationManager authenticationManager, JwToken jwToken) {
        super(authenticationManager);

        super.setFilterProcessesUrl("/api/v1/auth/login");
        super.setPostOnly(true);
        this.authenticationManager = authenticationManager;
        this.jwToken = jwToken;
    }


    public record LoginDto(String username, String password) {
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginDto mapper;
        try {
            mapper = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("username or password was not sent");
        }

        var token = new UsernamePasswordAuthenticationToken(mapper.username(), mapper.password());
        return authenticationManager.authenticate(token);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var principal = (UserDetails) authResult.getPrincipal();
        var tokens = jwToken.createJwtTokens(principal);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info(failed.getMessage());
        log.info(failed.getClass().toString());
//        if( failed instanceof BadCredentialsException) {
//            bruteForcingStopperService.registerLoginFailure();
//        }
        if (failed instanceof LockedException) {
            response.setStatus(HttpStatus.LOCKED.value());
            var message = new ExceptionMessage(
                    "this user us locked for a day after many login attempts",
                    failed.getCause(),
                    HttpStatus.LOCKED,
                    ZonedDateTime.now());
            new ObjectMapper().writeValue(response.getOutputStream(),message);
            return;
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
