package com.kgr.framework.platform.config;

import com.kgr.framework.platform.core.service.PlatformApiService;
import com.kgr.framework.platform.core.service.PlatformApiServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;


/**
 * @author: kgr
 * @create: 2023/11/07 09:35
 */
@Configuration
@EnableConfigurationProperties(PlatformApiProperties.class)
@ConditionalOnBean(RedisTemplate.class)
public class PlatformAutoConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PlatformApiService platformApiService(PlatformApiProperties properties, RedisTemplate redisTemplate, RestTemplate restTemplate) {
        return new PlatformApiServiceImpl(properties, redisTemplate, restTemplate);
    }



}
