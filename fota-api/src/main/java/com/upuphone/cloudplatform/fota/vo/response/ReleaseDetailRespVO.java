package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Classname ReleaseDetailRespVO
 * @Description
 * @Date 2022/2/14 6:42 下午
 * @Created by gz-d
 */
@ApiModel(value = "版本发布计划详情")
@Getter
@Setter
public class ReleaseDetailRespVO {
    @ApiModelProperty(value = "发布id", required = true)
    private String releaseId;

    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "发布名称", required = true)
    private String releaseName;

    @ApiModelProperty(value = "升级类型", required = true)
    private String upgradeType;

    @ApiModelProperty(value = "升级范围类型", required = true)
    private String upgradeRangeType;

    @ApiModelProperty(value = "imei")
    private String imei;

    @ApiModelProperty(value = "发布状态", required = true)
    private String status;

    @ApiModelProperty(value = "测试时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @ApiModelProperty(value = "版本描述", required = true)
    private String description;

}
