package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.Region;
import com.upuphone.cloudplatform.fota.vo.entity.RegionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/12/29 13:54
 */
@Mapper
public interface RegionConverter {
    RegionConverter INSTANCE = Mappers.getMapper(RegionConverter.class);

    @Mappings({
            @Mapping(source = "country", target = "country"),
            @Mapping(source = "state", target = "state"),
            @Mapping(source = "city", target = "city")
    })
    List<RegionVO> regionPO2VOList(List<Region> regionList);
}
