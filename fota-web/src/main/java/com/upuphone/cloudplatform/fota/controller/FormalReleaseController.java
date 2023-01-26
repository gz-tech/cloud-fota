package com.upuphone.cloudplatform.fota.controller;

import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.common.response.CommonErrorCode;
import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.DictConstant;
import com.upuphone.cloudplatform.fota.OrikaUtil;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.bo.VersionBO;
import com.upuphone.cloudplatform.fota.bo.VersionWithFileBO;
import com.upuphone.cloudplatform.fota.client.FormalReleaseClient;
import com.upuphone.cloudplatform.fota.service.ReleaseService;
import com.upuphone.cloudplatform.fota.service.VersionService;
import com.upuphone.cloudplatform.fota.util.OperationLog;
import com.upuphone.cloudplatform.fota.vo.request.*;
import com.upuphone.cloudplatform.fota.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.upuphone.cloudplatform.fota.DictConstant.FORMAL_RELEASE;
import static com.upuphone.cloudplatform.fota.DictConstant.FORMAL_TO_RELEASE;

/**
 * @Classname FormalReleaseController
 * @Description
 * @Date 2022/2/14 6:56 下午
 * @Created by gz-d
 */
@Api(tags = "正式发布api")
@RestController
@RequestMapping("/formal-release")
@Slf4j
public class FormalReleaseController implements FormalReleaseClient {

    @Autowired
    private VersionService versionService;

    @Autowired
    private ReleaseService releaseService;

    @Override
    public CommonResponse<FormalReleasePageRespVO> selectFormalReleaseByPage(@RequestBody @Valid ReleaseListReqVO releaseListReqVO) {
        ReleaseBO releaseBO = OrikaUtil.map(releaseListReqVO, ReleaseBO.class);
        FormalReleasePageRespVO formalReleasePageRespVO = releaseService.selectFormalReleaseByPage(releaseBO,
                releaseListReqVO.getPageNum(), releaseListReqVO.getPageSize());
        return CommonResponse.success(formalReleasePageRespVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(content = "新建计划", objName = "#formalReleaseAddReqVO.getReleaseName()",
            relationId = "#formalReleaseAddReqVO.getVersionId()", operationModule = "FORMAL")
    public CommonResponse formalReleaseAdd(@RequestBody @Valid FormalReleaseAddReqVO formalReleaseAddReqVO) {
        ReleaseBO releaseBO = OrikaUtil.map(formalReleaseAddReqVO, ReleaseBO.class);
        releaseBO.setType(DictConstant.FORMAL_RELEASE);
        releaseBO.setStatus(FORMAL_TO_RELEASE);
        releaseService.releaseAdd(releaseBO);
        VersionBO versionBO = new VersionBO();
        //新建正式发布计划， 版本状态变更为已发布。已发布的就不能在新建版本中选择了。
        versionBO.setVersionId(formalReleaseAddReqVO.getVersionId());
        versionBO.setStatus(DictConstant.VERSION_RELEASED);
        versionBO.setDescription(formalReleaseAddReqVO.getDescription());
        versionService.versionUpdate(versionBO);
        return CommonResponse.success();
    }

    @Override
    public CommonResponse<VersionRespVO> formalReleaseDetail(@ApiParam(value = "版本id", required = true) String versionId) {
        VersionBO versionBO = versionService.selectVersionById(versionId);
        VersionRespVO versionRespVO = OrikaUtil.map(versionBO, VersionRespVO.class);
        return CommonResponse.success(versionRespVO);
    }

    @Override
    public CommonResponse<List<ReleaseListRespVO>> formalReleasePlanList(@ApiParam(value = "版本id", required = true) String versionId) {
        List<ReleaseBO> releaseBOList = releaseService.selectPlanListByVersionId(versionId);
        List<ReleaseListRespVO> result = OrikaUtil.mapAsList(releaseBOList, ReleaseListRespVO.class);
        return CommonResponse.success(result);
    }

    @Override
    public CommonResponse<ReleaseDetailRespVO> formalReleasePlanDetail(String releaseId) {
        ReleaseBO releaseBO = releaseService.releaseDetail(releaseId);
        ReleaseDetailRespVO releaseDetailRespVO = OrikaUtil.map(releaseBO, ReleaseDetailRespVO.class);
        return CommonResponse.success(releaseDetailRespVO);
    }

    @Override
    public CommonResponse versionDescriptionUpdate(@RequestBody @Valid VersionUpdateReqVO versionUpdateReqVO) {
        VersionBO versionBO = new VersionBO();
        versionBO.setVersionId(versionUpdateReqVO.getVersionId());
        versionBO.setDescription(versionUpdateReqVO.getDescription());
        versionService.versionUpdate(versionBO);
        return CommonResponse.success();
    }

    @Override
    @OperationLog(content = "编辑计划", objName = "#formalReleaseUpdateReqVO.getReleaseName()",
            relationId = "#formalReleaseUpdateReqVO.getVersionId()", operationModule = "FORMAL")
    public CommonResponse formalReleasePlanUpdate(@RequestBody @Valid FormalReleaseUpdateReqVO formalReleaseUpdateReqVO) {
        ReleaseBO releaseBO = OrikaUtil.map(formalReleaseUpdateReqVO, ReleaseBO.class);
        if (!releaseService.repeatNameRelease(formalReleaseUpdateReqVO.getReleaseName(),
                formalReleaseUpdateReqVO.getReleaseId(), FORMAL_RELEASE)) {
            throw new BusinessException(CommonErrorCode.PARAM_ERROR, "版本名称重复");
        }
        releaseService.releaseUpdateByReleaseId(releaseBO);
        return CommonResponse.success();
    }

    @Override
    @OperationLog(content = "下架/取消发布", relationId = "#request.getVersionId()", operationModule = "FORMAL")
    public CommonResponse formalReleaseWithdrawOrCancel(@RequestBody @Valid ReleaseStatusUpdateReqVO request) {
        ReleaseBO releaseBO = OrikaUtil.map(request, ReleaseBO.class);
        releaseBO.setStatus(request.getReleaseStatus());
        releaseService.releaseUpdateByReleaseId(releaseBO);
        return CommonResponse.success();
    }

    @Override
    public CommonResponse<List<VersionTargetRespVO>> getAvailableVersionsForFormalRelease() {
        VersionBO versionBO = new VersionBO();
        versionBO.setStatus(DictConstant.VERSION_RELEASABLE);
        List<VersionWithFileBO> versionWithFileList = versionService.selectVersionToformalRelease(versionBO);
        if (versionWithFileList == null) {
            return CommonResponse.success();
        }
        List<VersionTargetRespVO> versionTargetRespVOS = OrikaUtil.mapAsList(versionWithFileList, VersionTargetRespVO.class);
        return CommonResponse.success(versionTargetRespVOS);
    }

    @Override
    public CommonResponse<List<String>> allVersionList() {
        List<String> result = versionService.selectVersionList();
        return CommonResponse.success(result);
    }
}
