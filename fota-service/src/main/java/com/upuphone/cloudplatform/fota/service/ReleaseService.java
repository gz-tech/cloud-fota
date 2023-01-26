package com.upuphone.cloudplatform.fota.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.upuphone.cloudplatform.fota.bo.DeviceBO;
import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.vo.response.FormalReleasePageRespVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ReleaseService
 * @Description
 * @Date 2022/2/17 2:37 下午
 * @Created by gz-d
 */
@Service
public interface ReleaseService {

    FormalReleasePageRespVO selectFormalReleaseByPage(ReleaseBO releaseBO, int pageNum, int pageSize);

    PageDTO<ReleaseBO> testReleaseByPage(ReleaseBO releaseBO, int pageNum, int pageSize);

    void releaseUpdateByVersionId(ReleaseBO releaseBO);

    void releaseUpdateByReleaseId(ReleaseBO releaseBO);

    String releaseAdd(ReleaseBO releaseBO);

    ReleaseBO releaseDetail(String  releaseId);

    List<ReleaseBO> selectPlanListByVersionId(String versionId);

    void releaseVersion() throws InterruptedException;

    ReleaseBO getMaxAvailableVersion(DeviceBO deviceBO);

    boolean repeatNameRelease(String releaseName, String releaseId, String type);

    String getUrl(ReleaseBO maxRelease, FileBO resultFile);
}
