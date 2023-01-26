package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.DictEntity;
import com.upuphone.cloudplatform.fota.vo.response.DictRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictConverter {
    DictConverter INSTANCE = Mappers.getMapper(DictConverter.class);

    @Mappings({
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "text", target = "text")
    })
    DictRespVO do2Vo(DictEntity dictEntity);

    List<DictRespVO> do2Volist(List<DictEntity> dictEntityList);

}
