package com.ismail.personalblogpost.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component()
public class JwToken {
    @Value("${JWT.secret_key}")
    private String secretKey ;
    @Value("${JWT.access_token_expires_after_in_minute}")
    private Integer accessTokenExpirationTimeInSeconds ;
    @Value("${JWT.refresh_token_expires_after_in_hours}")
    private Integer refreshTokenExpirationTimeInHours ;
    public record TokenContainer(String accessToken , String refreshToken )  {} ;



    public TokenContainer createJwtToken(UserDetails userDetails) {
        var algorithm = Algorithm.HMAC256(secretKey) ;
        var authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String accessToken = JWT.create()
                .withClaim("authorities",authorities)
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * accessTokenExpirationTimeInSeconds))
                .sign(algorithm) ;
        String refreshToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60  * 60  * refreshTokenExpirationTimeInHours))
                .sign(algorithm) ;
        return new TokenContainer(accessToken,refreshToken) ;
    }
}
