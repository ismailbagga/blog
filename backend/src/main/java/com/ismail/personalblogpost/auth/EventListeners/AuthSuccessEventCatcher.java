package com.ismail.personalblogpost.auth.EventListeners;

import com.ismail.personalblogpost.auth.BruteForcingStopperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthSuccessEventCatcher  implements ApplicationListener<AuthenticationSuccessEvent> {
    private  final BruteForcingStopperService bruteForcingStopperService ;
    public AuthSuccessEventCatcher(BruteForcingStopperService bruteForcingStopperService) {
        this.bruteForcingStopperService = bruteForcingStopperService;
    }


        @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
            String username = event.getAuthentication().getName() ;
            log.debug("username is --> {}",username);

        bruteForcingStopperService.registerLoginSuccess(username);

    }
}


