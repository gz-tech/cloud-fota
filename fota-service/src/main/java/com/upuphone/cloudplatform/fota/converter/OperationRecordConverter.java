package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.OperationRecord;
import com.upuphone.cloudplatform.fota.entity.VersionPO;
import com.upuphone.cloudplatform.fota.vo.response.OperationListRespVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionListRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 16:10
 */
@Mapper
public interface OperationRecordConverter {
    OperationRecordConverter INSTANCE = Mappers.getMapper(OperationRecordConverter.class);

    @Mappings({
            @Mapping(source = "versionPO.versionId", target = "versionId"),
            @Mapping(source = "versionPO.name", target = "versionName"),
            @Mapping(source = "versionPO.description", target = "description"),
            @Mapping(source = "versionPO.status", target = "status"),
            @Mapping(source = "versionPO.statusName", target = "statusName"),
            @Mapping(source = "operationRecord.userName", target = "operator"),
            @Mapping(source = "operationRecord.time", target = "operationTime")
    })
    VersionListRespVO mergeVO(VersionPO versionPO, OperationRecord operationRecord);

    @Mappings({
            @Mapping(source = "time", target = "operationTime"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "userId", target = "operator")
    })
    OperationListRespVO operationRecord2RecordRespVO(OperationRecord operationRecord);

    List<OperationListRespVO> operationRecord2RecordRespVOs(List<OperationRecord> operationRecords);
}
