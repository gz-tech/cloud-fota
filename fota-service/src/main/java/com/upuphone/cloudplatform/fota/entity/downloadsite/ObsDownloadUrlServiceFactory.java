package com.upuphone.cloudplatform.fota.entity.downloadsite;

import com.upuphone.cloudplatform.common.utils.SpringUtil;
import com.upuphone.cloudplatform.fota.service.DownloadUrlService;
import com.upuphone.cloudplatform.fota.service.impl.ObsDownloadUrlServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ObsDownloadUrlServiceFactory implements ApkDownloadUrlFactory{

    @Override
    public DownloadUrlService createDownloadInstance() {
        ObsDownloadUrlServiceImpl bean = SpringUtil.getBean(ObsDownloadUrlServiceImpl.class);
        return bean;
    }

}
