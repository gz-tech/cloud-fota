package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Classname FormalReleaseListRespVO
 * @Description
 * @Date 2022/2/14 7:56 上午
 * @Created by gz-d
 */
@ApiModel(value = "版本发布计划分页查询")
@Getter
@Setter
public class FormalReleaseListRespVO {

    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "更新包list,包括更新包状态", required = true)
    private List<ReleaseInfoRespVO> releases;

    @ApiModelProperty(value = "操作人", required = true)
    private String operator;

    @ApiModelProperty(value = "版本描述", required = true)
    private String description;
}
