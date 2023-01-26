package com.upuphone.cloudplatform.fota.service;

import com.upuphone.cloudplatform.fota.vo.response.OperationListRespVO;

import java.util.List;

/**
 * @Classname OperationRecordService
 * @Description
 * @Date 2022/1/21 3:59 下午
 * @Created by gz-d
 */
public interface OperationRecordService {

    List<OperationListRespVO> listVersionOperation(String versionId, String module);

    void saveOperation(String content, String relationId, String type);
}
