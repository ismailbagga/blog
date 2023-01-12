package com.ismail.personalblogpost.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ismail.personalblogpost.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository ;
    private final  JwToken jwToken ;

    public AuthService(UserRepository userRepository, JwToken jwToken) {
        this.userRepository = userRepository;
        this.jwToken = jwToken;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return  userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("this username %s does not exists".formatted(username))) ;
    }

    public String generateAccessToken(String token ) {
        DecodedJWT payload ;
        try {
            payload = jwToken.verifyRefreshToken(token) ;
        }
        catch (Exception exception) {
            throw  new APIException(exception.getMessage(), HttpStatus.EXPECTATION_FAILED)  ;
        }
        String username = payload.getSubject() ;
        var foundUser = userRepository.findByUsername(username).orElseThrow(()-> new APIException("there no  owner of token",HttpStatus.NOT_FOUND)) ;
        return jwToken.generateAccessToken(foundUser);
    }
}
