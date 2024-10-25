package com.example.userservice.security.services;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialNonExpired;
    private boolean enabled;

    private List<GrantedAuthority>grantedAuthorities;

    public CustomUserDetail(User user){

        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        this.accountNonExpired= true;
        this.accountNonLocked = true;
        this.credentialNonExpired = true;
        this.enabled = true;
        this.grantedAuthorities = new ArrayList<>();

        for(Role role: user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
