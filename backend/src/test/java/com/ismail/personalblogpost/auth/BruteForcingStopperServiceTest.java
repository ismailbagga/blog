package com.ismail.personalblogpost.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness =  Strictness.LENIENT)
class BruteForcingStopperServiceTest {
    @Mock
    Clock clock ;
    @Mock UserRepository userRepository ;
    @InjectMocks
    BruteForcingStopperService bruteForcingStopperService ;
    User user  = User.builder().username("james").enabled(true).password("123").id(1L)
            .build();
    private static ZonedDateTime NOW = ZonedDateTime.now() ;
    @BeforeEach
    void setUp() {
        when(clock.getZone()).thenReturn(NOW.getZone()) ;
        when(clock.instant()).thenReturn(NOW.toInstant()) ;
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user)) ;
    }

    @Test
    void registerLoginSuccess() {
    }

//            ZonedDateTime.of(
//            2023,6,15,12,30,30,0, ZoneId.of("GMT")
//    )  ;
    @Test
    void registerMoreThen10Times() {
        assertNull(user.getUnlockedAt());
        loginAttemptNTimes(10);
        assertNotNull(user.getUnlockedAt());
        assertEquals(NOW.toLocalDateTime().plusDays(1),user.getUnlockedAt());

    }
    @Test
    void registerLessThen10Times() {
        assertNull(user.getUnlockedAt());
        loginAttemptNTimes(9);
        assertNull(user.getUnlockedAt());

    }

    @Test
    void loginAfter2DaysFromLastLoginAttempts() {
        var before_2_days = ZonedDateTime.of(
                2023,6,15,12,30,30,0, ZoneId.of("GMT")
        )  ;

        when(clock.getZone()).thenReturn(before_2_days.getZone()) ;
        when(clock.instant()).thenReturn(before_2_days.toInstant()) ;
        loginAttemptNTimes(9);
        assertNull(user.getUnlockedAt());
        var after_2_days = ZonedDateTime.of(
                2023,6,17,12,30,31,0, ZoneId.of("GMT")
        )  ;
        when(clock.getZone()).thenReturn(after_2_days.getZone()) ;
        when(clock.instant()).thenReturn(after_2_days.toInstant()) ;
        loginAttemptNTimes(9);
        assertNull(user.getUnlockedAt());


    }
    public  void loginAttemptNTimes(int n ) {
        for (int i = 0 ; i< n ; i++ ) {
            bruteForcingStopperService.registerLoginFailure(user.getUsername());
        }
    }
}