package com.upuphone.cloudplatform.fota.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.upuphone.cloudplatform.fota.constant.ApiConstant.VERSION_REGEXP;


@Data
@ApiModel(value = "文件信息")
public class FileInfoReqVO {
    @NotBlank(message = "版本号不能为空")
    @Pattern(regexp = VERSION_REGEXP, message = "版本号格式不正确")
    @ApiModelProperty(value = "关联版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "差分包起始版本id")
    @Pattern(regexp = "^([0-9]{1,2}(\\.[0-9]{1,2}){3}[\\w\\W]*$)|(^$)$", message = "版本号格式不正确")
    private String fromVersionId;

    @ApiModelProperty(value = "关联版本名", required = true)
    @NotBlank(message = "版本名不能为空")
    private String versionName;

    private String fileId;

    @ApiModelProperty(value = "文件名", required = true)
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @ApiModelProperty(value = "端地址", required = true)
    @NotBlank(message = "端地址不能为空")
    private String endPoint;

    @ApiModelProperty(value = "桶名", required = true)
    @NotBlank(message = "桶名不能为空")
    private String bucketName;

    @ApiModelProperty(value = "对象名", required = true)
    @NotBlank(message = "对象名不能为空")
    private String objectName;

    @ApiModelProperty(value = "密钥", required = true)
    @NotBlank(message = "密钥不能为空")
    private String ak;

    @ApiModelProperty(value = "密钥", required = true)
    @NotBlank(message = "密钥不能为空")
    private String sk;

    @ApiModelProperty(value = "md5", required = true)
    @NotBlank(message = "文件md5不能为空")
    private String md5;

    @ApiModelProperty(value = "文件大小", required = true)
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;


    @ApiModelProperty(value = "文件类型，complete-整包， difference-差分包",
            allowableValues = "complete, difference", example = "complete", required = true)
    @NotBlank(message = "文件类型不能为空")
    private String fileType;

    @ApiModelProperty(value = "关联版本id", required = true)
    @NotBlank(message = "存储服务提供商不能为空")
    private String storageProviderName;

    private String status;

}
