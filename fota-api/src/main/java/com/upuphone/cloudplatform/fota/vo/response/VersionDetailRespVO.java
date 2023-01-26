package com.upuphone.cloudplatform.fota.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upuphone.cloudplatform.fota.vo.entity.UpgradeRangeVO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/12/1 19:11
 */
@Getter
@Setter
public class VersionDetailRespVO {
    private String versionId;

    private String versionName;

    private String upgradeType;

    private List<UpgradeRangeVO> upgradeRangeList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    private String description;

    private String status;

    private String cancelReason;
}
