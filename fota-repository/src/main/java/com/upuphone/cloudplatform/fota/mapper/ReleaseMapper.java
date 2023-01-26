package com.upuphone.cloudplatform.fota.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upuphone.cloudplatform.fota.entity.FormalReleaseListPO;
import com.upuphone.cloudplatform.fota.entity.ReleasePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname ReleaseMapper
 * @Description
 * @Date 2022/2/17 2:23 下午
 * @Created by gz-d
 */
@Mapper
public interface ReleaseMapper extends BaseMapper<ReleasePO> {
    IPage<FormalReleaseListPO>  releaseListByPage(Page<FormalReleaseListPO> page, @Param("versions") List<String> versions);

    IPage<ReleasePO>  testReleaseListByPage(Page<ReleasePO> page, @Param("release") ReleasePO releasePO);

    void release(@Param("fromStatus")String fromStatus, @Param("toStatus")String toStatus);

    void formalVersionRelease(@Param("fromStatus")String fromStatus, @Param("toStatus")String toStatus,
                 @Param("versionId") String versionId);

    List<String> selectVersionToRelease(@Param("status") String status);

    List<ReleasePO> selectRelease();

    List<ReleasePO> selectReleaseList(@Param("release") ReleasePO releaseBO);

    void updateByVersionId(ReleasePO releasePO);

    ReleasePO selectExistNameRelease(ReleasePO releasePO);

    List<ReleasePO> selectExistTestRelease(@Param("versionId") String versionId);
}
