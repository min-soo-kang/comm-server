package com.its.dsrc.communication.tcp;

import com.its.dsrc.config.SystemInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@ToString
public abstract class ServerAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ServerAbstract.class);

    @Autowired
    SystemInfo systemInfo;

/*
    protected String eventName;
    protected String bindAddress;
    protected int    bindPort;
    protected int    workerThreads;
    protected int    acceptThreads;
    protected int    backlog;
    protected int    rcvBuf;
    protected int    sndBuf;
*/

    protected int getWorkerThreads() {
        return this.systemInfo.getCpuCores() * 2;
    }

    protected int getAcceptThreads() {
        int acceptThreads = getWorkerThreads() / 4;
        if (acceptThreads == 0) acceptThreads = 1;
        return acceptThreads;
    }
}
