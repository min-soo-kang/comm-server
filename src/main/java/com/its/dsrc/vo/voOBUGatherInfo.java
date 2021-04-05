package com.its.dsrc.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class voOBUGatherInfo {
    private String ID;              //	N	VARCHAR2(3)	    N			아이디
    private String CLCT_DT;         //	N	VARCHAR2(14)	N			수집 일시
    private String OBU_IDNT_NMBR;   //	N	VARCHAR2(200)	N			OBU 인식 번호
    private String CTYP;            //	N	VARCHAR2(7)	    Y			차종
    private String OBU_KIND;        //	N	VARCHAR2(7)	    Y			OBU 종류
}
