package com.upuphone.cloudplatform.fota.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.upuphone.cloudplatform.fota.bo.VersionBO;
import com.upuphone.cloudplatform.fota.bo.VersionWithFileBO;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionInfoListRespVO;

import java.util.List;

public interface VersionService {
    PageDTO<VersionInfoListRespVO> versionListByPage(VersionInfoListReqVO versionInfoListReqVO);

    List<VersionWithFileBO> versionWithFileList(VersionBO versionBO);

    void versionUpdate(VersionBO versionBO);

    VersionBO selectVersionById(String versionId);

    void addVersion(VersionBO versionBO);

    List<String> selectVersionList();

    List<VersionWithFileBO> selectVersionToformalRelease(VersionBO versionBO);

    String getMatchStringFromVersionId(String versionId);
}
