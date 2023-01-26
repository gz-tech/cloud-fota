package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.entity.FilePO;
import com.upuphone.cloudplatform.fota.vo.request.FileInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.response.FileDetailRespVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-29T21:48:57+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Azul Systems, Inc.)"
)
public class FileInfoConverterImpl implements FileInfoConverter {

    @Override
    public FilePO vo2Do(FileInfoReqVO fileInfoReqVO) {
        if ( fileInfoReqVO == null ) {
            return null;
        }

        FilePO filePO = new FilePO();

        filePO.setName( fileInfoReqVO.getFileName() );
        filePO.setSize( fileInfoReqVO.getFileSize() );
        filePO.setType( fileInfoReqVO.getFileType() );
        filePO.setStorageId( fileInfoReqVO.getStorageId() );
        filePO.setMd5( fileInfoReqVO.getMd5() );
        filePO.setVersionId( fileInfoReqVO.getVersionId() );
        filePO.setFromVersionId( fileInfoReqVO.getFromVersionId() );
        filePO.setStatus( fileInfoReqVO.getStatus() );

        return filePO;
    }

    @Override
    public FileDetailRespVO po2Detailvo(FilePO filePO) {
        if ( filePO == null ) {
            return null;
        }

        FileDetailRespVO fileDetailRespVO = new FileDetailRespVO();

        fileDetailRespVO.setVersionId( filePO.getVersionId() );
        fileDetailRespVO.setFromVersionId( filePO.getFromVersionId() );
        fileDetailRespVO.setName( filePO.getName() );
        fileDetailRespVO.setFileSize( filePO.getSize() );
        fileDetailRespVO.setFileType( filePO.getType() );
        fileDetailRespVO.setStatus( filePO.getStatus() );

        return fileDetailRespVO;
    }
}
