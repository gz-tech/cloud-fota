package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.FilePO;
import com.upuphone.cloudplatform.fota.vo.request.FileInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.response.FileDetailRespVO;
import com.upuphone.cloudplatform.fota.vo.response.FileInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FileInfoConverter {
    FileInfoConverter INSTANCE = Mappers.getMapper(FileInfoConverter.class);

    @Mappings({
            @Mapping(source = "fileName", target = "name"),
            @Mapping(source = "fileSize", target = "size"),
            @Mapping(source = "fileId", target = "fileId"),
            @Mapping(source = "fileType", target = "type"),
            @Mapping(source = "storageProviderName", target = "storageProvider")
    })
    FilePO vo2Do(FileInfoReqVO fileInfoReqVO);


    @Mappings({
            @Mapping(source = "versionId", target = "versionId"),
            @Mapping(source = "fromVersionId", target = "fromVersionId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "size", target = "fileSize"),
            @Mapping(source = "type", target = "fileType"),
            @Mapping(source = "status", target = "status")
    })
    FileDetailRespVO po2Detailvo(FilePO filePO);

    @Mappings({
            @Mapping(source = "name", target = "fileName"),
            @Mapping(source = "size", target = "fileSize"),
            @Mapping(source = "fileId", target = "fileId"),
            @Mapping(source = "type", target = "fileType"),
            @Mapping(source = "storageProvider", target = "storageProviderName")
    })
    FileInfoReqVO po2Vo(FilePO filePO);

    List<FileInfoReqVO> po2Volist(List<FilePO> filePOList);


    @Mappings({
            @Mapping(source = "versionId", target = "versionId"),
            @Mapping(source = "versionId", target = "name")
    })
    FilePO fileInfoListReqVO2FileInfo(VersionInfoListReqVO versionInfoListReqVO);


    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "fileId", target = "fileId")
    })
    FileInfoRespVO fileInfo2FileInfoResp(FilePO filePO);

    List<FileInfoRespVO> fileInfo2FileInfoRespList(List<FilePO> filePOList);
}
