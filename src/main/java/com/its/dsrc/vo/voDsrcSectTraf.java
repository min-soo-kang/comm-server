package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcSectTraf {
    private String RSE_SECT_ID; //	N	VARCHAR2(8)	    N			RSE 구간 아이디
    private String CRTN_DT;     //	N	VARCHAR2(14)	N			생성 일시
    private int    TFVL;        //	N	NUMBER(6)	    Y	0		교통량
    private int    TRVL_SPED;   //	N	NUMBER(3)	    Y	0		통행 속도
    private int    TRVL_HH;     //	N	NUMBER(6)	    Y	0		통행 시간
}
