package com.upuphone.cloudplatform.fota.bo;

import lombok.Data;

import java.util.List;

/**
 * @Classname VersionWithFileBO
 * @Description
 * @Date 2022/2/17 7:56 下午
 * @Created by gz-d
 */
@Data
public class VersionWithFileBO {
    private String versionId;

    private List<String> files;
}
