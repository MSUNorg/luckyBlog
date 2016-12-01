package com.msun.luckyBlog.persistence.service;

import java.lang.management.ManagementFactory;

import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

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
