package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voAuthorized {
    private String accessIpAddrs;
    private int isAuthorized;
}
