/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog.persistence.service;

import java.lang.management.ManagementFactory;

import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

/**
 * @author zxc Dec 1, 2016 6:38:04 PM
 */
@SuppressWarnings("restriction")
@Service
public class MonitorSer {

    // List<Visiter> getVisiters();//获取访问人数列表

    // 获取剩余内存百分比
    public int getFreeMemory() {
        OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalVirtualMemory = osmxb.getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double compare = (freePhysicalMemorySize * 1.0 / totalVirtualMemory) * 100;
        return compare.intValue();
    }
}
