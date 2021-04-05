package com.its.dsrc.service;

import com.its.dsrc.config.DsrcServerConfig;
import com.its.dsrc.mapper.UnitSystMapper;
import com.its.dsrc.vo.voUnitSyst;
import com.its.dsrc.vo.voUnitSystStts;
import com.its.utils.SysUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Service
@Transactional(rollbackFor = {Exception.class})
public class UnitSystService {

    @Autowired
    private UnitSystMapper unitSystMapper;
    @Autowired
    private DsrcServerConfig dsrcServerConfig;

    private voUnitSyst unitSyst = new voUnitSyst();
    private voUnitSystStts unitSystStts = new voUnitSystStts();
    private ConcurrentHashMap<String, voUnitSyst> untiSystMap = new ConcurrentHashMap<String, voUnitSyst>();

    private String processId;
    private int sttsMin;

    @PostConstruct
    private void init() {
        this.processId = this.dsrcServerConfig.getProcessId();
        log.info("init. processId: {}", this.dsrcServerConfig.getProcessId());
        loadMaster();
        updateUnitSyst(true);
    }

    @PreDestroy
    public void destroyService() {
        log.error("destroy. system terminated.......");
        updateUnitSyst(false);
    }

    public void loadMaster() {
        List<voUnitSyst> objList = null;
        try {
            objList = this.unitSystMapper.selectAll();
            log.info("unitSystMapper.selectAll(), {} EA", objList.size());
            for (voUnitSyst unit : objList) {
                if (unit.getSYST_ID().equals(this.processId)) {
                    this.unitSystStts.setSYST_ID(this.processId);
                    /*String localIp = unit.getSYST_IP_1().trim();
                    long ipAddr = SysUtils.ipToLong(localIp);
                    UnitSystManager.srcIpAddr = String.format("%03d.%03d.%03d.%03d-", (ipAddr>>24)&0xFF, (ipAddr>>16)&0xFF, (ipAddr>>8)&0xFF, (ipAddr>>0)&0xFF);
                    log.info("loadMaster: {}", UnitSystManager.srcIpAddr);*/
                }

                if (unit.getSYST_TYPE().contentEquals("UOT")) {
                    unit.setSYST_IP_1(unit.getSYST_IP_1().trim());
                    this.untiSystMap.put(unit.getSYST_ID(), unit);
                }
            }
        }
        catch (Exception e) {
            log.error("unitSystMapper.selectAll: {}", e);
        }

        for (Map.Entry<String, voUnitSyst> e : this.untiSystMap.entrySet()) {
            log.info(e.toString());
        }
    }

    public void updateUnitSyst(boolean isRun) {
        boolean insHs = false;
        Calendar cal = Calendar.getInstance();
        int min = cal.get(Calendar.MINUTE);
        if ((min % 5) == 0) {
            insHs = true;
            this.sttsMin = min;
        }

        if (this.unitSystStts == null) {
            this.unitSystStts = new voUnitSystStts();
        }

        String SYST_STTS_CD = isRun ? "SPS1" : "SPC3";
        this.unitSystStts.setSYST_ID(this.processId);
        this.unitSystStts.setUPDT_DT(SysUtils.getSysTime());
        this.unitSystStts.setSYST_STTS_CD(SYST_STTS_CD);

        int res = this.unitSystMapper.updateUnitSystStts(this.unitSystStts);
        log.info("unitSystMapper.updateUnitSystStts: {} EA", res);

        if (insHs) {
            res = this.unitSystMapper.insertUnitSystSttsHs(this.unitSystStts);
            log.info("unitSystMapper.insertUnitSystSttsHs: {} EA", res);

            loadMaster();
        }
    }
}
