package com.upuphone.cloudplatform.fota.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname TestReleasePO
 * @Description
 * @Date 2022/2/17 1:47 下午
 * @Created by gz-d
 */
@Getter
@Setter
@TableName("version_release")
public class ReleasePO extends Model<ReleasePO>  implements Serializable, Comparable<ReleasePO> {
    @TableId
    private String releaseId;

    private String versionId;

    @TableField(value = "release_name", condition = SqlCondition.LIKE)
    private String releaseName;

    private LocalDateTime releaseTime;

    @TableField(exist = false)
    private LocalDateTime operationTime;

    @TableField(exist = false)
    private String operator;

    @TableField(exist = false)
    private LocalDateTime startTime;

    @TableField(exist = false)
    private LocalDateTime endTime;

    private String status;

    private String upgradeType;

    private String upgradeRangeType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy = "admin";

    private String updateBy = "admin";

    private String type;

    private String imei;

    @TableField(exist = false)
    private int major;

    @TableField(exist = false)
    private int minor;

    @TableField(exist = false)
    private int build;

    @TableField(exist = false)
    private int patch;

    @TableField(exist = false)
    private String description;

    @TableField(exist = false)
    private String versionName;

    @Override
    public int compareTo(ReleasePO o) {
        int a = o.major - this.major;
        int b = o.minor - this.minor;
        int c = o.build - this.build;
        int d = o.patch - this.patch;
        return a == 0?(b == 0 ? (c==0 ? d:c):b):a;
    }

}
