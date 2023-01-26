package com.upuphone.cloudplatform.fota.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 9:55
 */
@Getter
@Setter
@ToString
public class VersionListReqVO {

    private String versionName;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @NotNull
    private Integer pageNum;

    @NotNull
    private Integer pageSize;


}
