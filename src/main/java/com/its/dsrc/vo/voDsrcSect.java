package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcSect {
    private String RSE_SECT_ID;     //	N	VARCHAR2(8)	    N			RSE 구간 아이디
    private int    DSTC;            //	N	NUMBER(5)	    Y			거리
    private String RSE_SECT_NM;     //	N	VARCHAR2(30)	Y			RSE 구간 명
    private String STRT_SPOT_NM;    //	N	VARCHAR2(60)	Y			시작 지점 명
    private String END_SPOT_NM;     //	N	VARCHAR2(60)	Y			종료 지점 명
    private String DEL_YN;          //	N	CHAR(1)	        Y	'N'		삭제 여부
    private String STRT_ID;         //	N	VARCHAR2(3)	    N			시작 아이디(TB_RSE_MSTR.ID)
    private String END_ID;          //	N	VARCHAR2(3)	    N			종료 아이디(TB_RSE_MSTR.ID)
    private String STR_RSE_ID;
    private String END_RSE_ID;
}
