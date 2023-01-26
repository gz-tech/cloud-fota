package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@ApiModel(value = "版本选择响应")
public class VersionTargetRespVO {

    @ApiModelProperty(value = "版本id")
    private String versionId;

    @ApiModelProperty(value = "文件名list")
    private List<String> files;

}
