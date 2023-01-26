package com.upuphone.cloudplatform.fota.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DeviceInfoReqVO implements Serializable {

    private String versionId;

    private String deviceType;

    private String deviceCode;

    private Boolean full;
}
