package com.upuphone.cloudplatform.fota.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.common.response.CommonErrorCode;
import com.upuphone.cloudplatform.fota.FotaErrorCode;
import com.upuphone.cloudplatform.fota.OrikaUtil;
import com.upuphone.cloudplatform.fota.bo.DeviceBO;
import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.config.FotaSetting;
import com.upuphone.cloudplatform.fota.constant.ServiceConstant;
import com.upuphone.cloudplatform.fota.entity.FilePO;
import com.upuphone.cloudplatform.fota.entity.FormalReleaseListPO;
import com.upuphone.cloudplatform.fota.entity.OperationPO;
import com.upuphone.cloudplatform.fota.entity.ReleasePO;
import com.upuphone.cloudplatform.fota.entity.downloadsite.ApkDownloadUrlFactory;
import com.upuphone.cloudplatform.fota.entity.downloadsite.ObsDownloadUrlServiceFactory;
import com.upuphone.cloudplatform.fota.mapper.FotaFileMapper;
import com.upuphone.cloudplatform.fota.mapper.FotaVersionMapper;
import com.upuphone.cloudplatform.fota.mapper.OperationRecordMapper;
import com.upuphone.cloudplatform.fota.mapper.ReleaseMapper;
import com.upuphone.cloudplatform.fota.service.DownloadUrlService;
import com.upuphone.cloudplatform.fota.service.ReleaseService;
import com.upuphone.cloudplatform.fota.service.VersionService;
import com.upuphone.cloudplatform.fota.util.AESUtils;
import com.upuphone.cloudplatform.fota.util.ObsUtil;
import com.upuphone.cloudplatform.fota.util.SnowFlakeUtil;
import com.upuphone.cloudplatform.fota.vo.response.FormalReleaseListRespVO;
import com.upuphone.cloudplatform.fota.vo.response.FormalReleasePageRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.upuphone.cloudplatform.fota.DictConstant.*;
import static com.upuphone.cloudplatform.fota.constant.ServiceConstant.VERSION_REGEXP;

/**
 * @Classname ReleaseServiceImpl
 * @Description
 * @Date 2022/2/17 3:05 下午
 * @Created by gz-d
 */
@Service
@Slf4j
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    private ReleaseMapper releaseMapper;

    @Autowired
    private FotaFileMapper fotaFileMapper;

    @Autowired
    private FotaVersionMapper fotaVersionMapper;

    @Autowired
    private OperationRecordMapper operationRecordMapper;

    @Autowired
    private ReleaseServiceImpl releaseService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private FotaSetting fotaSetting;

    @Override
    public FormalReleasePageRespVO selectFormalReleaseByPage(ReleaseBO releaseBO, int pageNum, int pageSize) {
        //根据搜索条件查找正式发布的release
        releaseBO.setType(FORMAL_RELEASE);
        List<ReleasePO> searchReleaseList = releaseMapper.selectReleaseList(OrikaUtil.map(releaseBO, ReleasePO.class));
        List<String> versionsInFormalRelease = searchReleaseList.stream().map(e->e.getVersionId()).collect(Collectors.toList());
        Page<FormalReleaseListPO> page = Page.of(pageNum, pageSize);
        IPage<FormalReleaseListPO> formalReleaseListByPage = releaseMapper.releaseListByPage(page, versionsInFormalRelease);
//        Collections.sort(formalReleaseListByPage.getRecords());
        PageDTO<FormalReleaseListRespVO> result = new PageDTO<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal()
        );
        result.setRecords(OrikaUtil.mapAsList(formalReleaseListByPage.getRecords(), FormalReleaseListRespVO.class));
        FormalReleasePageRespVO formalReleasePageRespVO = new FormalReleasePageRespVO();
        formalReleasePageRespVO.setPageDTO(result);
        //真正命中的发布 用于前台标记
        List<String> releaseList = searchReleaseList.stream().map(e->e.getReleaseId()).collect(Collectors.toList());
        formalReleasePageRespVO.setTargetReleaseIds(releaseList);
        return formalReleasePageRespVO;
    }

    @Override
    public PageDTO<ReleaseBO> testReleaseByPage(ReleaseBO releaseBO, int pageNum, int pageSize) {
        Page<ReleasePO> page = Page.of(pageNum, pageSize);
        ReleasePO releasePO = OrikaUtil.map(releaseBO, ReleasePO.class);
        IPage<ReleasePO> releasePOIPage = releaseMapper.testReleaseListByPage(page, releasePO);
        List<ReleaseBO> releaseBOList = OrikaUtil.mapAsList(releasePOIPage.getRecords(), ReleaseBO.class);
        List<String> releaseIds = releasePOIPage.getRecords().stream().map(e->e.getReleaseId())
                .collect(Collectors.toList());
        List<OperationPO> operationList = operationRecordMapper.selectOperation(releaseIds, "TEST");

        HashMap<String, ReleaseBO> map = new HashMap<>();
        for (ReleaseBO bo:
             releaseBOList) {
            map.put(bo.getReleaseId(), bo);
        }
        for (OperationPO po:
             operationList) {
            String releaseId = po.getRelationId();
            ReleaseBO releaseBO1 = map.get(releaseId);
            if (releaseBO1 == null) {
                continue;
            }
            releaseBO1.setOperationTime(po.getOperationTime());
            releaseBO1.setOperator(po.getOperator());
        }
        PageDTO<ReleaseBO> releaseBOPageDTO = new PageDTO<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal()
        );
        releaseBOPageDTO.setRecords(releaseBOList);
        return releaseBOPageDTO;
    }

    @Override
    public void releaseUpdateByVersionId(ReleaseBO releaseBO) {
        ReleasePO releasePO = OrikaUtil.map(releaseBO, ReleasePO.class);

        releaseMapper.updateByVersionId(releasePO);
    }

    @Override
    public void releaseUpdateByReleaseId(ReleaseBO releaseBO) {
        ReleasePO releasePO = OrikaUtil.map(releaseBO, ReleasePO.class);
        UpdateWrapper<ReleasePO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("release_id", releaseBO.getReleaseId());
        releaseMapper.update(releasePO, updateWrapper);
    }

    @Override
    public String releaseAdd(ReleaseBO releaseBO) {
        if (TEST_RELEASE.equals(releaseBO.getType())) {
            if(!CollectionUtils.isEmpty(releaseMapper.selectExistTestRelease(releaseBO.getVersionId()))) {
                log.info("版本{}已处于待测试或测试中", releaseBO.getVersionId());
                throw new BusinessException(FotaErrorCode.TEST_RELEASE_EXIST);
            }
        }
        ReleasePO findExistOne = releaseMapper.selectOne(Wrappers.<ReleasePO>lambdaQuery()
        .eq(ReleasePO::getReleaseName, releaseBO.getReleaseName()));
        if (findExistOne != null && releaseBO.getType().equals(findExistOne.getType())) {
            throw new BusinessException(CommonErrorCode.PARAM_ERROR, "版本名重复");
        }
        ReleasePO releasePO = OrikaUtil.map(releaseBO, ReleasePO.class);
        releasePO.setReleaseId(SnowFlakeUtil.getSnowFlakeId());
        releaseMapper.insert(releasePO);
        return releasePO.getReleaseId();
    }

    @Override
    public ReleaseBO releaseDetail(String releaseId) {
        ReleasePO releasePO = releaseMapper.selectById(releaseId);
        return OrikaUtil.map(releasePO, ReleaseBO.class);
    }

    @Override
    public List<ReleaseBO> selectPlanListByVersionId(String versionId) {
        List<ReleasePO> releasePOList = releaseMapper.selectList(Wrappers.<ReleasePO>lambdaQuery()
                .eq(ReleasePO::getVersionId, versionId)
                .eq(ReleasePO::getType, FORMAL_RELEASE));
        return OrikaUtil.mapAsList(releasePOList, ReleaseBO.class);
    }

    @Override
    public void releaseVersion() {
        //待测试->测试中
        releaseService.testRelease();
        //正式版公开发布，文件先复制到公开桶
        releaseToPublic();
    }

    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void testRelease() {
        //获取需要变成测试中的版本id
        List<String> versionInTestList = releaseMapper.selectVersionToRelease(TEST_TO_RELEASE);
        if (versionInTestList.size() != 0) {
            //把对应的文件变成测试中状态
            fotaFileMapper.updateFileStatusByVersionId(versionInTestList, FILE_IN_TEST);
            //版本变成测试中状态
            fotaVersionMapper.updateVersionStatusByVersionId(versionInTestList, VERSION_IN_TEST);
        }
        //变更待测试到测试中
        releaseMapper.release(TEST_TO_RELEASE, TEST_IN_RELEASE);
    }

    public void releaseToPublic() {
        List<FilePO> filesToPublic = fotaFileMapper.selectFilesToPublic();
        Set<String> versions = filesToPublic.stream().map(e->e.getVersionId()).collect(Collectors.toSet());
        for (String versionId:
             versions) {
            releaseService.releaseOne(filesToPublic, versionId);
        }
    }

    @Transactional(rollbackFor = Exception.class, timeout = 300)
    public void releaseOne(List<FilePO> filesToPublic, String versionId) {
        for (FilePO file:
                filesToPublic) {
            if (file.getVersionId().equals(versionId)) {
                ObsUtil.copyObject(fotaSetting.getAk(), fotaSetting.getSk(), fotaSetting.getEndPoint(),
                        file.getBucketName(), file.getObjectName(),
                        fotaSetting.getPublicBucket(), file.getObjectName());
            }
        }
        //变更待发布到发布中
        releaseMapper.formalVersionRelease(FORMAL_TO_RELEASE, FORMAL_IN_RELEASE, versionId);
    }

    @Override
    public ReleaseBO getMaxAvailableVersion(DeviceBO deviceBO) {
        //带major minor build patch号
        List<ReleasePO> allRelease = releaseMapper.selectRelease();
        Collections.sort(allRelease);
        ReleasePO result = new ReleasePO();
        for (ReleasePO r:
                allRelease) {
            if (Objects.equals(r.getStatus(), FORMAL_IN_RELEASE)) {
                //对外发布，ALL或者imei号符合
                if (RANGE_ALL.equals(r.getUpgradeRangeType())
                        || testImei(r.getImei(), deviceBO.getDeviceCode())) {
                    result = r;
                    break;
                }
            } else {
                //测试发布，必须imei号匹配
                if (testImei(r.getImei(), deviceBO.getDeviceCode())) {
                    result = r;
                    break;
                }
            }
        }
        ReleasePO currrentVersion = getCurrentVersionInfo(deviceBO.getVersionId());
        if (currrentVersion.compareTo(result) < 0) {
            log.info("能更新的最大版本小于当前版本");
            return null;
        }
        return OrikaUtil.map(result, ReleaseBO.class);
    }

    boolean testImei(String imeis, String deviceCode) {
        return (Strings.isBlank(imeis) || isDeviceInImeis(imeis, deviceCode));
    }

    boolean isDeviceInImeis(String imeis, String deviceCode) {
        List<String> imeiList =  new ArrayList<>();
        Collections.addAll(imeiList, imeis.split(","));

        String imei = AESUtils.decrypt(deviceCode);
        if(imei == null) {
            throw new BusinessException(CommonErrorCode.PARAM_ERROR, "非法设备号");
        }
        return imeiList.contains(imei) ? true : false;
    }

    ReleasePO getCurrentVersionInfo(String versionId) {
        String []s = versionService.getMatchStringFromVersionId(versionId).split("\\.");
        ReleasePO releasePO = new ReleasePO();
        releasePO.setMajor(Integer.parseInt(s[0]));
        releasePO.setMinor(Integer.parseInt(s[1]));
        releasePO.setBuild(Integer.parseInt(s[2]));
        releasePO.setPatch(Integer.parseInt(s[3]));
        return releasePO;
    }

    @Override
    public boolean repeatNameRelease(String releaseName, String releaseId, String type) {
        ReleasePO releasePO = new ReleasePO();
        releasePO.setReleaseId(releaseId);
        releasePO.setReleaseName(releaseName);
        releasePO.setType(type);
        return releaseMapper.selectExistNameRelease(releasePO) == null;
    }

    @Override
    public String getUrl(ReleaseBO maxRelease, FileBO resultFile) {
        String result = null;
        if (TEST_RELEASE.equals(maxRelease.getType())) {
            ApkDownloadUrlFactory apkDownloadUrlFactory = new ObsDownloadUrlServiceFactory();
            DownloadUrlService downloadUrlService = apkDownloadUrlFactory.createDownloadInstance();
            result = downloadUrlService.getUrl(resultFile);
        } else {
            StringBuilder url = new StringBuilder();
            try {
                url.append("https://").append(fotaSetting.getPublicBucket()).append(".")
                        .append(fotaSetting.getEndPoint()).append("/")
                        .append(URLEncoder.encode(resultFile.getObjectName(), "UTF-8"));
                result = url.toString();
            } catch (UnsupportedEncodingException e) {
                log.error("生成url出错");
                throw new BusinessException(CommonErrorCode.SERVICE_ERROR);
            }
        }
        return result;
    }

}
