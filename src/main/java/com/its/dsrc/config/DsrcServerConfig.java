package com.its.dsrc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Getter
@Setter
@ToString
@Configuration
//@PropertySource({ "classpath:persistence-mysql.properties" })
public class DsrcServerConfig {

    @Value("${process.id:DSRC01}")
    private String processId;

    @Value("${process.name:DSRC}")
    private String processName;

    @Value("${dsrc.server.centerAddress:0.0.0.0}")
    private String centerAddress;
    @Value("${dsrc.server.centerPort:#{4602}}")
    private int    centerPort;

    @Value("${dsrc.server.bindAddress:0.0.0.0}")
    private String bindAddress;
    @Value("${dsrc.server.bindPort:#{355}}")
    private int    bindPort;

    @Value("${dsrc.server.workerThreads:#{8}}")
    private int    workerThreads;
    @Value("${dsrc.server.acceptThreads:#{1}}")
    private int    acceptThreads;
    @Value("${dsrc.server.backlog:#{128}}")
    private int    backlog;

    @Value("${dsrc.server.rcvBuf:#{32768}}")
    private int    rcvBuf;
    @Value("${dsrc.server.sndBuf:#{32768}}")
    private int    sndBuf;

    @Value("${dsrc.server.readerIdleTime:#{0}}")
    private int    readerIdleTime;
    @Value("${dsrc.server.writerIdleTime:#{0}}")
    private int    writerIdleTime;
    @Value("${dsrc.server.allIdleTime:#{0}}")
    private int    allIdleTime;
    @Value("${dsrc.server.connectTimeoutMillis:#{0}}")
    private int    connectTimeoutMillis;

    @Value("${dsrc.server.dataThreads:#{20}}")
    private int    dataThreads;

    @Value("${dsrc.server.senderText:gmutis}")
    private String senderText;

    @Value("${dsrc.publication.nonCryptObu:false}")
    private String publicationNonCryptObu;

    @Value("${dsrc.publication.antenna:0}")
    private int publicationAntenna;

    @Value("${dsrc.subscriptoin.status:false}")
    private String subscriptionStatus;

    @Value("${dsrc.subscription.traffic:false}")
    private String subscriptionTraffic;

    @Value("${dsrc.subscription.status.cycle:#{30}}")
    private int    subscriptionStatusCycle;

    @Value("${dsrc.server.center.dump:false}")
    private String centerDump;
    @Value("${dsrc.server.dsrc.recv.dump:false}")
    private String dsrcRecvDump;
    @Value("${dsrc.server.dsrc.send.dump:false}")
    private String dsrcSendDump;

    @Value("${dsrc.filtering.minSpeed:#{3}}")
    private int    filteringMinSpeed;
    @Value("${dsrc.filtering.maxSpeed:#{140}}")
    private int    filteringMaxSpeed;
    @Value("${dsrc.filtering.historyMinute:#{60}}")
    private int    filteringHistoryMinute;
    @Value("${dsrc.filtering.algorithm:#{1}}")
    private int    filteringAlgorithm;

    @PostConstruct
    private void init() {
        log.info("init");
        if (this.backlog< 64) this.backlog = 64;
        if (this.backlog == 0) this.backlog = 64;

        log.info("init.end. {}", toString());
    }
}
