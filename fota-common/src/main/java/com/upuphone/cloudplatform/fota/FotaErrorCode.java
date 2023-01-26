package com.upuphone.cloudplatform.fota;

import com.upuphone.cloudplatform.common.response.ErrorCode;

/**
 * @Classname FotaErrorCode
 * @Description
 * @Date 2022/2/16 1:57 下午
 * @Created by gz-d
 */
public enum FotaErrorCode implements ErrorCode {
    NOT_FOUND_ERROR(200001, "not found error"),
    REPEATED_FILE_ERROR(200002, "文件重复上传"),
    VERSION_ALREADY_TEST_ERROR(200003, "版本已测试通过或失败"),
    OPERATION_DENIED(200004, "不允许操作"),
    TEST_RELEASE_EXIST(200005, "已建立该测试版本"),
    COPY_OBJECT_ERROR(200006, "复制对象错误");

    private int errorCode;
    private String errorMessage;

    FotaErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
