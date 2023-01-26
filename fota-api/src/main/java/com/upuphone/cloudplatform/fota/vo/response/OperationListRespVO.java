package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author guangzheng.ding
 * @date 2021/11/29 9:09
 */
@Getter
@Setter
@ApiModel(value = "操作记录")
public class OperationListRespVO {

    @ApiModelProperty(value = "操作内容", required = true)
    private String content;

    @ApiModelProperty(value = "操作人", required = true)
    private String operator;

    @ApiModelProperty(value = "操作时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;
}
