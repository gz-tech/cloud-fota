package com.upuphone.cloudplatform.fota.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upuphone.cloudplatform.fota.entity.TestReleaseVersionPO;
import com.upuphone.cloudplatform.fota.entity.VersionInfoListPO;
import com.upuphone.cloudplatform.fota.entity.VersionPO;
import com.upuphone.cloudplatform.fota.entity.VersionWithFilePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FotaVersionMapper extends BaseMapper<VersionPO> {

    int updateVersion(VersionPO versionPO);

    VersionPO selectVersionById(@Param("versionId") String versionId);

    IPage<VersionInfoListPO> versionListByPage(Page<VersionInfoListPO> page, @Param("version") VersionPO versionPO);

    void updateVersionStatusByVersionId(@Param("versionId") List<String> versionId, @Param("status") String status);

    List<TestReleaseVersionPO>  testReleaseVersion();

    List<VersionWithFilePO> selectVersionWithFile(@Param("version") VersionPO versionPO);

}
