package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class voDsrcCtlrLog {

    public static int LOG_TYPE_LOGIN = 0;
    public static int LOG_TYPE_LOGOUT = 1;
    public static int LOG_TYPE_INVALID_USER = 2;
    public static int LOG_TYPE_OTHER = 3;

    private String ID;          //	N	VARCHAR2(3)	    N			아이디
    private String CLCT_DT;     //	N	VARCHAR2(14)	N			수집 일시
    private String LOG_TYPE;    //	N	VARCHAR2(7)	    Y			로그 유형
    private String LOG_ID;      //	N	VARCHAR2(64)	Y			로그 ID
    private String LOG_ADDRESS; //	N	VARCHAR2(20)	Y			로그 ADDRESS

}
