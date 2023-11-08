package com.kgr.framework.platform.core.dto;

import cn.hutool.json.JSONObject;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author: kgr
 * @create: 2023/11/07 11:15
 *
 */

/**
 * 作者: 你的名字
 * 创建日期: 2023-05-26
 * 文件描述: 这里是文件的描述
 */
@Data
public class PlatformRequest {
    @NotNull(message = "接口ID不能为空")
    private Integer appId;
    @NotNull(message = "接口UUID不能为空")
    private String apiUuid;
    // 地址参数
    private JSONObject urlParam;
    // 请求题参数
    private JSONObject bodyParam;
    // 目标接口地址
    private String apiPath;
    // 目标接口请求方式
    private String method;
    // 请求来源
    private String source;
    // url参数（sm4加密）
    private String urlParamEncrypt;
    // 请求题参数（sm4加密）
    private String bodyParamEncrypt;
    // formData参数
    private String formData;
    // formData参数（sm4加密）
    private String formDataEncrypt;
    // 目标接口接收文件流的字段名
    private String fileField;
    // 上传的文件（支持多个）
    private MultipartFile [] file;

}
