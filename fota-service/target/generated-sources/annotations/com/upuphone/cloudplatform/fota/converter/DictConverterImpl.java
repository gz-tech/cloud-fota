package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.DictEntity;
import com.upuphone.cloudplatform.fota.vo.response.DictRespVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-29T21:48:57+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Azul Systems, Inc.)"
)
public class DictConverterImpl implements DictConverter {

    @Override
    public DictRespVO do2Vo(DictEntity dictEntity) {
        if ( dictEntity == null ) {
            return null;
        }

        DictRespVO dictRespVO = new DictRespVO();

        dictRespVO.setCode( dictEntity.getCode() );
        dictRespVO.setText( dictEntity.getText() );

        return dictRespVO;
    }

    @Override
    public List<DictRespVO> do2Volist(List<DictEntity> dictEntityList) {
        if ( dictEntityList == null ) {
            return null;
        }

        List<DictRespVO> list = new ArrayList<DictRespVO>( dictEntityList.size() );
        for ( DictEntity dictEntity : dictEntityList ) {
            list.add( do2Vo( dictEntity ) );
        }

        return list;
    }
}
