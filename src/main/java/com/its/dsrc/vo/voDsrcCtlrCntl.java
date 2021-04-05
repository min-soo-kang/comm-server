package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcCtlrCntl {
    private String ID;          //	N	VARCHAR2(3)	    N			아이디
    private String CNTL_DT;     //	N	VARCHAR2(14)	N			제어 일시
    private String DEVC_TYPE;   //	N	VARCHAR2(7)	    N			장치 유형
    private String CNTL_TYPE;   //	N	VARCHAR2(7)	    Y			제어 유형
    private String RSPS_TYPE;   //	N	VARCHAR2(7)	    Y			응답 유형
    String           reqIpAddress;
    int              reqPort;
}
