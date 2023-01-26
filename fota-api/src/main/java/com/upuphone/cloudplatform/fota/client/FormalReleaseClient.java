package com.upuphone.cloudplatform.fota.client;

import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.vo.request.*;
import com.upuphone.cloudplatform.fota.vo.response.VersionTargetRespVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Classname FormalReleaseClient
 * @Description
 * @Date 2022/2/14 7:24 下午
 * @Created by gz-d
 */
@FeignClient(path = "/formal-release",value = "cloud-fota-formal-release")
public interface FormalReleaseClient {

    @ApiOperation(value = "正式版本发布分页查询")
    @PostMapping("/list")
    CommonResponse selectFormalReleaseByPage(@RequestBody ReleaseListReqVO releaseListReqVO);

    @ApiOperation(value = "新建版本/新建计划")
    @PostMapping("/add")
    CommonResponse formalReleaseAdd(@RequestBody FormalReleaseAddReqVO formalReleaseAddReqVO);

    @ApiOperation(value = "编辑版本-详情")
    @GetMapping("/detail")
    CommonResponse formalReleaseDetail(String versionId);

    @ApiOperation(value = "计划管理-版本计划list")
    @GetMapping("/plan-list")
    CommonResponse formalReleasePlanList(String versionId);

    @ApiOperation(value = "编辑计划-详情查看")
    @GetMapping("/plan-detail")
    CommonResponse formalReleasePlanDetail(String releaseId);

    @ApiOperation(value = "编辑版本-更新")
    @PostMapping("/update")
    CommonResponse versionDescriptionUpdate(VersionUpdateReqVO versionUpdateReqVO);

    @ApiOperation(value = "编辑计划-更新")
    @PostMapping("/plan-update")
    CommonResponse formalReleasePlanUpdate(FormalReleaseUpdateReqVO formalReleaseUpdateReqVO);

    @ApiOperation(value = "计划管理-下架/取消发布")
    @PostMapping("/release-status-update")
    CommonResponse formalReleaseWithdrawOrCancel(@RequestBody @Valid ReleaseStatusUpdateReqVO request);

    @ApiOperation(value = "选择目标版本")
    @GetMapping("/version/select")
    CommonResponse<List<VersionTargetRespVO>> getAvailableVersionsForFormalRelease();

    @ApiOperation(value = "搜索框版本list")
    @GetMapping("/version/list")
    CommonResponse<List<String>> allVersionList();
}
