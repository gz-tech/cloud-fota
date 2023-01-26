package com.upuphone.cloudplatform.fota.config;

import com.upuphone.cloudplatform.fota.service.ReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author guangzheng.ding
 * @date 2021/12/5 13:35
 */
@Component
@Slf4j
public class ScheduleTask {

    @Resource
    private ReleaseService releaseService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void releaseVersion() throws InterruptedException {
        log.info("检查版本发布");
        releaseService.releaseVersion();
    }
}
