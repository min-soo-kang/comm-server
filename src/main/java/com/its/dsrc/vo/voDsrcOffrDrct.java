package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcOffrDrct {
    private String ID;                  //	N	VARCHAR2(3)	    N			아이디
    private String RSE_ID;              //	N	VARCHAR2(30)	N			RSE 아이디
    private String PRE_ID;              //	N	VARCHAR2(3)	    N			아이디
    private String PRE_RSE_ID;          //	N	VARCHAR2(30)	N			RSE 아이디
    private String IXR_DRCT_NUM;        //	N	CHAR(1)	        N			교차로 방향 개수
    private String OBU_ENTR_DRCT_NMBR;  //	N	CHAR(1)	        N			OBU 진입 방향 번호
}
