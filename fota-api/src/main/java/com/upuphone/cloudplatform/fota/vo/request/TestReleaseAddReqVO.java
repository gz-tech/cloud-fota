package com.upuphone.cloudplatform.fota.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 17:05
 */
@Getter
@Setter
@ApiModel(value = "测试发布请求")
public class TestReleaseAddReqVO {

    @ApiModelProperty(value = "版本id", required = true)
    @NotBlank(message = "版本id不为空")
    private String versionId;

    @ApiModelProperty(value = "版本名称", required = true)
    @NotBlank
    @Length(max = 20, message = "版本名称不超过20个字符")
    private String releaseName;

    @ApiModelProperty(value = "升级方式 pull push", required = true)
    @NotBlank(message = "升级方式不为空")
    private String upgradeType;

    @ApiModelProperty(value = "升级范围类型 -all -imei", required = true)
    @NotBlank(message = "升级范围类型不为空")
    private String upgradeRangeType;

    @ApiModelProperty(value = "可升级的imei号")
    private String imei;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "测试发布时间", required = true)
    private LocalDateTime releaseTime;

    @NotBlank(message = "版本描述不为空")
    @ApiModelProperty(value = "版本描述", required = true)
    private String description;



//    private List<Map<String,Object>> upgradeRangeList;
}
