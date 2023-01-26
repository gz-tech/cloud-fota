package com.upuphone.cloudplatform.fota.config;



import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@Setter
public class FotaSetting {
    @Value("${obs.endPoint}")
    private String endPoint;

    @Value("${obs.ak}")
    private String ak;

    @Value("${obs.sk}")
    private String sk;

    @Value("${obs.bucketName}")
    private String bucketName;

    @Value("${imei}")
    private String imei;


    @Value("${public.obs.bucket}")
    private String publicBucket;

    @Value("#{${dictionary}}")
    private Map<String, Map<String, String>> dictionary;

}
