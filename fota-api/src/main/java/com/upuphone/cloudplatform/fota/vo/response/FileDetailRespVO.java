package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Classname FileDetailRespVO
 * @Description
 * @Date 2022/2/7 8:16 下午
 * @Created by gz-d
 */
@Getter@Setter
@ApiModel(value = "文件详细信息")
public class FileDetailRespVO {

    @ApiModelProperty(value = "目标版本", required = true)
    private String versionId;

    @ApiModelProperty(value = "差分包源版本号")
    private String fromVersionId;

    @ApiModelProperty(value = "更新包名称", required = true)
    private String name;

    @ApiModelProperty(value = "更新包大小", required = true)
    private Long fileSize;

    @ApiModelProperty(value = "更新包类型-code", required = true)
    private String fileType;

    @ApiModelProperty(value = "更新包状态-code", required = true)
    private String status;
}
