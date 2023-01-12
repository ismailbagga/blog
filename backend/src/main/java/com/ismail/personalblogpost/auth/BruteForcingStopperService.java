package com.ismail.personalblogpost.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BruteForcingStopperService {

    private record LoginAttempt(int count, LocalDateTime date) {
    }

    ;

    private final UserRepository userRepository;
    private final int cacheMaxLimit = 1000;

    private final ConcurrentHashMap<String, LoginAttempt> attemptsCache = new ConcurrentHashMap<>();
    private final Clock clock;

    public BruteForcingStopperService(UserRepository userRepository, Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public void registerLoginSuccess(String username) {
        attemptsCache.remove(username);
    }

    @Transactional
    public void registerLoginFailure(String username) {
        var attempt = attemptsCache.get(username);
//                        If Last Attempt Was more than  2 days ago reset counter
        if (attempt == null || attempt.date.isBefore(LocalDateTime.now(clock).minusDays(2))) {
            attemptsCache.put(username, new LoginAttempt(1, LocalDateTime.now(clock)));
            return;
        }
        int maxLoggingAttempts = 10;
        if (attempt.count + 1 >= maxLoggingAttempts) {
            var result = userRepository.findByUsername(username);
            if (result.isEmpty()) return;
            var user = result.get();
            user.setUnlockedAt(LocalDateTime.now(clock).plusDays(1));
//            Remove all Login Attempts of this user
            attemptsCache.remove(username);

            return;
        }

        attemptsCache.put(username, new LoginAttempt(attempt.count() + 1, LocalDateTime.now(clock)));
    }

}
