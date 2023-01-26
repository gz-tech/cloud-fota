package com.upuphone.cloudplatform.fota.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.common.response.CommonErrorCode;
import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.DictConstant;
import com.upuphone.cloudplatform.fota.FotaErrorCode;
import com.upuphone.cloudplatform.fota.OrikaUtil;
import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.bo.VersionBO;
import com.upuphone.cloudplatform.fota.bo.VersionWithFileBO;
import com.upuphone.cloudplatform.fota.client.TestReleaseClient;
import com.upuphone.cloudplatform.fota.config.FotaSetting;
import com.upuphone.cloudplatform.fota.service.FileService;
import com.upuphone.cloudplatform.fota.service.OperationRecordService;
import com.upuphone.cloudplatform.fota.service.ReleaseService;
import com.upuphone.cloudplatform.fota.service.VersionService;
import com.upuphone.cloudplatform.fota.util.OperationLog;
import com.upuphone.cloudplatform.fota.vo.request.*;
import com.upuphone.cloudplatform.fota.vo.response.FileInfoRespVO;
import com.upuphone.cloudplatform.fota.vo.response.ReleaseDetailRespVO;
import com.upuphone.cloudplatform.fota.vo.response.TestReleaseListRespVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionTargetRespVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.upuphone.cloudplatform.fota.DictConstant.*;

/**
 * @Classname TestReleaseManagementController
 * @Description
 * @Date 2022/2/14 6:55 下午
 * @Created by gz-d
 */
@Api(tags = "测试发布api")
@RestController
@RequestMapping("/test-release")
@Slf4j
public class TestReleaseController implements TestReleaseClient {

    @Autowired
    private FileService fileService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private OperationRecordService operationRecordService;

    @Autowired
    private FotaSetting fotaSetting;

    @Override
    public CommonResponse<PageDTO<TestReleaseListRespVO>> testList(@RequestBody @Valid ReleaseListReqVO releaseListReqVO) {
        ReleaseBO releaseBO = OrikaUtil.map(releaseListReqVO, ReleaseBO.class);
        PageDTO<ReleaseBO>  releaseBOPageDTO =
                releaseService.testReleaseByPage(releaseBO, releaseListReqVO.getPageNum(), releaseListReqVO.getPageSize());
        PageDTO<TestReleaseListRespVO> result = new PageDTO<>(
                releaseBOPageDTO.getCurrent(),
                releaseBOPageDTO.getSize(),
                releaseBOPageDTO.getTotal()
        );
        result.setRecords(OrikaUtil.mapAsList(releaseBOPageDTO.getRecords(), TestReleaseListRespVO.class));
        return CommonResponse.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse testReleaseAdd(@RequestBody @Valid TestReleaseAddReqVO testReleaseAddReqVO) {
        ReleaseBO releaseBO = OrikaUtil.map(testReleaseAddReqVO, ReleaseBO.class);
        if (RANGE_ALL.equals(releaseBO.getUpgradeRangeType())) {
            //测试管理中默认设置测试imei库
            releaseBO.setImei(fotaSetting.getImei());
        }
        releaseBO.setType(DictConstant.TEST_RELEASE);
        releaseBO.setStatus(TEST_TO_RELEASE);
        String releaseId = releaseService.releaseAdd(releaseBO);
        VersionBO versionBO = new VersionBO();
        versionBO.setVersionId(testReleaseAddReqVO.getVersionId());
        versionBO.setDescription(testReleaseAddReqVO.getDescription());
        versionService.versionUpdate(versionBO);
        operationRecordService.saveOperation("新建测试版本", releaseId, TEST_RELEASE);
        return CommonResponse.success();
    }

    @Override
    public CommonResponse<ReleaseDetailRespVO> testReleaseDetail(String releaseId) {
        ReleaseBO releaseBO = releaseService.releaseDetail(releaseId);
        if (releaseBO == null) {
            throw new BusinessException(FotaErrorCode.NOT_FOUND_ERROR);
        }
        VersionBO versionBO = versionService.selectVersionById(releaseBO.getVersionId());
        ReleaseDetailRespVO releaseDetailRespVO = OrikaUtil.map(releaseBO, ReleaseDetailRespVO.class);
        releaseDetailRespVO.setDescription(versionBO.getDescription());
        return CommonResponse.success(releaseDetailRespVO);
    }

    @Override
    public CommonResponse<List<VersionTargetRespVO>> selectTestReleaseVersion() {
        VersionBO versionBO = new VersionBO();
        //版本是待测试状态 且 如果测试版本存在，状态不能为待测试
        versionBO.setStatus(VERSION_TO_TEST);
        List<VersionWithFileBO> versionWithFileList = versionService.versionWithFileList(versionBO);
        if (versionWithFileList == null) {
            return CommonResponse.success();
        }
        List<VersionTargetRespVO> versionTargetRespVOS = OrikaUtil.mapAsList(versionWithFileList, VersionTargetRespVO.class);
        return CommonResponse.success(versionTargetRespVOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 20)
    @OperationLog(content = "取消测试", relationId = "#request.getReleaseId()", operationModule = "TEST")
    public CommonResponse testReleaseCancel(@RequestBody @Valid ReleaseStatusUpdateReqVO request) {
        ReleaseBO releaseBO = new ReleaseBO();
        releaseBO.setReleaseId(request.getReleaseId());
        ReleaseBO releaseBO1 = releaseService.releaseDetail(request.getReleaseId());
        if (releaseBO1 == null) {
            throw new BusinessException(FotaErrorCode.NOT_FOUND_ERROR);
        }
        String status = releaseBO1.getStatus();
        if (!TEST_TO_RELEASE.equals(status) && !TEST_IN_RELEASE.equals(status)) {
            throw new BusinessException(FotaErrorCode.OPERATION_DENIED, "测试版本状态非待测试或测试中， 不允许取消");
        }
        //测试版本状态->测试终止
        releaseBO.setStatus(DictConstant.TEST_CANCEL);
        releaseService.releaseUpdateByReleaseId(releaseBO);
        VersionBO versionBO = new VersionBO();
        versionBO.setVersionId(request.getVersionId());
        versionBO.setStatus(VERSION_TO_TEST);
        //版本状态->待测试
        versionService.versionUpdate(versionBO);

        List<FileBO> filesToChangeStatus = fileService.fileListByVersionId(request.getVersionId());
        List<String> files = filesToChangeStatus.stream().map(e->e.getFileId())
                .collect(Collectors.toList());
        //文件状态->待测试
        fileService.fileUpdateByFileIds(files, FILE_TO_TEST);
        return CommonResponse.success();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(content = "更新包测试状态变更", relationId = "#fileStatusUpdateReqVO.getVersionId()", operationModule = "FILE")
    public CommonResponse testFileUpdate(@RequestBody FileStatusUpdateReqVO fileStatusUpdateReqVO) {
        VersionBO versionBO = new VersionBO();
        versionBO.setVersionId(fileStatusUpdateReqVO.getVersionId());
        ReleaseBO releaseBO = new ReleaseBO();
        releaseBO.setReleaseId(fileStatusUpdateReqVO.getReleaseId());
        log.info("更新文件状态");
        fileService.fileUpdateByFileIds(fileStatusUpdateReqVO.getFileId(), fileStatusUpdateReqVO.getStatus());
        if (FILE_PASS.equals(fileStatusUpdateReqVO.getStatus())) {
            List<FileBO> fileList = fileService.fileListByVersionId(fileStatusUpdateReqVO.getVersionId());
            List<FileBO> filePass = fileList.stream().filter(item->FILE_PASS.equals(item.getStatus())).collect(Collectors.toList());
            if (fileList.size() == filePass.size() && fileList.size() != 0) {
                log.info("所有文件都测试通过");
                versionBO.setStatus(VERSION_RELEASABLE);
                versionService.versionUpdate(versionBO);
                releaseBO.setStatus(TEST_PASS);
            }
        } else if (FILE_FAIL.equals(fileStatusUpdateReqVO.getStatus())) {
            versionBO.setStatus(VERSION_FAIL);
            versionService.versionUpdate(versionBO);
            releaseBO.setStatus(TEST_FAIL);
        } else {
            throw new BusinessException(CommonErrorCode.PARAM_ERROR, "状态非法");
        }
        releaseService.releaseUpdateByReleaseId(releaseBO);
        return CommonResponse.success();
    }

    @Override
    @OperationLog(content = "编辑测试版本", relationId = "#releaseUpdateReqVO.getReleaseId()", operationModule = "TEST")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse testReleaseUpdate(@RequestBody @Valid TestReleaseUpdateReqVO releaseUpdateReqVO) {

        ReleaseBO releaseBO = OrikaUtil.map(releaseUpdateReqVO, ReleaseBO.class);

        if (!releaseService.repeatNameRelease(releaseUpdateReqVO.getReleaseName(),
                releaseUpdateReqVO.getReleaseId(), TEST_RELEASE)) {
            throw new BusinessException(CommonErrorCode.PARAM_ERROR, "版本名称重复");
        }
        releaseService.releaseUpdateByReleaseId(releaseBO);
        VersionBO versionBO = new VersionBO();
        versionBO.setVersionId(releaseUpdateReqVO.getVersionId());
        versionBO.setDescription(releaseUpdateReqVO.getDescription());
        versionService.versionUpdate(versionBO);
        return CommonResponse.success();
    }

    @Override
    public CommonResponse<List<FileInfoRespVO>> fileListByVersionId(@RequestParam("versionId") String versionId) {
        List<FileBO> fileBOList = fileService.fileListByVersionId(versionId);
        return CommonResponse.success(OrikaUtil.mapAsList(fileBOList, FileInfoRespVO.class));
    }
}
