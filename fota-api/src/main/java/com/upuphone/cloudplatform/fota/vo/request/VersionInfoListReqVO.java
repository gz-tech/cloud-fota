package com.upuphone.cloudplatform.fota.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 18:02
 */
@Getter
@Setter
@ApiModel(value = "版本分页查询请求")
public class VersionInfoListReqVO {

    @ApiModelProperty(value = "页码", required = true)
    @NotNull(message = "页码不为空")
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小", required = true)
    @NotNull(message = "分页大小不为空")
    private Integer pageSize;

    @ApiModelProperty(value = "搜索参数：版本id")
    private String versionId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "搜索参数：上传起始时间")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "搜索参数：上传截止时间")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "搜索参数：上传截止时间")
    private String versionStatus;
}
