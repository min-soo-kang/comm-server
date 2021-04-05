package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voUnitSyst {
    private String SYST_ID;     //	N	VARCHAR2(30)	N			시스템 ID
    private String SYST_TYPE;   //	N	VARCHAR2(7)	    Y			시스템 유형
    private String SYST_NM;     //	N	VARCHAR2(100)	Y			시스템 명
    private String SYST_IP_1;   //	N	VARCHAR2(20)	Y			시스템 IP_1
    private String SYST_IP_2;   //	N	VARCHAR2(20)	Y			시스템 IP_2
    private int PRGM_PORT;      //	N	VARCHAR2(5)	    Y			프로그램 포트
}
