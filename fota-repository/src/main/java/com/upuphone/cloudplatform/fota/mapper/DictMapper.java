package com.upuphone.cloudplatform.fota.mapper;

import com.upuphone.cloudplatform.fota.entity.DictEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/29 18:40
 */
@Mapper
public interface DictMapper {
    List<DictEntity> selectDictByFlnm(@Param("flnm") String flnm);
}
