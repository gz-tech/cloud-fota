package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.OperationRecord;
import com.upuphone.cloudplatform.fota.vo.response.OperationListRespVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-29T21:48:57+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Azul Systems, Inc.)"
)
public class OperationRecordConverterImpl implements OperationRecordConverter {

    @Override
    public OperationListRespVO operationRecord2RecordRespVO(OperationRecord operationRecord) {
        if ( operationRecord == null ) {
            return null;
        }

        OperationListRespVO operationListRespVO = new OperationListRespVO();

        operationListRespVO.setOperationTime( operationRecord.getTime() );
        operationListRespVO.setContent( operationRecord.getContent() );
        operationListRespVO.setOperator( operationRecord.getUserId() );

        return operationListRespVO;
    }

    @Override
    public List<OperationListRespVO> operationRecord2RecordRespVOs(List<OperationRecord> operationRecords) {
        if ( operationRecords == null ) {
            return null;
        }

        List<OperationListRespVO> list = new ArrayList<OperationListRespVO>( operationRecords.size() );
        for ( OperationRecord operationRecord : operationRecords ) {
            list.add( operationRecord2RecordRespVO( operationRecord ) );
        }

        return list;
    }
}
