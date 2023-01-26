package com.upuphone.cloudplatform.fota.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guangzheng.ding
 * @date 2021/11/29 18:22
 */
@Getter
@Setter
public class DictEntity {

    private String code;

    private String text;

    public DictEntity() {
    }

    public DictEntity(String code, String text) {
        this.code = code;
        this.text = text;
    }
}
