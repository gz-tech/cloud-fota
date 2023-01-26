package com.upuphone.cloudplatform.fota.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.common.response.CommonErrorCode;
import com.upuphone.cloudplatform.fota.OrikaUtil;
import com.upuphone.cloudplatform.fota.bo.VersionBO;
import com.upuphone.cloudplatform.fota.bo.VersionWithFileBO;
import com.upuphone.cloudplatform.fota.converter.VersionConverter;
import com.upuphone.cloudplatform.fota.entity.*;
import com.upuphone.cloudplatform.fota.mapper.FotaFileMapper;
import com.upuphone.cloudplatform.fota.mapper.FotaVersionMapper;
import com.upuphone.cloudplatform.fota.mapper.ReleaseMapper;
import com.upuphone.cloudplatform.fota.service.VersionService;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionInfoListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.upuphone.cloudplatform.fota.DictConstant.*;
import static com.upuphone.cloudplatform.fota.constant.ServiceConstant.VERSION_REGEXP;

@Service
@Slf4j
public class VersionServiceImpl implements VersionService {
    @Autowired
    private FotaVersionMapper fotaVersionMapper;

    @Autowired
    private FotaFileMapper fotaFileMapper;

    @Autowired
    private ReleaseMapper releaseMapper;

    @Override
    public PageDTO<VersionInfoListRespVO> versionListByPage(VersionInfoListReqVO versionInfoListReqVO) {
        VersionPO versionPO = VersionConverter.INSTANCE.versionListVO2PO(versionInfoListReqVO);
        Page<VersionInfoListPO> page = Page.of(versionInfoListReqVO.getPageNum(), versionInfoListReqVO.getPageSize());
        IPage<VersionInfoListPO> versionListByPage = fotaVersionMapper.versionListByPage(page,versionPO);
        PageDTO<VersionInfoListRespVO> fileInfoListRespVOPageDTO = new PageDTO<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal()
        );
        List<VersionInfoListRespVO> versionInfoListRespVOS =
                OrikaUtil.mapAsList(versionListByPage.getRecords(), VersionInfoListRespVO.class);
        fileInfoListRespVOPageDTO.setRecords(versionInfoListRespVOS);
        return fileInfoListRespVOPageDTO;
    }

    @Override
    public void versionUpdate(VersionBO versionBO) {
        VersionPO versionPO = OrikaUtil.map(versionBO, VersionPO.class);
        fotaVersionMapper.updateVersion(versionPO);
    }

    @Override
    public List<VersionWithFileBO> versionWithFileList(VersionBO versionBO) {
        List<VersionWithFileBO> versionWithFileBOS = new ArrayList<>();
        //可以选为新建测试的版本号必须是待测试状态 且 如果改版本的测试发布存在，该发布的状态不能为待测试或测试中
        List<VersionPO> allVersions = fotaVersionMapper.selectList(Wrappers.<VersionPO>lambdaQuery()
                .eq(VersionPO::getStatus, VERSION_TO_TEST));
        Collections.sort(allVersions);
        QueryWrapper<ReleasePO> queryWrapper = new QueryWrapper<ReleasePO>();
        queryWrapper.eq("type", TEST_RELEASE);
        queryWrapper.and(wrapper -> wrapper.eq("status", TEST_TO_RELEASE).or().eq("status", TEST_IN_RELEASE));
        List<ReleasePO> testReleaseListInReleaseOrToRelease = releaseMapper.selectList(queryWrapper);
        List<String> testVersionsInReleaseOrToRelease = testReleaseListInReleaseOrToRelease.stream()
                .map(e->e.getVersionId()).collect(Collectors.toList());
        Iterator<VersionPO> it = allVersions.listIterator();
        while(it.hasNext()) {
            if (testVersionsInReleaseOrToRelease.contains(it.next().getVersionId())) {
                it.remove();
            }
        }
        List<String> versionsAvailableForTest = allVersions.stream().map(e->e.getVersionId()).collect(Collectors.toList());
        List<FilePO> filePOList = fotaFileMapper.selectFilesByVersionIds(new ArrayList<>(versionsAvailableForTest));
        for (String versionId:
                versionsAvailableForTest) {
            List<String> files = new ArrayList<>();
            for (FilePO filePO:
                 filePOList) {
                if (filePO.getVersionId().equals(versionId)) {
                    files.add(filePO.getName());
                }
            }
            VersionWithFileBO versionWithFileBO = new VersionWithFileBO();
            versionWithFileBO.setVersionId(versionId);
            versionWithFileBO.setFiles(files);
            versionWithFileBOS.add(versionWithFileBO);
        }
        return versionWithFileBOS;
    }

    public List<VersionWithFileBO> selectVersionToformalRelease(VersionBO versionBO) {
        VersionPO versionPO = OrikaUtil.map(versionBO, VersionPO.class);
        List<VersionWithFilePO> versionWithFilePOS = fotaVersionMapper.selectVersionWithFile(versionPO);
        return OrikaUtil.mapAsList(versionWithFilePOS, VersionWithFileBO.class);
    }

    @Override
    public String getMatchStringFromVersionId(String versionId) {
        Pattern r = Pattern.compile(VERSION_REGEXP);
        Matcher m = r.matcher(versionId);
        String matchString = null;
        if (m.find()) {
            matchString = m.group(0);
        } else {
            log.error("版本号错误");
            throw new BusinessException(CommonErrorCode.PARAM_ERROR,"版本号错误");
        }
        return matchString;
    }


    @Override
    public void addVersion(VersionBO versionBO) {
        String []s = getMatchStringFromVersionId(versionBO.getVersionId()).split("\\.");
        VersionPO versionPO = OrikaUtil.map(versionBO, VersionPO.class);
        versionPO.setMajor(Integer.parseInt(s[0]));
        versionPO.setMinor(Integer.parseInt(s[1]));
        versionPO.setBuild(Integer.parseInt(s[2]));
        versionPO.setPatch(Integer.parseInt(s[3]));
        fotaVersionMapper.insert(versionPO);
    }

    @Override
    public List<String> selectVersionList() {
        List<VersionPO> versionPOList = fotaVersionMapper.selectList(Wrappers.lambdaQuery());
        Collections.sort(versionPOList);
        List<String> result = versionPOList.stream().map(e->e.getVersionId()).collect(Collectors.toList());
        return result;
    }

    @Override
    public VersionBO selectVersionById(String versionId) {
        VersionPO versionPO = fotaVersionMapper.selectVersionById(versionId);
        return OrikaUtil.map(versionPO, VersionBO.class);
    }
}
