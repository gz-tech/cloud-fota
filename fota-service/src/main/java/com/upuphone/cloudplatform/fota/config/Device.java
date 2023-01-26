package com.upuphone.cloudplatform.fota.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author guangzheng.ding
 * @date 2021/12/3 14:32
 */
@Configuration
@ConfigurationProperties(prefix = "fota")
public class Device {
    private List<Map<String, String>> deviceList;

    public List<Map<String, String>> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Map<String, String>> deviceList) {
        this.deviceList = deviceList;
    }
}
