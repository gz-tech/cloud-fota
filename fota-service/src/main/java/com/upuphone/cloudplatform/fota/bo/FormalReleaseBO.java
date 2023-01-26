package com.upuphone.cloudplatform.fota.bo;

import lombok.Data;

import java.util.List;

/**
 * @Classname FormalReleaseListBO
 * @Description
 * @Date 2022/2/17 2:58 下午
 * @Created by gz-d
 */
@Data
public class FormalReleaseBO {
    private String versionId;

    private String operator;

    private String description;

    private List<ReleaseBO> releases;
}
