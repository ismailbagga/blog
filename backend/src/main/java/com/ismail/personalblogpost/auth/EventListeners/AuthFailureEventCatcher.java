package com.ismail.personalblogpost.auth.EventListeners;

import com.ismail.personalblogpost.auth.BruteForcingStopperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthFailureEventCatcher implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final BruteForcingStopperService bruteForcingStopperService;

    public AuthFailureEventCatcher(BruteForcingStopperService bruteForcingStopperService) {
        this.bruteForcingStopperService = bruteForcingStopperService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        var username = event.getAuthentication().getName();
        log.debug("username is --> {}", username);
        log.debug("Exception Class  -> {}", event.getException().toString());
        if (event.getException() instanceof BadCredentialsException) {
            bruteForcingStopperService.registerLoginFailure(username);
            return;
        }
        log.debug("not credentials exception");
    }
}
