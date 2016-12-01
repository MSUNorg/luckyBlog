package com.msun.luckyBlog.support;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 其他工具类
 */
public class Tools {

    /**
     * 获取Timestamp
     */
    public static Timestamp getTimestamp() {
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return Timestamp.valueOf(nowTime);
    }

    public static List<String> getTagList(String tagStr) {
        List<String> tagList = new ArrayList<>();
        StringTokenizer token = new StringTokenizer(tagStr, ",");
        while (token.hasMoreTokens()) {
            tagList.add(token.nextToken());
        }
        return tagList;
    }

    /**
     * 从request中获取用户真实ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
