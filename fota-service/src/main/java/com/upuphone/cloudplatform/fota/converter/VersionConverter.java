package com.upuphone.cloudplatform.fota.converter;


import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.bo.VersionBO;
import com.upuphone.cloudplatform.fota.entity.VersionPO;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.request.VersionListReqVO;
import com.upuphone.cloudplatform.fota.vo.response.UpgradeRespVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionDetailRespVO;
import com.upuphone.cloudplatform.fota.vo.response.VersionInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VersionConverter {
    VersionConverter INSTANCE = Mappers.getMapper(VersionConverter.class);
    @Mappings({
            @Mapping(source = "releaseBO.versionId", target = "versionId"),
            @Mapping(source = "releaseBO.releaseName", target = "versionName"),
            @Mapping(source = "releaseBO.description", target = "versionDescription"),
            @Mapping(source = "fileBO.name", target = "fileName"),
            @Mapping(source = "fileBO.md5", target = "md5"),
            @Mapping(source = "fileBO.size", target = "fileSize")
    })
    UpgradeRespVO mergeVo(ReleaseBO releaseBO, FileBO fileBO);

    @Mappings({
            @Mapping(source = "versionId", target = "versionId"),
            @Mapping(source = "description", target = "description")
    })
    VersionPO bo2Po(VersionBO versionBO);

    @Mappings({
            @Mapping(source = "versionName", target = "name")
    })
    VersionPO versionListReqVO2VersionInfo(VersionListReqVO versionListReqVO);

    @Mappings({
            @Mapping(source = "versionId", target = "versionId"),
            @Mapping(source = "name", target = "versionName"),
            @Mapping(source = "status", target = "status")
    }
    )
    VersionDetailRespVO versionInfo2VersionDetailVO(VersionPO versionPO);

    @Mappings({
            @Mapping(source = "startTime", target = "startTime"),
            @Mapping(source = "endTime", target = "endTime"),
            @Mapping(source = "versionId", target = "versionId"),
            @Mapping(source = "versionStatus", target = "status")
    }
    )
    VersionPO versionListVO2PO(VersionInfoListReqVO versionInfoListReqVO);


    @Mappings({
            @Mapping(source = "versionId", target = "versionId"),
            //TODO 暂时用versionId当verisonName
            @Mapping(source = "versionId", target = "versionName"),
            @Mapping(source = "description", target = "versionDescription")
    }
    )
    VersionInfoRespVO bo2vo(VersionBO bo);
}
