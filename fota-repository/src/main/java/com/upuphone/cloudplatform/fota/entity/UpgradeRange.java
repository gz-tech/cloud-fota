package com.upuphone.cloudplatform.fota.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guangzheng.ding
 * @date 2021/11/26 16:38
 */
@Getter
@Setter
public class UpgradeRange extends CommonEntity {
    private String id;

    private String versionId;

    private String device;

    private int percent;

    private String imei;

    private String country;

    private String state;

    private String city;


}
