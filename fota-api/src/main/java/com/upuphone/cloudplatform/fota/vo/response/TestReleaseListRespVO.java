package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Classname TestReleaseRespVO
 * @Description
 * @Date 2022/2/13 10:19 下午
 * @Created by gz-d
 */
@ApiModel(value = "测试发布分页查询返回")
@Getter
@Setter
public class TestReleaseListRespVO {
    @ApiModelProperty(value = "发布id", required = true)
    private String releaseId;

    @ApiModelProperty(value = "测试版本名称", required = true)
    private String releaseName;

    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "测试发布状态", required = true)
    private String status;

    @ApiModelProperty(value = "版本发布时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @ApiModelProperty(value = "最后操作时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "最后操作人", required = true)
    private String operator;
}
