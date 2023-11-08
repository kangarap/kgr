package com.kgr.framework.platform.core.po;

import lombok.Data;

import java.util.List;

/**
 * @author: kgr
 * @create: 2023/11/07 17:26
 */
@Data
public class Target {
    private String name;
    private Integer apiId;
    private String apiUuid;
    private List<TargetUrl> urls;
}
