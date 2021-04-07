package org.java.web.security;

import org.java.web.model.Role;
import org.java.web.model.User;
import org.java.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        User user = userService.getUser(login);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        String password = authentication.getCredentials().toString();
        if(!password.equals(user.getPassword())){
            throw new BadCredentialsException("Incorrect password or username!");
        }
        Set<Role> roles = userService.getUser(login).getRoles();
        Set<GrantedAuthority> authorities = new HashSet();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new UsernamePasswordAuthenticationToken(login,user.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
