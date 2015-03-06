package com.sqlboaconnector.util;

import android.util.Log;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetUtil {

    private static final String TAG = NetUtil.class.getSimpleName();

    public static List<String> getAvailableIP() {
        List<String> ipList = new ArrayList<>();
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (isIPv4) {
                            ipList.add(sAddr);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Could not get IP: " + ex, ex);
        }

        return ipList;
    }

}
