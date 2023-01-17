package com.ismail.personalblogpost.auth;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "basic_app_user",indexes = {@Index(name = "basic_user_username_idx",columnList = "username")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "basic_app_user_id_sequence")
    @SequenceGenerator(name = "basic_app_user_id_sequence" ,sequenceName = "basic_app_user_id_seq",allocationSize = 1)
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean enabled = false ;
    private int version = 0 ;


    private LocalDateTime unlockedAt ;
    @Enumerated(EnumType.STRING)
    private UserRoles role;


    public User(String username, String password, boolean enabled, UserRoles role) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return unlockedAt == null || unlockedAt.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
