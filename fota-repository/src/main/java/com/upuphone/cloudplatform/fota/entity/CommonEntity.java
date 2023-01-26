package com.upuphone.cloudplatform.fota.entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * @author guangzheng.ding
 * @date 2021/11/28 11:34
 */
@Getter
@Setter
public class CommonEntity {

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy = "admin";
//    private String createUser = "admin";

    private String updateBy = "admin";
//    private String updateUser = "admin";

//    private String createUserId;

//    private String updateUserId;

    private char deleted;

}
