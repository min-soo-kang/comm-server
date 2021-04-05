package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voBroker {
    private String ipAddr;  // 브로커 서버 IP주소
    private int    port;    // 브로커 서버 포트번호
    private String id;      // 브로커 서버 접속 ID
    private String pwd;     // 브로커 서버 접속 패스워드
}
