package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcSectPass {
    private String RSE_SECT_ID;         //	N	VARCHAR2(8)	    N			RSE 구간 아이디
    private String CRTN_DT;             //	N	VARCHAR2(14)	N			생성 일시
    private String OBU_IDNT_NMBR;       //	N	VARCHAR2(200)	N			OBU 인식 번호
    private String STRT_SPOT_PASS_DT;   //	N	VARCHAR2(14)	N			시작 지점 통과 일시
    private String END_SPOT_PASS_DT;    //	N	VARCHAR2(14)	N			종료 지점 통과 일시
}
