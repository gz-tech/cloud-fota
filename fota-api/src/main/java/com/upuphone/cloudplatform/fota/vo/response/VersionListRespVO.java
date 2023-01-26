package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author guangzheng.ding
 */
@Getter
@Setter
@ApiModel(description = "响应实体类")
public class VersionListRespVO {

    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "版本名称", required = true)
    private String versionName;

    @ApiModelProperty(value = "版本发布状态", required = true)
    private String status;

    @ApiModelProperty(value = "版本发布状态名称", required = true)
    private String statusName;

    @ApiModelProperty(value = "版本描述", required = true)
    private String description;

    @ApiModelProperty(value = "版本发布时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    @ApiModelProperty(value = "最后操作时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    @ApiModelProperty(value = "最后操作人", required = true)
    private String operator;

    public VersionListRespVO() {
    }
}
