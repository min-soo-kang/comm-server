package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcOffrSectTraf {
    private String OFFR_SECT_ID;
    private String OFFR_DRCT_NM;
    private String OBU_ENTR_DRCT_NMBR;
    private String IFSC_ID;
    private String CMTR_GRAD_CD;
    private String CMTR_GRAD_NM;
    private int    SPED;
    private int    TRVL_HH;
}
