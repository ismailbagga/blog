package com.ismail.personalblogpost.config;

import com.ismail.personalblogpost.auth.AuthService;
import com.ismail.personalblogpost.auth.CustomAuthorizationFilter;
import com.ismail.personalblogpost.auth.CustomUsernameAndPasswordAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder() ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomUsernameAndPasswordAuthFilter authFilter,
                                                   CustomAuthorizationFilter authorizationFilter
                                                   ) throws Exception {
        http.csrf().disable() ;
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;

        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated() ;

        http.addFilter(authFilter) ;
        http.addFilterBefore(authorizationFilter,authFilter.getClass()) ;
        return  http.build() ;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       AuthService authService ,
                                                       PasswordEncoder passwordEncoder
    ) throws Exception {
        var builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class) ;
        var provider = new DaoAuthenticationProvider() ;
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authService);
        provider.setHideUserNotFoundExceptions(false);
        return builder.authenticationProvider(provider).parentAuthenticationManager(null).build() ;
    }
}
