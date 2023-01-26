package com.upuphone.cloudplatform.fota.service;

import com.upuphone.cloudplatform.fota.bo.FileBO;

public interface DownloadUrlService {
    String getUrl(FileBO fileBO);
}
