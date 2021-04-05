package com.its.dsrc.communication.tcp;

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ToString
@Component
public class DsrcAsn1Sever extends ServerAsn1Abstract {

    private static final Logger logger = LoggerFactory.getLogger(DsrcAsn1Sever.class);

    @Value("${server.dsrc.bindAddress:127.0.0.1}")
    private String bindAddress;

    @Value("${server.dsrc.bindPort:#{355}}")
    private int    bindPort;

    @Value("${server.dsrc.worker:#{8}}")
    private int    workerThreads;

    @Value("${server.dsrc.accept:#{1}}")
    private int    acceptThreads;

    @Value("${server.dsrc.backlog:#{128}}")
    private int    backlog;

    @Value("${server.dsrc.rcvBuf:#{32768}}")
    private int    rcvBuf;

    @Value("${server.dsrc.sndBuf:#{32768}}")
    private int    sndBuf;

    @PostConstruct
    private void init() {
        logger.info("DsrcAsn1Server init: {}", getClass().getSimpleName());
        //logger.info("DsrcAsn1Server info: bindAddress[{}]", this.bindAddress);
        logger.info("DsrcAsn1Server info: {}", toString());
    }
}
