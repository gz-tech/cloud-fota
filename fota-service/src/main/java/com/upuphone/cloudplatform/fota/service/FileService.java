package com.upuphone.cloudplatform.fota.service;

import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.vo.request.FileInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.response.FileDetailRespVO;

import java.util.List;

/**
 * @Classname FileService
 * @Description
 * @Date 2022/1/21 2:52 下午
 * @Created by gz-d
 */
public interface FileService {
    void addFile(FileInfoReqVO fileInfoReqVO);

    FileDetailRespVO fileDetail(String fileId);

    void fileUpdateByVersionId(FileBO fileBO);

    List<FileBO> fileListByVersionId(String versionId);

    void fileUpdateByFileIds(List<String> files, String status);
}
