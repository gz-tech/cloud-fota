package com.upuphone.cloudplatform.fota.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Classname
 * @Description
 * @Date 2022/2/14 8:22 下午
 * @Created by gz-d
 */
@ApiModel(value = "下架/取消发布")
@Getter
@Setter
public class ReleaseStatusUpdateReqVO {
    @ApiModelProperty(value = "发布id", required = true)
    private String releaseId;

    @ApiModelProperty(value = "变更状态", required = true)
    @NotBlank(message = "变更状态不为空")
    private String releaseStatus;

    @ApiModelProperty(value = "下架/取消原因", required = true)
    @NotBlank(message = "原因不为空不为空")
    @Length(min = 5, max = 100, message = "原因在5-100个字符")
    private String reason;

    @ApiModelProperty(value = "versionId", required = true)
    @NotBlank(message = "目标版本号不为空")
    private String versionId;
}
