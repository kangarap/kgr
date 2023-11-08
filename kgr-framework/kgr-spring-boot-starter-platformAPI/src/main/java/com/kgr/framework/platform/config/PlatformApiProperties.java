package com.kgr.framework.platform.config;

import com.kgr.framework.platform.core.po.Api;
import com.kgr.framework.platform.core.po.Auth;
import com.kgr.framework.platform.core.po.Target;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: kgr
 * @create: 2023/11/07 09:35
 */

@Data
@Validated
@ConfigurationProperties(prefix = "platform-config")
public class PlatformApiProperties {

    private String redisKey;
    private Api api;
    private Auth auth;
    private List<Target> targets;


}
