package com.upuphone.cloudplatform.fota.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("fota_file")
public class FilePO extends Model<FilePO> {

    @TableId(value = "file_id")
    private String fileId;

    private String name;

    @TableField(value = "endpoint")
    private String endPoint;

    private String bucketName;

    private String objectName;

    private String md5;

    private Long size;

    private String storageProvider;

    private String ak;

    private String sk;

    private String type;

    private String versionId;

    private String fromVersionId;

    private String status;

    @TableField(exist = false)
    private LocalDateTime uploadTime;

    @TableField(exist = false)
    private LocalDateTime startTime;

    @TableField(exist = false)
    private LocalDateTime endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String versionName;

    private String createBy = "admin";

    private String updateBy = "admin";

    @TableLogic
    private String delFlag;

    public FilePO() {
    }
}
