package com.its.dsrc.communication.tcp;

import com.its.dsrc.config.SystemInfo;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServerManager {

    private static final Logger logger = LoggerFactory.getLogger(ServerManager.class);

    private ChannelGroup channelGroup;
    private static ConcurrentHashMap<String, Object> CONNECTION_LIST = new ConcurrentHashMap();

    @PostConstruct
    public void init() {
        logger.info("TCP ServerManager init: {}", getClass().getSimpleName());
        this.channelGroup = (ChannelGroup)new DefaultChannelGroup((EventExecutor) GlobalEventExecutor.INSTANCE);
    }

    public ChannelGroup getChannelGroup() {
        return this.channelGroup;
    }

    public void addChannel(String ipAddress, Object obj) {
        this.CONNECTION_LIST.put(ipAddress, obj);
        logger.info("Add Channel: {}, {} Connections.", ipAddress, this.CONNECTION_LIST.size());
    }

    public void delChannel(String ipAddress) {
        this.CONNECTION_LIST.remove(ipAddress);
        logger.info("Del Channel: {}, {} Connections.", ipAddress, this.CONNECTION_LIST.size());
    }

    public ConcurrentHashMap<String, Object> getConnectionList() {
        return this.CONNECTION_LIST;
    }
}
