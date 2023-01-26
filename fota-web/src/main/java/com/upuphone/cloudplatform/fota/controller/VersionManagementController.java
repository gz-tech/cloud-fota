package com.upuphone.cloudplatform.fota.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.DictConstant;
import com.upuphone.cloudplatform.fota.FotaErrorCode;
import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.bo.VersionBO;
import com.upuphone.cloudplatform.fota.client.VersionManagementClient;
import com.upuphone.cloudplatform.fota.service.FileService;
import com.upuphone.cloudplatform.fota.service.ReleaseService;
import com.upuphone.cloudplatform.fota.service.VersionService;
import com.upuphone.cloudplatform.fota.util.OperationLog;
import com.upuphone.cloudplatform.fota.vo.request.FileInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.request.FlashOperationReqVO;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.response.FileDetailRespVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionInfoListRespVO;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.upuphone.cloudplatform.fota.DictConstant.*;

/**
 * @Classname VersionController
 * @Description
 * @Date 2022/2/14 6:58 下午
 * @Created by gz-d
 */

@RestController
@Slf4j
@RequestMapping("/version")
public class VersionManagementController implements VersionManagementClient {
    @Autowired
    private FileService fileService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private ReleaseService releaseService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(content = "新增更新包", objName = "#fileInfoReqVO.getFileName()", relationId = "#fileInfoReqVO.getVersionId()", operationModule = "FILE")
    public CommonResponse addFile(@RequestBody @Valid FileInfoReqVO fileInfoReqVO) {
        VersionBO existVersion = versionService.selectVersionById(fileInfoReqVO.getVersionId());
        if (existVersion == null) {
            log.info("新增文件目标版本不存在，新增版本");
            VersionBO versionBO = new VersionBO();
            versionBO.setStatus(VERSION_TO_TEST);
            versionBO.setVersionId(fileInfoReqVO.getVersionId());
            versionService.addVersion(versionBO);
            fileInfoReqVO.setStatus(FILE_TO_TEST);
        } else if (VERSION_TO_TEST.equals(existVersion.getStatus())) {
           fileInfoReqVO.setStatus(FILE_TO_TEST);
        } else if (VERSION_IN_TEST.equals(existVersion.getStatus())) {
            fileInfoReqVO.setStatus(FILE_IN_TEST);
        } else {
            log.info("版本非待测试或测试中状态");
            throw new BusinessException(FotaErrorCode.VERSION_ALREADY_TEST_ERROR);
        }
        fileService.addFile(fileInfoReqVO);
        return CommonResponse.success();
    }

    @Override
    public CommonResponse<PageDTO<VersionInfoListRespVO>> versionListByPage(@RequestBody @Valid VersionInfoListReqVO versionInfoListReqVO) {
        PageDTO<VersionInfoListRespVO> pageDTO = versionService.versionListByPage(versionInfoListReqVO);
        return CommonResponse.success(pageDTO);
    }

    @Override
    public CommonResponse<FileDetailRespVO> fileDetail(@ApiParam(value = "文件id", required = true) @RequestParam("fileId") String fileId) {
        return CommonResponse.success(fileService.fileDetail(fileId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(content = "一键状态变更", relationId = "#flashOperationReqVO.getVersionId()", operationModule = "FILE")
    public CommonResponse flashOperation(@RequestBody @Valid FlashOperationReqVO flashOperationReqVO) {
        VersionBO versionBO = new VersionBO();
        FileBO fileBO = new FileBO();
        versionBO.setVersionId(flashOperationReqVO.getVersionId());
        fileBO.setVersionId(flashOperationReqVO.getVersionId());
        ReleaseBO releaseBO = new ReleaseBO();
        releaseBO.setVersionId(flashOperationReqVO.getVersionId());
        if (flashOperationReqVO.isSuccess()) {
            VersionBO operatingVersion = versionService.selectVersionById(flashOperationReqVO.getVersionId());
            //除了已发布和可发布 都变成 可发布
            if (VERSION_RELEASABLE.equals(operatingVersion.getStatus())
                    || VERSION_RELEASED.equals(operatingVersion.getStatus())) {
                throw new BusinessException(FotaErrorCode.OPERATION_DENIED, "已发布或可发布状态下不允许此操作");
            }
            versionBO.setStatus(DictConstant.VERSION_RELEASABLE);
            fileBO.setStatus(DictConstant.FILE_PASS);
            //测试发布中的状态都变测试通过
            releaseBO.setStatus(TEST_PASS);
            releaseBO.setType(TEST_RELEASE);
            releaseService.releaseUpdateByVersionId(releaseBO);
        } else {
            versionBO.setStatus(DictConstant.VERSION_FAIL);
            fileBO.setStatus(DictConstant.FILE_FAIL);
            log.info("一键失败，下架该版本关联的所有发布");
            //正式发布中的状态都变已取消
            releaseBO.setStatus(FORMAL_CANCEL);
            releaseBO.setType(FORMAL_RELEASE);
            releaseService.releaseUpdateByVersionId(releaseBO);
            //测试发布中的状态都变测试失败
            releaseBO.setStatus(TEST_FAIL);
            releaseBO.setType(TEST_RELEASE);
            releaseService.releaseUpdateByVersionId(releaseBO);
        }
        versionService.versionUpdate(versionBO);
        fileService.fileUpdateByVersionId(fileBO);
        return CommonResponse.success();
    }
}
