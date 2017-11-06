package com.brewery.services.auth.session;

import com.brewery.admin.auth.User;
import com.brewery.exceptions.JwtAuthentiacationException;
import com.brewery.services.auth.user.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component(value = "customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = customUserDetailService.getUserDetailsByUserName(username);

        if(user == null || !user.getUsername().equalsIgnoreCase(username)){
            throw new JwtAuthentiacationException("User with specified name not found!");
        }

        if(!password.equals(user.getPassword())){
            throw new JwtAuthentiacationException("Wrong user password!");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
