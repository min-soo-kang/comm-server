package com.its.dsrc.service;

import com.its.dsrc.mapper.DsrcSectMapper;
import com.its.dsrc.mapper.OBUGatherMapper;
import com.its.dsrc.vo.voDsrcSectPass;
import com.its.dsrc.vo.voDsrcSectTraf;
import com.its.dsrc.vo.voOBUGatherInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class OBUGatherService {

    @Autowired
    private OBUGatherMapper obuGatherMapper;
    @Autowired
    private DsrcSectMapper dsrcSectMapper;

    @PostConstruct
    private void init() {
        log.info("init");
        loadMaster();
    }

    void loadMaster() {
    }

    public int insertOBUGatherInfoHs(List<voOBUGatherInfo> list) {
        // TODO: Transaction 으로 처리하자
        for (voOBUGatherInfo vo : list) {
            this.obuGatherMapper.insertOBUGatherInfoHs(vo);
        }
        int jobCnt = list.size();
        list.clear();
        return jobCnt;
    }

    public int insertDsrcSectTraf(List<voDsrcSectTraf> list) {
        // TODO: Transaction 으로 처리하자
        for (voDsrcSectTraf vo : list) {
            this.dsrcSectMapper.insertRseSectTrafHs(vo);
        }
        int jobCnt = list.size();
        list.clear();
        return jobCnt;
    }

    public int insertDsrcSectPassHs(List<voDsrcSectPass> list) {
        // TODO: Transaction 으로 처리하자
        for (voDsrcSectPass vo : list) {
            this.dsrcSectMapper.insertRseSectPassHs(vo);
        }
        int jobCnt = list.size();
        list.clear();
        return jobCnt;
    }

}
