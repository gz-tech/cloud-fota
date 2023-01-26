package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class VersionInfoRespVO {
    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "发布时间", required = true)
    private Long versionReleaseTimestamp;

    @ApiModelProperty(value = "版本名称", required = true)
    private String versionName;

    @ApiModelProperty(value = "版本描述", required = true)
    private String versionDescription;

    @ApiModelProperty(value = "关联文件名", required = true)
    private String fileName;

    @ApiModelProperty(value = "md5", required = true)
    private String md5;

    @ApiModelProperty(value = "文件大小", required = true)
    private Long fileSize;

    @ApiModelProperty(value = "下载地址", required = true)
    private String downloadUrl;
}
