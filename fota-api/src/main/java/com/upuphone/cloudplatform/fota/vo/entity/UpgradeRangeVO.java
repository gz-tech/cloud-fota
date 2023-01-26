package com.upuphone.cloudplatform.fota.vo.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/12/1 20:13
 */
@Getter
@Setter
public class UpgradeRangeVO {

    private String upgradeRangeType;

    private List<RegionVO> area;

    private List<String> deviceType;

    private List<String> imei;

    private Integer percent;
}
