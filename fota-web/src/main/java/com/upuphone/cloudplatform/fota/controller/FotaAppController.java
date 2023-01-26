package com.upuphone.cloudplatform.fota.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.common.response.CommonErrorCode;
import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.common.utils.JsonUtility;
import com.upuphone.cloudplatform.fota.OrikaUtil;
import com.upuphone.cloudplatform.fota.bo.DeviceBO;
import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.client.FotaAppClient;
import com.upuphone.cloudplatform.fota.constant.FileConstant;
import com.upuphone.cloudplatform.fota.converter.VersionConverter;
import com.upuphone.cloudplatform.fota.service.FileService;
import com.upuphone.cloudplatform.fota.service.ReleaseService;
import com.upuphone.cloudplatform.fota.vo.request.DeviceInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.request.UpgradeReqVO;
import com.upuphone.cloudplatform.fota.vo.response.UpgradeRespVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionInfoRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "手机端 API")
public class FotaAppController implements FotaAppClient {

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private FileService fileService;


    @Override
    @ApiOperation(value = "版本更新v1")
    public CommonResponse<List<VersionInfoRespVO>> getVersionStatus(@RequestParam("deviceInfo") String deviceInfo) {
        List<VersionInfoRespVO> result = new ArrayList<>();
        DeviceInfoReqVO deviceInfoReqVO = null;
        try {
            deviceInfoReqVO = JsonUtility.toObject(deviceInfo, DeviceInfoReqVO.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(CommonErrorCode.PARAM_ERROR);
        }
        DeviceBO deviceBO = OrikaUtil.map(deviceInfoReqVO, DeviceBO.class);
        ReleaseBO maxRelease = releaseService.getMaxAvailableVersion(deviceBO);
        if (maxRelease == null) {
            //TODO 后面改成返回code
            return CommonResponse.success();
        } else {
            List<FileBO> fileBOList = fileService.fileListByVersionId(maxRelease.getVersionId());
            FileBO fullFile = null;
            FileBO diffFile = null;
            for (FileBO file:
                    fileBOList) {
                if (deviceBO.getVersionId().equals(file.getFromVersionId())
                        && FileConstant.DIFFERENCE.equals(file.getType())) {
                    diffFile = file;
                }
                if (FileConstant.COMPLETE.equals(file.getType())) {
                    fullFile = file;
                }
            }
            FileBO resultFile = diffFile != null ? diffFile : fullFile;
            UpgradeRespVO upgradeRespVO = VersionConverter.INSTANCE.mergeVo(maxRelease, resultFile);
            upgradeRespVO.setVersionReleaseTimestamp(maxRelease.getReleaseTime()
                    .atZone(ZoneId.systemDefault()).toEpochSecond());
            String url = releaseService.getUrl(maxRelease, resultFile);
            upgradeRespVO.setDownloadUrl(url);
            VersionInfoRespVO versionInfoRespVO = OrikaUtil.map(upgradeRespVO, VersionInfoRespVO.class);
            result.add(versionInfoRespVO);
            return CommonResponse.success(result);
        }
    }

    @Override
    @ApiOperation(value = "版本更新v2")
    public CommonResponse<UpgradeRespVO> upgrade(@RequestBody UpgradeReqVO upgradeReqVO) {
        DeviceBO deviceBO = OrikaUtil.map(upgradeReqVO, DeviceBO.class);
        ReleaseBO maxRelease = releaseService.getMaxAvailableVersion(deviceBO);

        if (maxRelease == null) {
            //TODO 后面改成返回code
            return CommonResponse.success();
        } else {
            List<FileBO> fileBOList = fileService.fileListByVersionId(maxRelease.getVersionId());
            FileBO fullFile = null;
            FileBO diffFile = null;
            for (FileBO file:
                    fileBOList) {
                if (deviceBO.getVersionId().equals(file.getFromVersionId())
                        && FileConstant.DIFFERENCE.equals(file.getType())) {
                    diffFile = file;
                }
                if (FileConstant.COMPLETE.equals(file.getType())) {
                    fullFile = file;
                }
            }
            FileBO resultFile = diffFile != null ? diffFile : fullFile;
            UpgradeRespVO upgradeRespVO = VersionConverter.INSTANCE.mergeVo(maxRelease, resultFile);
            upgradeRespVO.setVersionReleaseTimestamp(maxRelease.getReleaseTime()
                    .atZone(ZoneId.systemDefault()).toEpochSecond());
            String url = releaseService.getUrl(maxRelease, resultFile);
            upgradeRespVO.setDownloadUrl(url);
            return CommonResponse.success(upgradeRespVO);
        }
    }
}
