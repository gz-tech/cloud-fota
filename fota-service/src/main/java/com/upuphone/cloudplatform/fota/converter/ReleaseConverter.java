package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.vo.response.TestReleaseListRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @Classname ReleaseConverter
 * @Description
 * @Date 2022/2/22 3:21 下午
 * @Created by gz-d
 */

@Mapper
public interface ReleaseConverter {
    @Mappings({
            @Mapping(source = "releaseId", target = "releaseId"),
            @Mapping(source = "releaseName", target = "releaseName"),
            @Mapping(source = "versionId", target = "versionId"),
            @Mapping(source = "status", target = "status")
    })
    TestReleaseListRespVO bo2Testvo(ReleaseBO releaseBO);
}
