package com.yechaoa.wanandroid_jetpack.util

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import androidx.annotation.RequiresPermission
import com.yechaoa.yutilskt.YUtils
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException
import java.util.*

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
object NetworkUtil {

    private fun NetworkUtil() {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    enum class NetworkType {
        NETWORK_ETHERNET, NETWORK_WIFI, NETWORK_5G, NETWORK_4G, NETWORK_3G, NETWORK_2G, NETWORK_UNKNOWN, NETWORK_NO
    }

    /**
     * Open the settings of wireless.
     */
    fun openWirelessSettings() {
        YUtils.getApp().startActivity(
            Intent(Settings.ACTION_WIRELESS_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    /**
     * Return whether network is connected.
     *
     * Must hold `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: connected<br></br>`false`: disconnected
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun isConnected(): Boolean {
        val info = getActiveNetworkInfo()
        return info != null && info.isConnected
    }

    /**
     * Return whether network is available using domain.
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(INTERNET)
    fun isAvailableByDns(): Boolean {
        return isAvailableByDns("")
    }

    /**
     * Return whether network is available using domain.
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param domain The name of domain.
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(INTERNET)
    fun isAvailableByDns(domain: String?): Boolean {
        val realDomain = if (TextUtils.isEmpty(domain)) "www.baidu.com" else domain!!
        val inetAddress: InetAddress?
        try {
            inetAddress = InetAddress.getByName(realDomain)
            return inetAddress != null
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Return whether using mobile data.
     *
     * Must hold `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun isMobileData(): Boolean {
        val info = getActiveNetworkInfo()
        return (null != info && info.isAvailable
                && info.type == ConnectivityManager.TYPE_MOBILE)
    }

    /**
     * Return whether using 4G.
     *
     * Must hold `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun is4G(): Boolean {
        val info = getActiveNetworkInfo()
        return (info != null && info.isAvailable
                && info.subtype == TelephonyManager.NETWORK_TYPE_LTE)
    }

    /**
     * Return whether using 4G.
     *
     * Must hold `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun is5G(): Boolean {
        val info = getActiveNetworkInfo()
        return (info != null && info.isAvailable
                && info.subtype == TelephonyManager.NETWORK_TYPE_NR)
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private fun getActiveNetworkInfo(): NetworkInfo? {
        val cm =
            YUtils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return null
        return cm.activeNetworkInfo
    }

    /**
     * Return the ip address.
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param useIPv4 True to use ipv4, false otherwise.
     * @return the ip address
     */
    @RequiresPermission(INTERNET)
    fun getIPAddress(useIPv4: Boolean): String? {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            val adds = LinkedList<InetAddress>()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp || ni.isLoopback) continue
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement())
                }
            }
            for (add in adds) {
                if (!add.isLoopbackAddress) {
                    val hostAddress = add.hostAddress
                    val isIPv4 = hostAddress.indexOf(':') < 0
                    if (useIPv4) {
                        if (isIPv4) return hostAddress
                    } else {
                        if (!isIPv4) {
                            val index = hostAddress.indexOf('%')
                            return if (index < 0) hostAddress.toUpperCase() else hostAddress.substring(
                                0,
                                index
                            ).toUpperCase()
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * Return the domain address.
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param domain The name of domain.
     * @return the domain address
     */
    @RequiresPermission(INTERNET)
    fun getDomainAddress(domain: String?): String? {
        val inetAddress: InetAddress
        return try {
            inetAddress = InetAddress.getByName(domain)
            inetAddress.hostAddress
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            ""
        }
    }


    /**
     * Return the gate way by wifi.
     *
     * @return the gate way by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    fun getGatewayByWifi(): String? {
        @SuppressLint("WifiManagerLeak") val wm =
            YUtils.getApp().getSystemService(WIFI_SERVICE) as WifiManager
                ?: return ""
        return Formatter.formatIpAddress(wm.dhcpInfo.gateway)
    }

    /**
     * Return the net mask by wifi.
     *
     * @return the net mask by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    fun getNetMaskByWifi(): String? {
        @SuppressLint("WifiManagerLeak") val wm =
            YUtils.getApp().getSystemService(WIFI_SERVICE) as WifiManager
                ?: return ""
        return Formatter.formatIpAddress(wm.dhcpInfo.netmask)
    }

    /**
     * Return the server address by wifi.
     *
     * @return the server address by wifi
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    fun getServerAddressByWifi(): String? {
        @SuppressLint("WifiManagerLeak") val wm =
            YUtils.getApp().getSystemService(WIFI_SERVICE) as WifiManager
                ?: return ""
        return Formatter.formatIpAddress(wm.dhcpInfo.serverAddress)
    }

    /**
     * Return the ssid.
     *
     * @return the ssid.
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    fun getSSID(): String? {
        val wm =
            YUtils.getApp().getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
                ?: return ""
        val wi = wm.connectionInfo ?: return ""
        val ssid = wi.ssid
        if (TextUtils.isEmpty(ssid)) {
            return ""
        }
        return if (ssid.length > 2 && ssid[0] == '"' && ssid[ssid.length - 1] == '"') {
            ssid.substring(1, ssid.length - 1)
        } else ssid
    }


    interface OnNetworkStatusChangedListener {
        fun onDisconnected()
        fun onConnected(networkType: NetworkType?)
    }
}