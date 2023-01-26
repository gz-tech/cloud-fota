package com.upuphone.cloudplatform.fota.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Classname UpgradeReqVO
 * @Description
 * @Date 2022/2/24 9:57 上午
 * @Created by gz-d
 */
@Getter
@Setter
@ApiModel(value = "升级请求")
public class UpgradeReqVO {
    @ApiModelProperty(value = "版本号", required = true)
    private String versionId;

    @ApiModelProperty(value = "设备型号", required = true)
    private String deviceType;

    @ApiModelProperty(value = "设备码（imei加密）", required = true)
    private String deviceCode;

}
