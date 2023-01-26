package com.upuphone.cloudplatform.fota.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Classname DictRespVO
 * @Description
 * @Date 2022/2/14 8:34 下午
 * @Created by gz-d
 */
@ApiModel(value = "字典返回")
@Getter
@Setter
public class DictRespVO {
    @ApiModelProperty(value = "字典码")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String text;
}
