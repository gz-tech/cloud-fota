package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Classname ReleaseListRespVO
 * @Description
 * @Date 2022/2/14 6:05 下午
 * @Created by gz-d
 */
@ApiModel(value = "版本发布计划list")
@Getter
@Setter
public class ReleaseListRespVO {
    @ApiModelProperty(value = "发布id", required = true)
    private String releaseId;

    @ApiModelProperty(value = "发布名称", required = true)
    private String releaseName;

    @ApiModelProperty(value = "发布时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @ApiModelProperty(value = "升级范围类型", required = true)
    private String upgradeRangeType;

    @ApiModelProperty(value = "imei")
    private String imei;

    @ApiModelProperty(value = "发布状态", required = true)
    private String status;

    @ApiModelProperty(value = "最后操作时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "最后操作人", required = true)
    private String operator;
}
