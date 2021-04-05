package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voUnitSystStts {
    private String SYST_ID;         //	N	VARCHAR2(30)	N			시스템 ID
    private String UPDT_DT;         //	N	VARCHAR2(14)	Y			갱신 일시
    private String SYST_STTS_CD;    //	N	VARCHAR2(7)	    Y			시스템 상태 코드
}
