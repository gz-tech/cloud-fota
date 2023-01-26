package com.upuphone.cloudplatform.fota.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname ReleaseBO
 * @Description
 * @Date 2022/2/17 2:39 下午
 * @Created by gz-d
 */
@Data
public class ReleaseBO {
    private String releaseId;

    private String versionId;

    private String releaseName;

    private LocalDateTime releaseTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime operationTime;

    private String operator;

    private String status;

    private String upgradeType;

    private String upgradeRangeType;

    private String imei;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy = "admin";

    private String updateBy = "admin";

    //"FORMAL"-正式发布   "TEST"-测试发布
    private String type;

    //取消/下架原因
    private String reason;

    private String description;

    private String versionName;
}
