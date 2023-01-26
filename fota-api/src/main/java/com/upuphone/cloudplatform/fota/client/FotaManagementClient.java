package com.upuphone.cloudplatform.fota.client;


import com.upuphone.cloudplatform.common.response.CommonResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author guangzheng.ding
 * @date 2021/12/29 16:59
 */
@FeignClient(name = "cloud-fota-common")
public interface FotaManagementClient {

    @ApiOperation(value = "字典查询")
    @GetMapping("/dict/{flnm}")
    CommonResponse getDict(@ApiParam(value = "字典类别", required = true) @PathVariable("flnm") String flnm);

    @ApiOperation(value = "操作记录")
    @GetMapping("/operation/record")
    CommonResponse recordList(@ApiParam(value = "版本id", required = true) String versionId,
                              @ApiParam(value = "模块 更新包FILE 测试TEST 发布FORMAL", required = true)String module);

}
