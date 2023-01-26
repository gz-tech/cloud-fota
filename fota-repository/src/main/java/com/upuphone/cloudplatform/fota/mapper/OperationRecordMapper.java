package com.upuphone.cloudplatform.fota.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upuphone.cloudplatform.fota.entity.OperationPO;
import com.upuphone.cloudplatform.fota.entity.OperationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 14:33
 */
@Mapper
public interface OperationRecordMapper extends BaseMapper<OperationRecord> {

    List<OperationRecord> listOperationByVersionId(@Param("relationId") String versionId, @Param("module") String module);

    List<OperationPO> selectOperation(@Param("relationIds") List<String> relationIds, @Param("module") String module);
}
