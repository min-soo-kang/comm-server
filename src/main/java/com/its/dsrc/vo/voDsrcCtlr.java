package com.its.dsrc.vo;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Setter
@ToString
public class voDsrcCtlr {
    private String ID;              //	N	VARCHAR2(3)	    N			아이디
    private String RSE_ID;          //	N	VARCHAR2(30)	N			RSE 아이디
    private String ROAD_SPOT_ID;    //	N	VARCHAR2(30)	N			도로 지점 아이디
    private String LOG_CNNC_ID;     //	N	VARCHAR2(64)	Y			로그 접속 아이디
    private String LOG_CNNC_PW;     //	N	VARCHAR2(64)	Y			로그 접속 비밀번호
    private String ISTL_LCTN_NM;    //	N	VARCHAR2(40)	Y			설치 위치 명
    private String IP_ADDR;         //	N	VARCHAR2(20)	Y			IP
    private int    PORT;            //	N	VARCHAR2(5)	    Y			PORT
    private int    MNFC_CMPY_CD;    //	N	NUMBER(5)	    Y			제조 업체 코드
    private int    CLCT_ABNR_BASI;  //	N	NUMBER(7)	    Y	0		수집 이상 기준

    private voDsrcCtlrStts stts;
    private int 			  netState;
    private boolean           isDupCon;
    private boolean           isDupLogin;
    private String 		      dstIpAddr;
    private Channel           channel;
    private InetSocketAddress cliReq;
    private long              syncTime;
    private String            LOG_ID;
    private ConcurrentHashMap<Integer, Timer> registeredCommandsTimer = null;
    private ConcurrentHashMap<Integer, voDsrcCtlrCntl> userCommands = null;
    private ConcurrentHashMap<String, voDsrcOffrSect> offrSectMap = null;

    public voDsrcCtlr() {

        this.stts = new voDsrcCtlrStts();
        this.registeredCommandsTimer = new ConcurrentHashMap<Integer, Timer>();
        this.userCommands = new ConcurrentHashMap<Integer, voDsrcCtlrCntl>();
        this.offrSectMap = new ConcurrentHashMap<String, voDsrcOffrSect>();

        initNet();
    }

    void initNet() {

        this.netState   = NET.CLOSED;
        this.isDupCon   = false;
        this.isDupLogin = false;
        this.dstIpAddr  = "";
        this.channel    = null;
        this.cliReq     = null;
        this.syncTime   = 0;
    }

    public synchronized boolean addUserCommands(int packetNmbr, voDsrcCtlrCntl command) {

        this.userCommands.put(Integer.valueOf(packetNmbr), command);
        return addCommandTimer(packetNmbr);
    }

    public synchronized boolean removeUserCommands(int packetNmbr) {

        voDsrcCtlrCntl command = this.userCommands.get(Integer.valueOf(packetNmbr));
        if (command != null) {
            this.userCommands.remove(Integer.valueOf(packetNmbr));
            return true;
        }
        return false;
    }

    public synchronized voDsrcCtlrCntl getUserCommands(int packetNmbr) {

        voDsrcCtlrCntl command = this.userCommands.get(Integer.valueOf(packetNmbr));
        return command;
    }

    private boolean addCommandTimer(int packetNmbr) {

        return true;
    }


    public synchronized boolean removeRegisteredCommandsTimer(int packetNmbr) {

        Timer timer = this.registeredCommandsTimer.get(Integer.valueOf(packetNmbr));
        if (timer != null) {
            this.registeredCommandsTimer.remove(Integer.valueOf(packetNmbr));
        }
        return true;
    }

    public synchronized boolean removeRegisteredCommands(int packetNmbr, boolean cancelTimer) {

        Timer timer = this.registeredCommandsTimer.get(Integer.valueOf(packetNmbr));
        if (timer != null) {
            if (cancelTimer)
                timer.cancel();
            this.registeredCommandsTimer.remove(Integer.valueOf(packetNmbr));
        }

        return false;
    }

}
