package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 18:08
 */
@Getter
@Setter
@ApiModel(value = "更新包管理-版本分页查询")
public class VersionInfoListRespVO {
    @ApiModelProperty(value = "版本id", required = true)
    private String versionId;

    @ApiModelProperty(value = "更新包list,包括更新包状态", required = true)
    private List<FileInfoRespVO> files;

    @ApiModelProperty(value = "版本状态-code", required = true)
    private String versionStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上传时间", required = true)
    private LocalDateTime uploadTime;
}
