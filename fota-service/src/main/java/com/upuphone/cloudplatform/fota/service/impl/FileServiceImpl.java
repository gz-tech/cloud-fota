package com.upuphone.cloudplatform.fota.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.fota.FotaErrorCode;
import com.upuphone.cloudplatform.fota.OrikaUtil;
import com.upuphone.cloudplatform.fota.bo.FileBO;
import com.upuphone.cloudplatform.fota.converter.FileInfoConverter;
import com.upuphone.cloudplatform.fota.entity.FilePO;
import com.upuphone.cloudplatform.fota.mapper.FotaFileMapper;
import com.upuphone.cloudplatform.fota.service.FileService;
import com.upuphone.cloudplatform.fota.util.SnowFlakeUtil;
import com.upuphone.cloudplatform.fota.vo.request.FileInfoReqVO;
import com.upuphone.cloudplatform.fota.vo.response.FileDetailRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname FileServiceImpl
 * @Description
 * @Date 2022/1/21 2:53 下午
 * @Created by gz-d
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private FotaFileMapper fotaFileMapper;

    @Override
    @Transactional(rollbackFor=Exception.class, timeout = 20)
    public void addFile(FileInfoReqVO fileInfoReqVO) {
        List<FilePO> existFile = fotaFileMapper.selectList(Wrappers.<FilePO>lambdaQuery()
                .eq(FilePO::getVersionId, fileInfoReqVO.getVersionId())
                .eq(FilePO::getType, fileInfoReqVO.getFileType())
                .eq(FilePO::getFromVersionId, fileInfoReqVO.getFromVersionId()));
        if (existFile.size() == 0) {
            log.info("没找到重复的包，可以新增");
            FilePO filePO = FileInfoConverter.INSTANCE.vo2Do(fileInfoReqVO);
            filePO.setFileId(SnowFlakeUtil.getSnowFlakeId());
            fotaFileMapper.addFile(filePO);
        } else {
            log.info("版本号[{}]起始版本号[{}]的包已存在 ", fileInfoReqVO.getVersionId(), existFile.get(0).getFromVersionId());
            throw new BusinessException(FotaErrorCode.REPEATED_FILE_ERROR);
        }
    }

    @Override
    public FileDetailRespVO fileDetail(String fileId) {
        FilePO filePO = fotaFileMapper.selectOne(Wrappers.<FilePO>lambdaQuery().eq(FilePO::getFileId, fileId));
        if (filePO == null) {
            throw new BusinessException(FotaErrorCode.NOT_FOUND_ERROR);
        }
        return FileInfoConverter.INSTANCE.po2Detailvo(filePO);
    }

    @Override
    public void fileUpdateByVersionId(FileBO fileBO) {
        FilePO filePO = OrikaUtil.map(fileBO, FilePO.class);
        UpdateWrapper<FilePO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("version_id", fileBO.getVersionId());
        fotaFileMapper.update(filePO, updateWrapper);
    }

    @Override
    public List<FileBO> fileListByVersionId(String versionId) {
        List<String> versionIds = new ArrayList<>();
        versionIds.add(versionId);
        List<FilePO> fileBOList = fotaFileMapper.selectFilesByVersionIds(versionIds);
        return OrikaUtil.mapAsList(fileBOList, FileBO.class);
    }

    @Override
    public void fileUpdateByFileIds(List<String> files, String status) {
        fotaFileMapper.updateFilesByFileIds(files, status);
    }
}
