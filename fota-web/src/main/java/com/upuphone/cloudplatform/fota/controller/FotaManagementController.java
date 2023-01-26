package com.upuphone.cloudplatform.fota.controller;


import com.upuphone.cloudplatform.common.response.CommonResponse;
import com.upuphone.cloudplatform.fota.client.FotaManagementClient;
import com.upuphone.cloudplatform.fota.service.FotaManagementService;
import com.upuphone.cloudplatform.fota.service.OperationRecordService;
import com.upuphone.cloudplatform.fota.vo.response.DictRespVO;
import com.upuphone.cloudplatform.fota.vo.response.OperationListRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "fota后台管理接口")
@RestController
public class FotaManagementController implements FotaManagementClient {
    @Autowired
    private FotaManagementService fotaManagementService;

    @Autowired
    private OperationRecordService operationRecordService;

    @Override
    public CommonResponse<List<DictRespVO>> getDict(@PathVariable("flnm") String flnm) {
        List<DictRespVO> dictEntityList = fotaManagementService.selectDictByFlnm(flnm);
        return CommonResponse.success(dictEntityList);
    }

    @Override
    public CommonResponse<List<OperationListRespVO>> recordList(@RequestParam("relationId") String relationId, @RequestParam("module") String module) {
        List<OperationListRespVO> operationListRespVOS = operationRecordService.listVersionOperation(relationId, module);
        return CommonResponse.success(operationListRespVOS);
    }
}
