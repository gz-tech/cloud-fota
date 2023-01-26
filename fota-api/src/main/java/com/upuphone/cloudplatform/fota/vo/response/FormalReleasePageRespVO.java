package com.upuphone.cloudplatform.fota.vo.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Classname FormalReleasePageRespVO
 * @Description
 * @Date 2022/2/25 11:02 上午
 * @Created by gz-d
 */
@ApiModel(value = "分页查询")
@Getter
@Setter
public class FormalReleasePageRespVO {
    @ApiModelProperty(value = "分页数据", required = true)
    private PageDTO<FormalReleaseListRespVO> pageDTO;

    @ApiModelProperty(value = "命中id", required = true)
    private List<String> targetReleaseIds;
}
