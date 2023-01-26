package com.upuphone.cloudplatform.fota.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname OperationPO
 * @Description
 * @Date 2022/3/2 10:30 下午
 * @Created by gz-d
 */
@Data
public class OperationPO {
    private String relationId;

    private String versionId;

    private String operator;

    private LocalDateTime operationTime;
}
