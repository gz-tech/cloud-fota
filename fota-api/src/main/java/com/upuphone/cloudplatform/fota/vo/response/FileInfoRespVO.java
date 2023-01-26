package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guangzheng.ding
 * @date 2021/11/30 15:58
 */
@Getter
@Setter
@ApiModel(value = "版本对应的更新包list")
public class FileInfoRespVO {

    @ApiModelProperty(value = "文件id", required = true)
    private String fileId;

    @ApiModelProperty(value = "文件名称", required = true)
    private String name;

    @ApiModelProperty(value = "文件状态-code", required = true)
    private String status;

}
