package com.upuphone.cloudplatform.fota.client;

import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.vo.request.*;
import com.upuphone.cloudplatform.fota.vo.response.VersionTargetRespVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Classname TestReleaseClient
 * @Description
 * @Date 2022/2/14 7:25 下午
 * @Created by gz-d
 */
@FeignClient(path = "/test-release",value = "cloud-fota-test-release")
public interface TestReleaseClient {

    @ApiOperation(value = "测试发布分页查询")
    @PostMapping("/list")
    CommonResponse testList(@RequestBody ReleaseListReqVO releaseListReqVO);

    @ApiOperation(value = "新建测试")
    @PostMapping("/add")
    CommonResponse testReleaseAdd(@RequestBody TestReleaseAddReqVO testReleaseAddReqVO);

    @ApiOperation(value = "编辑/查看-测试发布详情")
    @GetMapping("/detail")
    CommonResponse testReleaseDetail(@ApiParam(value = "发布id", required = true) String releaseId);

    @ApiOperation(value = "新建测试-选择目标版本")
    @GetMapping("/version/select")
    CommonResponse<List<VersionTargetRespVO>> selectTestReleaseVersion();

    @ApiOperation(value = "取消测试")
    @PostMapping("/cancel")
    CommonResponse testReleaseCancel(ReleaseStatusUpdateReqVO releaseStatusUpdateReqVO);

    @ApiOperation(value = "编辑-更新测试发布")
    @PostMapping("/update")
    CommonResponse testReleaseUpdate(@RequestBody TestReleaseUpdateReqVO releaseUpdateReqVO);

    @ApiOperation(value = "操作-测试成功/失败")
    @PostMapping("/file-update")
    CommonResponse testFileUpdate(@RequestBody FileStatusUpdateReqVO fileStatusUpdateReqVO);

    @ApiOperation(value = "操作-版本对应文件list")
    @GetMapping("/file-list")
    CommonResponse fileListByVersionId(@ApiParam(value = "版本id", required = true) @RequestParam("versionId") String versionId);
}
