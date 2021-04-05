package com.its.dsrc.repository;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.its.dsrc.vo.voDsrcCtlr;
import com.its.dsrc.vo.voDsrcOffrDrct;
import com.its.dsrc.vo.voDsrcSect;
import com.its.dsrc.vo.voObuNonCrypt;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DsrcRepository {
    private static DsrcRepository _instance = null;

    public ConcurrentHashMap<String, voDsrcCtlr> dsrcCtlrMap = null;
    public ConcurrentHashMap<String, voDsrcCtlr> dsrcCtlrIpMap = null;
    public ConcurrentHashMap<Channel, voDsrcCtlr> rseCtlrChannelMap = null;

    // DSRC_SECT 정보관리, DSRC Sect 관리(sectid, start_rsd_id, end_rse_id, ...)
    public ConcurrentHashMap<String, voDsrcSect> dsrcSectMap = null;
    // 시작 DSRC를 키로 하는 SECT_ID 목록, { STRT_ID, [SECT_ID, SECT_ID, ...] }
    public Multimap<String, String> dsrcSectStartMap = null;
    // 종료 DSRC를 키로 하는 SECT_ID 목록, { END_ID, [SECT_ID, SECT_ID, ...] }
    public Multimap<String, String> dsrcSectEndMap = null;

    public List<voObuNonCrypt> obuNonCryptList = null;
    public List<voDsrcOffrDrct> rseOffrDrctList = null;
    public Date bootingDate= null;

    public static DsrcRepository getInstance() {
        if (_instance == null) {
            synchronized (DsrcRepository.class) {
                if (_instance == null)
                    _instance = new DsrcRepository();
            }
        }
        return _instance;
    }

    public DsrcRepository() {
        this.dsrcCtlrMap = new ConcurrentHashMap<>();
        this.dsrcCtlrIpMap = new ConcurrentHashMap<>();
        this.rseCtlrChannelMap = new ConcurrentHashMap<>();
        this.dsrcSectMap = new ConcurrentHashMap<>();
        this.dsrcSectStartMap = Multimaps.synchronizedMultimap((Multimap)HashMultimap.create());
        this.dsrcSectEndMap = Multimaps.synchronizedMultimap((Multimap)HashMultimap.create());
        this.obuNonCryptList = Collections.synchronizedList(new ArrayList<>());
        this.rseOffrDrctList = Collections.synchronizedList(new ArrayList<>());
        this.bootingDate = new Date();

    }

    public List<voDsrcOffrDrct> getRseOffrDrctList(String rseId) {
        List<voDsrcOffrDrct> list = Collections.synchronizedList(new ArrayList<>());
        for (voDsrcOffrDrct vo : this.rseOffrDrctList) {
            if (vo.getRSE_ID().equals(rseId))
                list.add(vo);
        }
        return list;
    }
}
