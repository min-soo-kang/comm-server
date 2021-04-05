package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcOffrSect {
    private String OFFR_SECT_ID;        //	N	VARCHAR2(8)	    N			제공 구간 아이디
    private String OFFR_DRCT_NM;        //	N	VARCHAR2(30)	Y			제공 방향 명
    private int    CNGS_BASI_SPED;      //	N	NUMBER(2)	    Y			정체 기준 속도
    private int    DELY_BASI_SPED;      //	N	NUMBER(2)	    Y			지체 기준 속도
    private String ID;                  //	N	VARCHAR2(3)	    N			아이디
    private String OBU_ENTR_DRCT_NMBR;  //	N	CHAR(1)	        N			OBU 진입 방향 번호
    //private String PRE_ID;              //	N	VARCHAR2(3)	    N			이전 아이디
    //private String IXR_DRCT_NUM;        //	N	CHAR(1)	        N			교차로 방향 개수

}
