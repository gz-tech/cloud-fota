package com.upuphone.cloudplatform.fota.client;

import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.request.FileInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.request.FlashOperationReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname VersionManagementClient
 * @Description
 * @Date 2022/2/14 7:26 下午
 * @Created by gz-d
 */
@Api(tags = "版本管理api")
@FeignClient(path = "/version",value = "cloud-fota-version")
public interface VersionManagementClient {
    @ApiOperation(value = "新增文件")
    @PostMapping("/file/add")
    CommonResponse addFile(@RequestBody FileInfoReqVO fileInfoReqVO);

    @ApiOperation(value = "更新包管理-版本（文件）分页查询")
    @PostMapping("/list")
    CommonResponse versionListByPage(@RequestBody VersionInfoListReqVO versionInfoListReqVO);

    @ApiOperation(value = "文件详情")
    @GetMapping ("/file/detail")
    CommonResponse fileDetail(String fileId);

    @ApiOperation(value = "一键测试通过/失败操作")
    @PostMapping("/file/flashOperation")
    CommonResponse flashOperation(@RequestBody FlashOperationReqVO flashOperationReqVO);

}
