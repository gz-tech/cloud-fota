package com.upuphone.cloudplatform.fota.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Classname TestReleaseListReqVO
 * @Description
 * @Date 2022/2/13 10:09 下午
 * @Created by gz-d
 */
@ApiModel(value = "测试发布分页查询")
@Getter
@Setter
public class ReleaseListReqVO {
    @ApiModelProperty(value = "版本号")
    private String versionId;

    @ApiModelProperty(value = "测试版本名称")
    private String releaseName;

    @ApiModelProperty(value = "测试发布状态")
    private String status;

    @ApiModelProperty(value = "测试发布起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "测试发布截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "页码", required = true)
    @NotNull(message = "页码不为空")
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小", required = true)
    @NotNull(message = "分页大小不为空")
    private Integer pageSize;
}
