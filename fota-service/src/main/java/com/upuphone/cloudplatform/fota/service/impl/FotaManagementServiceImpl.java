package com.upuphone.cloudplatform.fota.service.impl;

import com.upuphone.cloudplatform.fota.config.FotaSetting;
import com.upuphone.cloudplatform.fota.converter.DictConverter;
import com.upuphone.cloudplatform.fota.entity.DictEntity;
import com.upuphone.cloudplatform.fota.service.FotaManagementService;
import com.upuphone.cloudplatform.fota.vo.response.DictRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FotaManagementServiceImpl implements FotaManagementService {

    @Autowired
    private FotaSetting fotaSetting;
    @Override
    public List<DictRespVO> selectDictByFlnm(String flnm) {
        /*fotaSetting.getDictionary();
        List<DictEntity> dictEntityList = dictMapper.selectDictByFlnm(flnm);*/
        List<DictEntity> dictEntityList = new ArrayList<>();
        for (String key:
             fotaSetting.getDictionary().get(flnm).keySet()) {
            dictEntityList.add(new DictEntity(key, fotaSetting.getDictionary().get(flnm).get(key)));
        }
        return DictConverter.INSTANCE.do2Volist(dictEntityList);
    }
}