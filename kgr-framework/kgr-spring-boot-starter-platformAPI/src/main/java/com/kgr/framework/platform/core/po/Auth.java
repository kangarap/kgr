package com.kgr.framework.platform.core.po;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: kgr
 * @create: 2023/11/07 17:24
 */
@Data
public class Auth {
    @NotNull(message = "grant_type不能为空")
    private String grantType;
    @NotNull(message = "clientId不能为空")
    private String clientId;
    @NotNull(message = "clientSecret不能为空")
    private String clientSecret;
}