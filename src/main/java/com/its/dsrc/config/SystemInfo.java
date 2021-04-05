package com.its.dsrc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Getter
@Setter
@ToString
@Configuration
public class SystemInfo {

    @Autowired
    private Environment env;

    private String activeProfiles;
    private String osName;
    private int    cpuCores;
    private String hostName;
    private String ipAddress;

    @PostConstruct
    private void init() {
        log.info("init");

        setActiveProfiles(Arrays.toString((Object[])this.env.getActiveProfiles()));
        setOsName(System.getProperty("os.name"));
        setCpuCores(Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        setHostName(getLocalHostName());
        setIpAddress(getLocalIpAddress());

        log.info("init.end. {}", toString());
    }
    private String getLocalHostName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("{}", e.getMessage(), e.getStackTrace());
        }
        return hostName;
    }

    private String getLocalIpAddress() {
        List<String> ipAddresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                for (InterfaceAddress ifAddr : ((NetworkInterface)e.nextElement()).getInterfaceAddresses()) {
                    if (!(ifAddr.getAddress() instanceof java.net.Inet6Address))
                        ipAddresses.add(ifAddr.getAddress().getHostAddress());
                }
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e.getStackTrace());
        }
        return ipAddresses.toString();
    }

    public List<String> getIpAddressesList() {
        List<String> ipAddresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                for (InterfaceAddress ifAddr : ((NetworkInterface)e.nextElement()).getInterfaceAddresses()) {
                    if (!(ifAddr.getAddress() instanceof java.net.Inet6Address))
                        ipAddresses.add(ifAddr.getAddress().getHostAddress());
                }
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e.getStackTrace());
        }
        return ipAddresses;
    }
}
