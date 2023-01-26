package com.upuphone.cloudplatform.fota.client;

import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.vo.request.UpgradeReqVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-fota-app")
public interface FotaAppClient {

    @GetMapping("/v1/fota-app/getVersionStatus")
    CommonResponse getVersionStatus(@RequestParam("deviceInfo") String deviceInfo) throws Exception;

    @PostMapping("/v1/upgrade")
    CommonResponse upgrade(UpgradeReqVO upgradeReqVO);

}
