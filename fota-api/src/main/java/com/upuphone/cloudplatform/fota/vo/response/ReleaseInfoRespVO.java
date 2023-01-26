package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Classname ReleaseInfoRespVO
 * @Description
 * @Date 2022/2/14 8:00 上午
 * @Created by gz-d
 */

@ApiModel(value = "版本发布计划")
@Getter
@Setter
public class ReleaseInfoRespVO {
    @ApiModelProperty(value = "发布计划id", required = true)
    private String releaseId;

    @ApiModelProperty(value = "版本发布计划名称", required = true)
    private String releaseName;

    @ApiModelProperty(value = "发布时间", required = true)
    private LocalDateTime releaseTime;

    @ApiModelProperty(value = "版本发布计划状态", required = true)
    private String status;
}
