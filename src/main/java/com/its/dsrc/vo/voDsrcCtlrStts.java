package com.its.dsrc.vo;

import com.its.utils.SysUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class voDsrcCtlrStts {
    private String ID;              //	N	VARCHAR2(3)	    N			아이디
    private String CLCT_DT;         //	N	VARCHAR2(14)	N			수집 일시
    private String CNTL_DEVC_STTS;  //	N	VARCHAR2(7)	    Y			제어 장치 상태
    private String CMNC_STTS;       //	N	VARCHAR2(7)	    Y			통신 상태
    private String ATN_1_STTS;      //	N	VARCHAR2(7)	    Y			안테나 1 상태
    private String ATN_2_STTS;      //	N	VARCHAR2(7)	    Y			안테나 2 상태
    private String ATN_3_STTS;      //	N	VARCHAR2(7)	    Y			안테나 3 상태
    private String ATN_4_STTS;      //	N	VARCHAR2(7)	    Y			안테나 4 상태
    private String ATN_1_MTNS;      //	N	VARCHAR2(7)	    Y			안테나 1 동작
    private String ATN_2_MTNS;      //	N	VARCHAR2(7)	    Y			안테나 2 동작
    private String ATN_3_MTNS;      //	N	VARCHAR2(7)	    Y			안테나 3 동작
    private String ATN_4_MTNS;      //	N	VARCHAR2(7)	    Y			안테나 4 동작

/*
if      (sDevcStts == "0") sDevcStts = "정상";
else if (sDevcStts == "1") sDevcStts = "문열림";
else if (sDevcStts == "2") sDevcStts = "팬동작";
else if (sDevcStts == "3") sDevcStts = "내부온도 이상";
else if (sDevcStts == "4") sDevcStts = "정보 없음";

if      (sCmncStts == "0") sCmncStts = "정상";
else if (sCmncStts == "1") sCmncStts = "장애";
else if (sCmncStts == "2") sCmncStts = "정보 없음";

if      (sAtn1Stts == "0") sAtn1Stts = "정상";
else if (sAtn1Stts == "1") sAtn1Stts = "장애";
else if (sAtn1Stts == "2") sAtn1Stts = "정보 없음";

if      (sAtn1Mtns == "0") sAtn1Mtns = "송출종료";
else if (sAtn1Mtns == "1") sAtn1Mtns = "송출개시";
else if (sAtn1Mtns == "2") sAtn1Mtns = "유지보수 근무개시";
else if (sAtn1Mtns == "3") sAtn1Mtns = "정보 없음";
*/

    public voDsrcCtlrStts() {
        initUnknown();
    }

    public void initUnknown() {
        setCLCT_DT(SysUtils.getSysTime());
        setCNTL_DEVC_STTS("4");
        setCMNC_STTS("2");
        setATN_1_STTS("2");
        setATN_2_STTS("2");
        setATN_3_STTS("2");
        setATN_4_STTS("2");
        setATN_1_MTNS("3");
        setATN_2_MTNS("3");
        setATN_3_MTNS("3");
        setATN_4_MTNS("3");
    }
    public void initError() {
        setCNTL_DEVC_STTS("4");
        setCMNC_STTS("1");
        setATN_1_STTS("1");
        setATN_2_STTS("1");
        setATN_3_STTS("1");
        setATN_4_STTS("1");
        setATN_1_MTNS("3");
        setATN_2_MTNS("3");
        setATN_3_MTNS("3");
        setATN_4_MTNS("3");
    }
    public void initNormal() {
        setCLCT_DT(SysUtils.getSysTime());
        setCNTL_DEVC_STTS("1");
        setCMNC_STTS("0");
        setATN_1_STTS("0");
        setATN_2_STTS("0");
        setATN_3_STTS("0");
        setATN_4_STTS("0");
        setATN_1_MTNS("0");
        setATN_2_MTNS("0");
        setATN_3_MTNS("0");
        setATN_4_MTNS("0");
    }
}
