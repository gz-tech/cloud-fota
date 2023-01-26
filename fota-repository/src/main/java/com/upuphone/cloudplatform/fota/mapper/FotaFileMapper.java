package com.upuphone.cloudplatform.fota.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upuphone.cloudplatform.fota.entity.FilePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FotaFileMapper extends BaseMapper<FilePO> {

    int addFile(FilePO filePO);

    List<FilePO> selectFilesByVersionIds(@Param("versions") List<String> versionIds);

    void updateFilesByFileIds(@Param("fileIds") List<String> fileIds, @Param("status") String status);

    void updateFileStatusByVersionId(@Param("versionId") List<String> versionId, @Param("status") String status);

    List<FilePO> selectFilesToPublic();
}
