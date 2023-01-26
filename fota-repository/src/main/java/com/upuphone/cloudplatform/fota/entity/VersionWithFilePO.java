package com.upuphone.cloudplatform.fota.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Classname VersionWithFilePO
 * @Description
 * @Date 2022/3/2 6:22 下午
 * @Created by gz-d
 */
@Getter
@Setter
public class VersionWithFilePO {
    private String versionId;

    private List<String> files;
}
