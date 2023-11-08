package com.kgr.framework.platform.core.dto;

import lombok.Data;

/**
 * @author: kgr
 * @create: 2023/11/07 10:45
 */
@Data
public class TokenInfo {
    // 令牌
    private String accessToken;
    // 刷新令牌
    private String refreshToken;
    // 令牌类型
    private String tokenType;
    // 过期时间 毫秒
    private String expiresIn;
    // 刷新令牌过期时间
    private String refreshExpiresIn;
}
