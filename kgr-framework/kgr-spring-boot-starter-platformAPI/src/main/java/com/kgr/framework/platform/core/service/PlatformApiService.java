package com.kgr.framework.platform.core.service;

import cn.hutool.json.JSONObject;
import com.kgr.framework.platform.core.dto.PlatformRequest;


/**
 * platformAPI service接口，定义接口
 * @author: kgr
 * @create: 2023/11/07 09:53
 */
public interface PlatformApiService {

    /**
     * 通用接口请求
     * @param request 通用请求参数
     * @return 接口返回
     */
    String doRequest(PlatformRequest request);



    /**
     * 通过apiId和tag标签请求接口
     * @param apiId
     * @param tag
     * @return
     */
    String doRequest(Integer apiId, String tag);
    String doRequest(Integer apiId, String tag, JSONObject urlParam);
    String doRequest(Integer apiId, String tag, JSONObject urlParam, JSONObject bodyParam);

    /**
     * 上传文件
     * @param request 请求参数
     * @return 接口返回
     */
    String doUpload(PlatformRequest request);

}
