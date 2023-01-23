package com.ismail.personalblogpost.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component()
public class JwToken {

    public static  final String AUTHORIZATION = "authorization" ;
    public static  final String TOKEN_PREFIX = "Bearer " ;
    public  static final String ACCESS_TOKEN_HEADER = "access_token" ;
    public  static final String REFRESH_TOKEN_COOKIE = "SECURITY" ;
    @Value("${JWT.access_secret_key}")
    private String accessSecret ;
    @Value("${JWT.refresh_secret_key}")
    private String refreshSecret ;
    @Value("${JWT.access_token_expires_after_in_minute}")
    private Integer accessTokenExpirationTimeInSeconds ;
    @Value("${JWT.refresh_token_expires_after_in_hours}")
    private Integer refreshTokenExpirationTimeInHours ;



    public record TokenContainer(String accessToken , String refreshToken )  {} ;



    public TokenContainer createJwtTokens(UserDetails userDetails) {
        var accessToken =  generateAccessToken(userDetails) ;
        var refreshToken = generateRefreshToken(userDetails.getUsername()) ;
        return new TokenContainer(accessToken,refreshToken) ;
    }
    public String generateAccessToken(UserDetails userDetails) {
        var accessAlgorithm = getAccessAlgorithm() ;
        var authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return  TOKEN_PREFIX + JWT.create()
                .withClaim("authorities",authorities)
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * accessTokenExpirationTimeInSeconds))
                .sign(accessAlgorithm) ;
    }
    public String generateRefreshToken(String  username) {
        var refreshAlgorithm = getRefreshAlgorithm() ;
        return  JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 *  accessTokenExpirationTimeInSeconds))
                .sign(refreshAlgorithm) ;
    }
    public DecodedJWT verifyAccessToken(String token) {
        if ( !token.startsWith(TOKEN_PREFIX)) throw  new JWTDecodeException("token must start with bearer") ;
        var verifier = JWT.require(getAccessAlgorithm()).build() ;
        return verifier.verify(token.replace(TOKEN_PREFIX,"")) ;
    }
    public DecodedJWT verifyRefreshToken(String token) {
        var verifier = JWT.require(getRefreshAlgorithm()).build() ;
        return verifier.verify(token) ;
    }
    public Algorithm getAccessAlgorithm() {
        return Algorithm.HMAC256(accessSecret) ;
    }
    public Algorithm getRefreshAlgorithm() {
        return Algorithm.HMAC256(refreshSecret) ;
    }
    public List<GrantedAuthority> decodeAuthoritiesInToken(List<String> authorities) {
        return  null ;
    }
}
