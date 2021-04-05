package com.its.dsrc.security;


import com.its.dsrc.service.UserService;
import com.its.dsrc.vo.voUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {


    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String id = authentication.getName();
        String password = (String) authentication.getCredentials();

        //log.info("Authentication!!!");
        //log.info(password);

        voUser user = new voUser();
        if (user == null)
            throw new BadCredentialsException("Login Error !!");


        //인증안하고 바로 접속

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setAuthorities(roles);
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(id, password, user.getAuthorities());
        result.setDetails(user);
        return result;
    }

    @Override
    public boolean supports(Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
