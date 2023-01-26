package com.upuphone.cloudplatform.fota.converter;

import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.bo.ReleaseBO;
import com.upuphone.cloudplatform.fota.entity.VersionPO;
import com.upuphone.cloudplatform.fota.vo.request.VersionInfoListReqVO;
import com.upuphone.cloudplatform.fota.vo.response.UpgradeRespVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-29T21:48:57+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Azul Systems, Inc.)"
)
public class VersionConverterImpl implements VersionConverter {

    @Override
    public UpgradeRespVO mergeVo(ReleaseBO releaseBO, FileBO fileBO) {
        if ( releaseBO == null && fileBO == null ) {
            return null;
        }

        UpgradeRespVO upgradeRespVO = new UpgradeRespVO();

        if ( releaseBO != null ) {
            upgradeRespVO.setVersionId( releaseBO.getVersionId() );
            upgradeRespVO.setVersionName( releaseBO.getReleaseName() );
            upgradeRespVO.setVersionDescription( releaseBO.getDescription() );
        }
        if ( fileBO != null ) {
            upgradeRespVO.setFileName( fileBO.getName() );
            upgradeRespVO.setMd5( fileBO.getMd5() );
            if ( fileBO.getSize() != null ) {
                upgradeRespVO.setFileSize( Long.parseLong( fileBO.getSize() ) );
            }
        }

        return upgradeRespVO;
    }

    @Override
    public VersionPO versionListVO2PO(VersionInfoListReqVO versionInfoListReqVO) {
        if ( versionInfoListReqVO == null ) {
            return null;
        }

        VersionPO versionPO = new VersionPO();

        versionPO.setStartTime( versionInfoListReqVO.getStartTime() );
        versionPO.setEndTime( versionInfoListReqVO.getEndTime() );
        versionPO.setVersionId( versionInfoListReqVO.getVersionId() );
        versionPO.setStatus( versionInfoListReqVO.getVersionStatus() );

        return versionPO;
    }
}
