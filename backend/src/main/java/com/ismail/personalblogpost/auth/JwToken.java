package com.ismail.personalblogpost.auth;

import org.springframework.stereotype.Component;

@Component
public class JwToken {
    public record TokenContainer(String accessToken , String refreshToken )  {} ;
}
