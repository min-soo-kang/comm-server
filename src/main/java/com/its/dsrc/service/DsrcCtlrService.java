package com.its.dsrc.service;

import com.its.dsrc.mapper.DsrcCtlrMapper;
import com.its.dsrc.repository.DsrcRepository;
import com.its.dsrc.vo.voDsrcCtlr;
import com.its.dsrc.vo.voDsrcCtlrCntl;
import com.its.dsrc.vo.voDsrcCtlrLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class DsrcCtlrService {

    @Autowired
    private DsrcCtlrMapper dsrcCtlrMapper;

    private int sttsMin;

    @PostConstruct
    private void init() {
        log.info("init");
        loadMaster();
        loadObjNonCrypt();
    }

    @PreDestroy
    public void destroyService() {
        log.error("destroy. system terminated.......");
        updateCtlrStts(false);
    }

    void loadMaster() {
        List<voDsrcCtlr> objList = null;
        try {
            objList = this.dsrcCtlrMapper.selectAll();
            log.info("dsrcCtlrMap.selectAll(), {} EA", objList.size());
            for (voDsrcCtlr unit : objList) {
                unit.setIP_ADDR(unit.getIP_ADDR().trim());  // 빈문자열 삭제
                if (unit.getIP_ADDR() != null && unit.getIP_ADDR().length() > 0) {

                    unit.getStts().setID(unit.getID());
                    unit.getStts().initUnknown();

                    DsrcRepository.getInstance().dsrcCtlrMap.put(unit.getID(), unit);
                    DsrcRepository.getInstance().dsrcCtlrIpMap.put(unit.getIP_ADDR(), unit);
                }
            }
        }
        catch (Exception e) {
            log.error("rseCtlrMapper.selectAll: {}", e);
        }

        for (Map.Entry<String, voDsrcCtlr> obj : DsrcRepository.getInstance().dsrcCtlrMap.entrySet()) {
            log.info(obj.toString());
        }
        /*for (Map.Entry<String, voDsrcCtlr> obj : DsrcRepository.getInstance().dsrcCtlrIpMap.entrySet()) {
            log.debug(obj.toString());
        }*/
    }

    public void loadObjNonCrypt() {
        try {
            DsrcRepository.getInstance().obuNonCryptList.clear();
            DsrcRepository.getInstance().obuNonCryptList = this.dsrcCtlrMapper.selectObuNonCryptList();
        }
        catch (Exception e) {
            log.error("rseCtlrMapper.loadObjNonCrypt: {}", e);
        }

        /*for (voObuNonCrypt vo : RseRepository.getInstance().obuNonCryptList) {
            log.debug(vo.toString());
        }*/
    }

    public void updateCtlrStts(boolean isRun) {
        boolean insHs = false;
        Calendar cal = Calendar.getInstance();
        int min = cal.get(Calendar.MINUTE);
        if ((min % 5) == 0) {
            insHs = true;
            this.sttsMin = min;
        }

    }

    public int insertRseCtlrLogHs(voDsrcCtlrLog obj) {
        return this.dsrcCtlrMapper.insertRseCtlrLogHs(obj);
    }

    public int insertRseCtlrCntlHs(voDsrcCtlrCntl obj) {
        return this.dsrcCtlrMapper.insertRseCtlrCntlHs(obj);
    }
}
