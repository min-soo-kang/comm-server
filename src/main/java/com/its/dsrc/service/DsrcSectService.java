package com.its.dsrc.service;

import com.its.dsrc.config.DsrcServerConfig;
import com.its.dsrc.mapper.DsrcSectMapper;
import com.its.dsrc.repository.DsrcRepository;
import com.its.dsrc.vo.voDsrcObuPassInfo;
import com.its.dsrc.vo.voDsrcSect;
import com.its.dsrc.vo.voDsrcSectPass;
import com.its.dsrc.vo.voDsrcSectTraf;
import com.its.utils.SysUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Service
@Transactional(rollbackFor = {Exception.class})
public class DsrcSectService {

    @Autowired
    private DsrcSectMapper dsrcSectMapper;

    @Autowired
    private DsrcServerConfig dsrcServerConfig;

    // OBU DSRC 차량통과정보, { SECT_ID, [OBU_ID/passinfo, OBU_ID/passinfo, ...] }
    private Map<String, Map<String, voDsrcObuPassInfo>> dsrcObuPassMap = null;
    // OBU Travel Time History, { SECT_ID, [seconds/endpasstime, ...] }
    private Map<String, SortedMap<Integer, String>> travelTimeHistoryMap = null;

    @PostConstruct
    private void init() {
        log.info("init");
        this.dsrcObuPassMap = Collections.synchronizedMap(new ConcurrentHashMap<>());
        this.travelTimeHistoryMap = Collections.synchronizedMap(new ConcurrentHashMap<>());
        loadMaster();
    }

    void loadMaster() {
        List<voDsrcSect> objList = null;
        try {
            objList = this.dsrcSectMapper.selectAll();
            log.info("DsrcSectService.selectAll(), {} EA", objList.size());
            //RseRepository.getInstance().dsrcSectMap.clear();
            for (voDsrcSect obj : objList) {
                DsrcRepository.getInstance().dsrcSectMap.put(obj.getRSE_SECT_ID(), obj);
            }
            setDsrcSectStartMap(objList);
            setDsrcSectEndMap(objList);
        }
        catch (Exception e) {
            log.error("dsrcCtlrMap.selectAll: {}", e);
        }

        // DSRC 통과 정보 저장 맵을 초기화한다.
        initDsrcObuPassMap();

        for (Map.Entry<String, voDsrcSect> obj : DsrcRepository.getInstance().dsrcSectMap.entrySet()) {
            log.info(obj.toString());
        }
    }

    public void setDsrcSectStartMap(List<voDsrcSect> dsrcSectList) {
        if (DsrcRepository.getInstance().dsrcSectStartMap == null) {
            log.error("setDsrcSectStartMap: DsrcRepository.getInstance().dsrcSectStartMap is NULL");
            return;
        }
        if (dsrcSectList == null) {
            log.error("setDsrcSectStartMap: dsrcSectList is NULL");
            return;
        }

        DsrcRepository.getInstance().dsrcSectStartMap.clear();
        for (voDsrcSect vo : dsrcSectList) {
            String startDsrcId = vo.getSTRT_ID();
            String sectId = vo.getRSE_SECT_ID();
            DsrcRepository.getInstance().dsrcSectStartMap.put(startDsrcId, sectId);
        }
        log.info("setDsrcSectStartMap: {}", DsrcRepository.getInstance().dsrcSectStartMap.toString());
    }

    public void setDsrcSectEndMap(List<voDsrcSect> dsrcSectList) {
        if (DsrcRepository.getInstance().dsrcSectEndMap == null) {
            log.error("setDsrcSectEndMap: DsrcRepository.getInstance().dsrcSectEndMap is NULL");
            return;
        }
        if (dsrcSectList == null) {
            log.error("setDsrcSectEndMap: dsrcSectList is NULL");
            return;
        }

        DsrcRepository.getInstance().dsrcSectEndMap.clear();
        for (voDsrcSect vo : dsrcSectList) {
            String endDsrcId = vo.getEND_ID();
            String sectId = vo.getRSE_SECT_ID();
            DsrcRepository.getInstance().dsrcSectEndMap.put(endDsrcId, sectId);
        }
        log.info("setDsrcSectEndMap: {}", DsrcRepository.getInstance().dsrcSectEndMap.toString());
    }

    public void initDsrcObuPassMap() {

        this.dsrcObuPassMap.clear();
        this.travelTimeHistoryMap.clear();
        String currTime = SysUtils.getSysTime();
        for (Map.Entry<String, voDsrcSect> obj : DsrcRepository.getInstance().dsrcSectMap.entrySet()) {
            //<obuId, passInfo>
            Map<String, voDsrcObuPassInfo> temp = Collections.synchronizedMap(new ConcurrentHashMap<>());
            this.dsrcObuPassMap.put(obj.getValue().getRSE_SECT_ID(), temp);
            SortedMap<Integer, String> temp2 = Collections.synchronizedSortedMap(new TreeMap<>());
            temp2.put(Integer.valueOf(240), currTime);
            this.travelTimeHistoryMap.put(obj.getValue().getRSE_SECT_ID(), temp2);
        }
    }

     /**
     * DSRC 구간 소통정보 생성
     * 스케쥴러에 의해 실행됨, 매 5분 정주기
     */
    public void makeDsrcSectTraffic() {

        log.info("makeDsrcSectTraffic: Start...");

        int historyMinute = this.dsrcServerConfig.getFilteringHistoryMinute();
        float minSpeed = (float)this.dsrcServerConfig.getFilteringMinSpeed();
        float maxSpeed = (float)this.dsrcServerConfig.getFilteringMaxSpeed();
        String currentTm = SysUtils.getSysTime();

        // 필터링 초기화(, )OBU 통과 수집 이력 목록 초기화)
        for (Map.Entry<String, voDsrcSect> obj : DsrcRepository.getInstance().dsrcSectMap.entrySet()) {
            String dsrcSectId = obj.getValue().getRSE_SECT_ID();
            log.info("makeDsrcSectTraffic: initFiltering, SECT_ID: {}", dsrcSectId);

            initFiltering(dsrcSectId, historyMinute);
        }

        // OBU 구간 통행정보 목록
        List<voDsrcSectPass> sectPassList = Collections.synchronizedList(new ArrayList<>());
        // 구간 소통정보 목록
        List<voDsrcSectTraf> sectTrafList = Collections.synchronizedList(new ArrayList<>());

        // OBU 구간 통과 목록을 이용하여 교통정보 생성
        for (String dsrcSectId : this.dsrcObuPassMap.keySet()) {
            // 구간 ID로 구간정보 조회
            voDsrcSect dsrcSect = DsrcRepository.getInstance().dsrcSectMap.get(dsrcSectId);
            if (dsrcSect == null) {
                log.error("makeDsrcSectTraffic: SECT_ID: {}, not found", dsrcSectId);
                continue;
            }

            log.info("makeDsrcSectTraffic: SECT_ID: {}", dsrcSectId);

            int distance = 0;
            int totalCount = 0;
            int missMatchCount = 0;
            int totalSeconds = 0;
            int calcCount = 0;
            int avgSpeed = 0;

            // 구간 거리 체크
            distance = dsrcSect.getDSTC();
            if (distance <= 0) {
                log.error("makeDsrcSectTraffic: SECT_ID: {}, distance: {}, distance error", dsrcSectId, distance);
                continue;
            }

            // 구간 통과 차량 목록 조회
            Map<String, voDsrcObuPassInfo> obuPassInfoMap = this.dsrcObuPassMap.get(dsrcSectId);
            if (obuPassInfoMap == null) {
                log.warn("makeDsrcSectTraffic: SECT_ID: {}, not found", dsrcSectId);
                continue;
            }

            // 전체 통과 차량
            totalCount = obuPassInfoMap.size();
            if (totalCount <= 0) {
                log.warn("makeDsrcSectTraffic: SECT_ID: {}, total pass volume zero", dsrcSectId);
                continue;
            }

            log.info("makeDsrcSectTraffic: SECT_ID: {}, pass obu: {} EA", dsrcSectId, totalCount);

            // 구간을 통과한 차량목록을 이용하여 구간 소통정보 생성
            for (String obuId : obuPassInfoMap.keySet()) {
                try {
                    voDsrcObuPassInfo dsrcObuPassInfo = obuPassInfoMap.get(obuId);
                    if (dsrcObuPassInfo == null) {
                        log.error("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, sect passing car get error", dsrcSectId, obuId);
                        missMatchCount++;
                        continue;
                    }

                    // 종점 DSRC 를 통과한 시각이 없으면 맷칭이 안된것임
                    if (dsrcObuPassInfo.getEndDsrcPassTm() == null) {
                        missMatchCount++;
                        if (dsrcObuPassInfo.getStartDsrcPassTm() != null) {
                            // 시점 통과시각과 현재시각이 기준값 이상 차이나면 리스트에서 삭제
                            if (SysUtils.gapTime(dsrcObuPassInfo.getStartDsrcPassTm(), currentTm, Calendar.MINUTE) > historyMinute) {
                                this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);
                            }
                        }
                        log.warn("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, end dsrc pass time null", dsrcSectId, obuId);
                        continue;   // 종료시각이 없으므로 사용하지 않음
                    }

                    // 시점 DSRC 를 통과한 시각이 없으면 DSRC 통과시 정보를 수집 못한것임(리스트에서 삭제)
                    if (dsrcObuPassInfo.getStartDsrcPassTm() == null) {
                        missMatchCount++;
                        this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);
                        log.warn("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, start dsrc pass time null", dsrcSectId, obuId);
                        continue;   // 이상 수집정보로 사용하지 않음
                    }

                    // 시점 및 종점 통과시각이 존재하는 경우 소통정보에 사용
                    int passSeconds = SysUtils.gapTime(dsrcObuPassInfo.getStartDsrcPassTm(), dsrcObuPassInfo.getEndDsrcPassTm(), Calendar.SECOND);
                    if (passSeconds <= 0) {
                        // 구간 운행시각이 이상일 경우 목록에서 삭제
                        missMatchCount++;
                        this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);
                        log.warn("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, start/end passSeconds error: {} sec.", dsrcSectId, obuId, passSeconds);
                        continue;   // 사용하지 않음
                    }

                    // 통행속도 계산(최소/최대 속도값 확인)
                    float speed = (float)((distance * 3600) / (passSeconds * 1000L));
                    if (minSpeed >= speed || maxSpeed <= speed) {
                        missMatchCount++;
                        this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);
                        log.warn("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, calc speed error: {}, {}/{}.", dsrcSectId, obuId, speed, minSpeed, maxSpeed);
                        continue;
                    }

                    if (!doFiltering(dsrcSectId, passSeconds, dsrcObuPassInfo.getEndDsrcPassTm())) {
                        missMatchCount++;
                        this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);
                        log.warn("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, doFiltering failed.", dsrcSectId, obuId);
                        continue;
                    }

                    totalSeconds = (totalSeconds + passSeconds);
                    calcCount++;

                    // 처리된 통과정보를 목록에서 삭제
                    this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);

                    // OBU 구간 통행 정보를 저장
                    voDsrcSectPass passVo = new voDsrcSectPass();
                    passVo.setRSE_SECT_ID(dsrcSectId);
                    passVo.setCRTN_DT(currentTm);
                    passVo.setOBU_IDNT_NMBR(obuId);
                    passVo.setSTRT_SPOT_PASS_DT(dsrcObuPassInfo.getStartDsrcPassTm());
                    passVo.setEND_SPOT_PASS_DT(dsrcObuPassInfo.getEndDsrcPassTm());
                    sectPassList.add(passVo);
                    log.warn("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, totalSeconds: {}, calcCount: {}, passSeconds: {}.", dsrcSectId, obuId, totalSeconds, calcCount, passSeconds);
                }
                catch(Exception e) {
                    missMatchCount++;
                    this.dsrcObuPassMap.get(dsrcSectId).remove(obuId);
                    log.error("makeDsrcSectTraffic: SECT_ID: {}, obuId: {}, {}.", dsrcSectId, obuId, e.toString());
                }
            } // 구간 통과 차량 목록

            if (totalCount <= missMatchCount) {
                log.warn("makeDsrcSectTraffic: SECT_ID: {}, make traffic error, totalObus: {}, missMatchCount: {}.", dsrcSectId, totalCount, missMatchCount);
                continue;
            }

            // 구간을 통과한 모든 OBU를 이용하여 소통정보 생성
            int matchedCount = totalCount - missMatchCount;
            int trvlSeconds = totalSeconds / matchedCount;
            if (matchedCount <= 0) {
                continue;   // 여기들어올일은 없음....
            }
            try {
                float fSpeed = (float)((distance*3600) / (trvlSeconds*1000L));
                avgSpeed = Math.round(fSpeed);
            }
            catch(Exception e) {
                log.error("makeDsrcSectTraffic: SECT_ID: {}, calc speed, {}.", dsrcSectId, e.toString());
                continue;
            }

            // 구간 소통정보 저장
            voDsrcSectTraf sectTraf = new voDsrcSectTraf();
            sectTraf.setRSE_SECT_ID(dsrcSectId);
            sectTraf.setCRTN_DT(currentTm);
            sectTraf.setTFVL(matchedCount);
            sectTraf.setTRVL_SPED(avgSpeed);
            sectTraf.setTRVL_HH(trvlSeconds);
            sectTrafList.add(sectTraf);
        } // 구간 목록

        log.info("makeDsrcSectTraffic: End.....");
    }

    /**
     * 사분위편차를 활용하여 이상치를 제거한다.
     * 사분위편차 및 관리도 모형에 의한 GPS 수집기반 구간통행속도 데이터 이상치 제거방안 연구, 한국ITS학회논문지, 제7권, 제6호 (2008년 12월)
     *
     * 관측값을 순서대로 정렬한 후
     * 25% 위치한 값을 1사분위수(Q1)
     * 75% 위치한 값을 3사분위수(Q3)
     * 3사분위수와 1사분위수의 차이를 사분위편차(IQR)
     * 이상치제거: 1사분위수에서 사분위편차의 1.5배만큼을 뺀 값보다 작거나 3사분위수에서 사분위편차의 1.5배만큼을 더한 값보다 큰 값을 이상치로 판단함
     * 하한 기준값 : Q1 – 1.5ⅹIQR
     * 상한 기준값 : Q3 + 1.5ⅹIQR
     */
    boolean doFiltering(String dsrcSectId, int travelSeconds, String endDateTime) {
        SortedMap<Integer, String> travelHistoryMap = this.travelTimeHistoryMap.get(dsrcSectId);
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        Iterator<Integer> it = travelHistoryMap.keySet().iterator();
        while (it.hasNext()) {
            try {
                int travelTime = ((Integer) it.next()).intValue();
                if (travelTime > 0)
                {
                    list.add(travelTime);
                }
            }
            catch(Exception e) {
                log.error("doFiltering: dsrcSectId: {}, {}", dsrcSectId, e.toString());
            }
        }

        int size = list.size();
        if (size < 3) {
            this.travelTimeHistoryMap.get(dsrcSectId).put(Integer.valueOf(travelSeconds), endDateTime);
            log.warn("doFiltering: dsrcSectId: {}, list size small: {} EA", dsrcSectId, size);
            return false;
        }

        int Q1Index = (int)(size * (1.0F / 4.0F));  // 1사분위 인덱스
        int Q3Index = (int)(size * (3.0F / 4.0F));  // 3사분위 인덱스

        int Q1 = list.get(Q1Index).intValue();      // 1사분위 값
        int Q3 = list.get(Q3Index).intValue();      // 3사분위 값
        int IQR = Math.abs(Q3 - Q1);
        double lowerBoundD = Q1 - 1.5D * IQR;
        double upperBoundD = Q3 + 1.5D * IQR;

        if (lowerBoundD <= 0.0D)
            lowerBoundD = 0;

        long lowerBound = Math.round(lowerBoundD);
        long upperBound = Math.round(upperBoundD);

        this.travelTimeHistoryMap.get(dsrcSectId).put(Integer.valueOf(travelSeconds), endDateTime);

        log.debug("doFiltering: Q1: {}, Q3: {}, IQR: {}, travelSeconds: {}, LOWER: {}, UPPER: {}",
                Integer.valueOf(Q1), Integer.valueOf(Q3), Integer.valueOf(IQR), Integer.valueOf(travelSeconds), Long.valueOf(lowerBound), Long.valueOf(upperBound));

        if (travelSeconds >= lowerBound && travelSeconds <= upperBound) {
            return true;
        }
        return false;
    }

    /**
     * 필터링(이상치제거)를 수행하기 전에 이상치제거 초기화 작업을 수행한다.
     * OBU 통행 이력 맵에서 가공시각 이전 수집된 정보를 모두 삭제한다.
     */
    void initFiltering(String dsrcSectId, int historyMinute) {
        SortedMap<Integer, String> travelHistoryMap = this.travelTimeHistoryMap.get(dsrcSectId);
        SortedMap<Integer, String> tempMap = Collections.synchronizedSortedMap(new TreeMap<>());
        tempMap.putAll(travelHistoryMap);

        String currentTime = SysUtils.getSysTime();
        Iterator<Integer> it = tempMap.keySet().iterator();
        while (it.hasNext()) {
            try {
                int travelTime = ((Integer) it.next()).intValue();
                if (travelTime == 0)
                    continue;

                String endDateTime = tempMap.get(Integer.valueOf(travelTime));
                if (endDateTime == null || endDateTime.equals(""))
                    continue;

                if (SysUtils.gapTime(endDateTime, currentTime, Calendar.MINUTE) > historyMinute) {
                    // 기준시간 이상 된 이력정보를 리스트에서 삭제
                    this.travelTimeHistoryMap.get(dsrcSectId).remove(Integer.valueOf(travelTime));
                }
            }
            catch(Exception e) {
                log.error("initFiltering: dsrcSectId: {}, {}", dsrcSectId, e.toString());
            }
        }
    }
}
