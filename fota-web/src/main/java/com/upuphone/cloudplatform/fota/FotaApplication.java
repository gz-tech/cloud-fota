package com.upuphone.cloudplatform.fota;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.upuphone")
@EnableScheduling
@MapperScan({"com.upuphone.cloudplatform.fota.mapper"})
@EnableApolloConfig
public class FotaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FotaApplication.class, args);
    }
}
