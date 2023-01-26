package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Classname VersionRespVO
 * @Description TODO
 * @Date 2022/2/14 8:57 下午
 * @Created by gz-d
 */
@ApiModel(value = "版本详情")
@Getter
@Setter
public class VersionRespVO {
    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "版本描述", required = true)
    private String description;
}
