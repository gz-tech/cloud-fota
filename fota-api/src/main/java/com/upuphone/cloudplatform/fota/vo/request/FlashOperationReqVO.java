package com.upuphone.cloudplatform.fota.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Classname FlashOperationReqVO
 * @Description
 * @Date 2022/2/7 7:41 下午
 * @Created by gz-d
 */
@Getter@Setter
@ApiModel(value = "一键操作")
public class FlashOperationReqVO {

    @ApiModelProperty(value = "版本id", required = true)
    @NotBlank(message = "操作版本号不为空")
    private String versionId;

    @ApiModelProperty(value = "一键成功/失败 true:成功 false:失败",
            allowableValues = "true, false", example = "true", required = true)
    @NotNull(message = "操作结果不为空")
    private boolean success;

}
