package com.its.utils;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public final class NettyUtils {

    public static String getTcpAddress(Channel ch) {
        String localIp = "local-unknown";
        String remoteIp = "remote-unknown";
        int localPort = 0;
        int remotePort = 0;
        InetSocketAddress localAddr = (InetSocketAddress)ch.localAddress();
        if (localAddr != null) {
            localIp = localAddr.getAddress().getHostAddress();
            localPort = localAddr.getPort();
        }

        InetSocketAddress remoteAddr = (InetSocketAddress)ch.remoteAddress();
        if (remoteAddr != null) {
            remoteIp = remoteAddr.getAddress().getHostAddress();
            remotePort = remoteAddr.getPort();
        }
        return "[Local #(" + localIp + ":" + localPort + ") Remote #(" + remoteIp + ":" + remotePort + ")]";
    }
    public static String getRemoteAddress(Channel ch) {
        String ip = getRemoteIpAddress(ch);
        int  port = getRemotePort(ch);
        return "[Remote #(" + ip + ":" + port + ")]";
    }

    public static String getLocalAddress(Channel ch) {
        String ip = getLocalIpAddress(ch);
        int  port = getLocalPort(ch);
        return "[Local #(" + ip + ":" + port + ")]";
    }

    public static String getRemoteIpAddress(Channel ch) {
        String ip = "unknown";
        InetSocketAddress inetAddr = (InetSocketAddress)ch.remoteAddress();
        if (inetAddr != null) {
            ip = inetAddr.getAddress().getHostAddress();
        }
        return ip;
    }

    public static int getRemotePort(Channel ch) {
        int port = 0;
        InetSocketAddress inetAddr = (InetSocketAddress)ch.remoteAddress();
        if (inetAddr != null)
            port = inetAddr.getPort();
        return port;
    }

    public static String getLocalIpAddress(Channel ch) {
        String ip = "unknown";
        InetSocketAddress inetAddr = (InetSocketAddress)ch.localAddress();
        if (inetAddr != null) {
            ip = inetAddr.getAddress().getHostAddress();
        }
        return ip;
    }

    public static int getLocalPort(Channel ch) {
        int port = 0;
        InetSocketAddress inetAddr = (InetSocketAddress)ch.localAddress();
        if (inetAddr != null)
            port = inetAddr.getPort();
        return port;
    }
}
