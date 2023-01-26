package com.upuphone.cloudplatform.fota.service.impl;

import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.service.DownloadUrlService;
import com.upuphone.cloudplatform.fota.util.ObsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ObsDownloadUrlServiceImpl implements DownloadUrlService {

    @Value("${obs.endPoint}")
    private String endPoint;

    @Value("${obs.ak}")
    private String ak;

    @Value("${obs.sk}")
    private String sk;

    @Override
    public String getUrl(FileBO fileBO) {
        long expireSeconds = 3600L;
        String temporaryUrl = ObsUtil.temporarySignatureUrl(fileBO.getAk(), fileBO.getSk(),
                fileBO.getEndPoint(), fileBO.getBucketName(), fileBO.getObjectName(),expireSeconds);
        return temporaryUrl;
    }
}
