package com.upuphone.cloudplatform.fota.service;

import com.upuphone.cloudplatform.fota.vo.response.DictRespVO;

import java.util.List;

public interface FotaManagementService {


    List<DictRespVO> selectDictByFlnm(String flnm);




}
