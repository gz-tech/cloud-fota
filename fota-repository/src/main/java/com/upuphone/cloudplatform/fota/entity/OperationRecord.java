package com.upuphone.cloudplatform.fota.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 14:33
 */
@Getter
@Setter
@TableName("fota_operation_record")
public class OperationRecord extends Model<OperationRecord> {
    @TableId(value = "operation_id")
    private String operationId;

    private LocalDateTime time;

    private String userId;

    private String userName;

    private String relationId;

    private String type;

    private String typeName;

    private String module;

    private String description;

    private String requestParam;

    private String content;
}
