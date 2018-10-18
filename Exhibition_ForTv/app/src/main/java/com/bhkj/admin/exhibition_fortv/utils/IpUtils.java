package com.bhkj.admin.exhibition_fortv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2018/10/16.
 */

public class IpUtils {
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                 try {

                 for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                      en.hasMoreElements(); ) { NetworkInterface intf = en.nextElement();
                 for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                      enumIpAddr.hasMoreElements();
                 ) {
                 InetAddress inetAddress = enumIpAddr.nextElement();
                 if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                 return inetAddress.getHostAddress();
                 }
                 } }
                 }
                 catch (SocketException e)
                 { e.printStackTrace(); } }
                 else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                  WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                 WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                 String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());

                return ipAddress; }
        }
                 else {
            //当前无网络连接,请在设置中打开网络
        }
                 return null;


            }

    public static String intIP2StringIP(int ip) {

        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
    }

    /**
     * 获取ip地址
     * @return
     */
    public static String getHostIP() {


        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("FuncTcpServer", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }

        }
