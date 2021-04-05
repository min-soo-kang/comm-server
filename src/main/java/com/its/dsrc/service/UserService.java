package com.its.dsrc.service;

import com.its.dsrc.vo.voUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("User Detailes!!!");
        log.info("username : "+username);

        //voUser user = DB에서 username가지고 있는지 확인해야한다.
        //무조건
        voUser user = new voUser("admin","{noop}1234");
        if(user==null) {
            throw new UsernameNotFoundException("No user found with "+username + user.getUsername());

        }
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        user.setAuthorities(roles);

        return user;

    }
}
