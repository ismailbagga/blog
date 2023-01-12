package com.ismail.personalblogpost.auth;

import com.ismail.personalblogpost.exception.APIException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/refresh")
    public void getAccessTokenFromRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        var cookies = request.getCookies();
        var refreshCookie = Arrays.stream(cookies)
                .filter((cookie -> cookie.getName().equals(JwToken.REFRESH_TOKEN_COOKIE)))
                .findFirst();
        if (refreshCookie.isEmpty()) {
            throw new APIException("refresh token cookie was not  found", HttpStatus.BAD_REQUEST);
        }
        log.info(refreshCookie.get().toString());
        var refreshToken = refreshCookie.get().getValue();
        log.info(refreshToken);
        response.setHeader(JwToken.AUTHORIZATION, authService.generateAccessToken(refreshToken));
    }
}
