package com.kgr.framework.platform.core.dto;

import lombok.Data;

/**
 * @author: kgr
 * @create: 2023/11/07 11:30
 */
@Data
public class PlatformResponse {
    private String data;
    private String msg;
    private Integer code;
    private Boolean success;
}
