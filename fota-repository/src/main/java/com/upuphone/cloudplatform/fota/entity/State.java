package com.upuphone.cloudplatform.fota.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guangzheng.ding
 * @date 2021/11/29 21:59
 */
@Getter
@Setter
public class State {
    private String name;

    private String cname;

    private String code;

    private List<City> cities;
}
