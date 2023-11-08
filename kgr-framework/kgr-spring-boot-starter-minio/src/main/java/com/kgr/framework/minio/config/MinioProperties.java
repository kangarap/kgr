package com.kgr.framework.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: kgr
 * @create: 2023/11/03 15:33
 */
@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    // 配置的域名访问
    private String domain;
    // ip访问
    private String endpoint;

    private String accessKey;
    private String accessSecret;

}
