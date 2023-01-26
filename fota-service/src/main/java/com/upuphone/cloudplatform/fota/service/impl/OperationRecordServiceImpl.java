package com.upuphone.cloudplatform.fota.service.impl;

import com.upuphone.cloudplatform.fota.converter.OperationRecordConverter;
import com.upuphone.cloudplatform.fota.entity.OperationRecord;
import com.upuphone.cloudplatform.fota.mapper.OperationRecordMapper;
import com.upuphone.cloudplatform.fota.service.OperationRecordService;
import com.upuphone.cloudplatform.fota.util.SnowFlakeUtil;
import com.upuphone.cloudplatform.fota.vo.response.OperationListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname OperationRecordServiceImpl
 * @Description TODO
 * @Date 2022/1/21 3:59 下午
 * @Created by gz-d
 */
@Service
@Slf4j
public class OperationRecordServiceImpl implements OperationRecordService {

    @Autowired
    private OperationRecordMapper operationRecordMapper;


    @Override
    public List<OperationListRespVO> listVersionOperation(String relationId, String module) {
        List<OperationRecord> operationRecordList = operationRecordMapper.listOperationByVersionId(relationId, module);
        List<OperationListRespVO> operationListRespVOList= OperationRecordConverter.INSTANCE.operationRecord2RecordRespVOs(operationRecordList);
        return operationListRespVOList;
    }

    @Override
    public void saveOperation(String content, String relationId, String module) {
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setOperationId(SnowFlakeUtil.getSnowFlakeId());
        operationRecord.setModule(module);
        operationRecord.setRelationId(relationId);
        operationRecord.setContent(content);
        operationRecordMapper.insert(operationRecord);
    }
}
