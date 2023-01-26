package com.upuphone.cloudplatform.fota.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Classname FileBO
 * @Description
 * @Date 2022/2/17 11:31 上午
 * @Created by gz-d
 */
@Setter
@Getter
public class FileBO {

    private String fileId;

    private String versionId;

    private String fromVersionId;

    private String name;

    private String type;

    private String status;

    private String delFlag;

    private String ak;

    private String sk;

    private String objectName;

    private String endPoint;

    private String bucketName;

    private String md5;

    private String size;
}
