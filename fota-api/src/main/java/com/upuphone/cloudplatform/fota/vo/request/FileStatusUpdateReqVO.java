package com.upuphone.cloudplatform.fota.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Classname FileStatusUpdateReqVO
 * @Description
 * @Date 2022/2/13 10:27 下午
 * @Created by gz-d
 */
@ApiModel(value = "文件测试状态请求参数")
@Getter
@Setter
public class FileStatusUpdateReqVO {
    @ApiModelProperty(value = "文件id", required = true)
    @NotBlank(message = "文件id")
    private List<String> fileId;

    @ApiModelProperty(value = "状态", required = true)
    @NotBlank(message = "状态")
    private String status;

    @ApiModelProperty(value = "版本id", required = true)
    @NotBlank(message = "版本id")
    private String versionId;

    @ApiModelProperty(value = "发布id", required = true)
    @NotBlank(message = "发布id")
    private String releaseId;

}
