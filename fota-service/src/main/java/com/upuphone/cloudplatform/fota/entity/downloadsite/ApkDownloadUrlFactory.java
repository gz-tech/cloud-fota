package com.upuphone.cloudplatform.fota.entity.downloadsite;

import com.upuphone.cloudplatform.fota.service.DownloadUrlService;

public interface ApkDownloadUrlFactory {
    DownloadUrlService createDownloadInstance();
}
