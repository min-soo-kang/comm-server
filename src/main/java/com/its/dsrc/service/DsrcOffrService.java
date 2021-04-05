package com.its.dsrc.service;

import com.its.dsrc.mapper.DsrcOffrMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class DsrcOffrService {

    @Autowired
    private DsrcOffrMapper rseOffrMapper;

    @PostConstruct
    private void init() {
        log.info("init");
        loadMaster();
    }

    void loadMaster() {

    }
}
