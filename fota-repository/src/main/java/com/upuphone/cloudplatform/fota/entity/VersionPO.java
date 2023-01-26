package com.upuphone.cloudplatform.fota.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("fota_version")
public class VersionPO extends Model<VersionPO> implements Serializable , Comparable<VersionPO>{

    @TableId(value = "version_id")
    private String versionId;

    private String name;

    private String description;


    @TableField(exist = false)
    private LocalDateTime startTime;

    @TableField(exist = false)
    private LocalDateTime endTime;

    private String status;

    @TableField(exist = false)
    private String statusName;

    private int major;

    private int minor;

    private int build;

    private int patch;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy = "admin";
//    private String createUser = "admin";

    private String updateBy = "admin";

    @TableLogic
    private String delFlag;

    public VersionPO(){}

    @Override
    public int compareTo(VersionPO o) {
        int a = o.major - this.major;
        int b = o.minor - this.minor;
        int c = o.build - this.build;
        int d = o.patch - this.patch;
        return a == 0?(b == 0 ? (c==0 ? d:c):b):a;
    }



}
