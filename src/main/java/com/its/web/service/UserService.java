package com.its.web.service;


import com.its.web.vo.voUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j

@Service
public class UserService implements UserDetailsService {
    @Override
    public voUser loadUserByUsername(String username) throws UsernameNotFoundException {
        //voUser user = DB에서 username가지고 있는지 확인해야한다.
        //무조건
        voUser user = new voUser("admin","{noop}1234");
        if(user==null) {
            throw new UsernameNotFoundException("No user found with "+username + user.getUsername());

        }
        //디비에서 권한 가져와야하는데 고정으로 넣어줌
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        user.setAuthorities(roles);

        return user;

    }
}
