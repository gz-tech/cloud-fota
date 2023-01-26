package com.upuphone.cloudplatform.fota.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author guangzheng.ding
 * @date 2021/11/27 20:42
 */
@ApiModel(value = "编辑版本")
@Getter
@Setter
public class VersionUpdateReqVO {
    @ApiModelProperty(value = "版本Id", required = true)
    @NotBlank(message = "版本id不为空")
    private String versionId;

    @ApiModelProperty(value = "版本描述", required = true)
    private String description;


}
