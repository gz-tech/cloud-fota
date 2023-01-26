package com.upuphone.cloudplatform.fota.bo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guangzheng.ding
 * @date 2021/11/27 20:42
 */
@ApiModel(value = "编辑版本")
@Getter
@Setter
public class VersionBO {

    private String versionId;

    private String description;

    private String status;

}
