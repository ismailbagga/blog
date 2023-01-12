package com.ismail.personalblogpost;

import com.ismail.personalblogpost.auth.User;
import com.ismail.personalblogpost.auth.UserRepository;
import com.ismail.personalblogpost.auth.UserRoles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OnBootstrap implements CommandLineRunner {
    private  final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;

    public OnBootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String username = "james" ;

        var user = userRepository.findByUsername(username) ;
        if ( user.isEmpty()) {
            var buildUser = User.builder().username(username)
                    .password(passwordEncoder.encode("master"))
                    .role(UserRoles.ADMIN)
                    .enabled(true)
                    .build() ;
            userRepository.save(buildUser) ;
        }
    }
}
