package com.upuphone.cloudplatform.fota.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/25 18:08
 */
@Getter
@Setter
public class FormalReleaseListPO implements Serializable, Comparable<FormalReleaseListPO>{

    private String versionId;

    private List<ReleasePO> releases;

    private String versionStatus;

    private LocalDateTime uploadTime;

    private String description;

    private String operator;

    private int major;

    private int minor;

    private int build;

    private int patch;




    @Override
    public int compareTo(FormalReleaseListPO o) {
        int a = o.major - this.major;
        int b = o.minor - this.minor;
        int c = o.build - this.build;
        int d = o.patch - this.patch;
        return a == 0?(b == 0 ? (c==0 ? d:c):b):a;
    }
}
