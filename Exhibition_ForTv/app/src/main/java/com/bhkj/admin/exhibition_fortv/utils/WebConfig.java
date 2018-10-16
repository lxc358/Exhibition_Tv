package com.bhkj.admin.exhibition_fortv.utils;

/**
 * Created by Administrator on 2018/10/16.
 */

public class WebConfig {
    private int port;//端口
    private int maxParallels;//最大监听数

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxParallels() {
        return maxParallels;
    }

    public void setMaxParallels(int maxParallels) {
        this.maxParallels = maxParallels;
    }

}
