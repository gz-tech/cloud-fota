package com.upuphone.cloudplatform.fota.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 18:08
 */
@Getter
@Setter
public class VersionInfoListPO {

    private String versionId;

    private List<FileListPO> files;

    private String versionStatus;

    private LocalDateTime uploadTime;
}
