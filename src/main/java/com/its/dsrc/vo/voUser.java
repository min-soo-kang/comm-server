package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class voUser implements UserDetails {
    private String id;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    public voUser(String id,String password){
        this.id = id;
        this.password = password;
    }

    public voUser() {

    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
