package com.demo.platform.commons.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtils {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个反向代理IP时，取第一个
        int commaOffset = ip.indexOf(',');
        if (commaOffset > 0) {
            ip = ip.substring(0, commaOffset);
        }
        if (ip.split("\\.").length != 4 ){
            return "127.0.0.1";
        }
        return ip;
    }


    private static InetAddress getLocalHostAddress() {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface
                    .getNetworkInterfaces(); nis.hasMoreElements();) {
                NetworkInterface ni = nis.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || !ni.isUp())
                    continue;
                for (Enumeration<InetAddress> ias = ni.getInetAddresses(); ias.hasMoreElements();) {
                    InetAddress ia = ias.nextElement();
                    if (ia instanceof Inet6Address) continue;
                    return ia;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isMobile(String str) {
        if (org.springframework.util.StringUtils.isEmpty(str))return false;
        boolean b = false;
        if(str.length()!=11)return b;

        Pattern p = null;
        Matcher m = null;

        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isEmail(String email){
        if (org.springframework.util.StringUtils.isEmpty(email)) return false;

        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean checkPatten(String str, String patten) {
        Pattern p =  Pattern.compile(patten);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 字符串首字母大写
     * @param str
     * @return
     */
    public static String toUpperCaseFirstOne(String str) {
        if (str == null || "".equals(str))
            return str;
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1))
                    .toString();
    }

}
