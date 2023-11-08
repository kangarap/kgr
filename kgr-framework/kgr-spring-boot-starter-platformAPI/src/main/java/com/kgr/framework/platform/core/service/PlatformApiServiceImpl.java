package com.kgr.framework.platform.core.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgr.framework.platform.config.PlatformApiProperties;
import com.kgr.framework.platform.core.dto.PlatformRequest;
import com.kgr.framework.platform.core.dto.PlatformResponse;
import com.kgr.framework.platform.core.dto.TokenInfo;
import com.kgr.framework.platform.core.po.Auth;
import com.kgr.framework.platform.core.po.Target;
import com.kgr.framework.platform.core.po.TargetUrl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * platformAPI service接口，实现类
 * @author: kgr
 * @create: 2023/11/07 09:53
 */
@RequiredArgsConstructor
public class PlatformApiServiceImpl implements PlatformApiService{
    private final PlatformApiProperties platformApiProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;


    /**
     * 赋能平台 认证授权
     */
    private String authorize(){
        String redisKey = platformApiProperties.getRedisKey() + "token";

        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForValue().get(redisKey));

        if(optional.isPresent()) {
            return optional.get().toString();
        }

        String tokenUrl = platformApiProperties.getApi().getGetToken();
        Auth auth =  platformApiProperties.getAuth();


        // 设置请求的 Content-Type
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE, (MediaType.APPLICATION_JSON_VALUE));

        // 请求body
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        map.add("grant_type", (auth.getGrantType()));
        map.add("client_id", (auth.getClientId()));
        map.add("client_secret", (auth.getClientSecret()));
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, header);
        ResponseEntity<TokenInfo> exchangeResult = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, TokenInfo.class);

        TokenInfo tokenInfo = exchangeResult.getBody();

        if(!exchangeResult.getStatusCode().equals(HttpStatus.OK) ||
                Objects.isNull(tokenInfo) ||
                Objects.isNull(tokenInfo.getAccessToken())
        ) {
            throw new RuntimeException("========= 赋能平台token获取失败 ========");
        }

        String accessToken = tokenInfo.getAccessToken();
        // 过期秒数
        long expireIn = ( new Long (tokenInfo.getExpiresIn()) - System.currentTimeMillis() ) / 1000;

        redisTemplate.opsForValue().set(redisKey, accessToken, expireIn, TimeUnit.SECONDS);

        return accessToken;
    }


    @Override
    @SneakyThrows(Exception.class)
    public String doRequest(PlatformRequest platformRequest) {

        String url = platformApiProperties.getApi().getExecute();
        return commRequest(url, platformRequest);

    }


    @Override
    public String doRequest(Integer apiId, String tag) {

        return doRequest(apiId, tag, null);
    }

    @Override
    public String doRequest(Integer apiId, String tag, JSONObject urlParam) {
        return doRequest(apiId, tag, urlParam, null);
    }


    @Override
    public String doRequest(Integer apiId, String tag, JSONObject urlParam, JSONObject bodyParam) {
        Target targetInfo = platformApiProperties.getTargets()
                .stream()
                .filter(target -> (apiId == null || target.getApiId().compareTo(apiId) != 0 ))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[kgr-spring-boot-starter-platformAPI][doRequest]接口未配置"));


        TargetUrl targetUrlInfo = targetInfo.getUrls()
                .stream()
                .filter(targetUrl -> tag.equals(targetUrl.getTag()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[kgr-spring-boot-starter-platformAPI][doRequest]接口未配置"));

        PlatformRequest request = new PlatformRequest();
        request.setAppId(targetInfo.getApiId());
        request.setApiUuid(targetInfo.getApiUuid());
        request.setApiPath(targetUrlInfo.getUrl());
        request.setMethod(targetUrlInfo.getMethod());
        request.setUrlParam(urlParam);
        request.setBodyParam(bodyParam);

        return doRequest(request);
    }

    @Override
    public String doUpload(PlatformRequest platformRequest) {

        String url = platformApiProperties.getApi().getExecuteFile();
        return commRequest(url, platformRequest);
    }


    @SneakyThrows(Exception.class)
    public String commRequest(String url, PlatformRequest platformRequest) {

        // 设置请求的 Content-Type
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE, (MediaType.APPLICATION_JSON_VALUE));
        header.add(HttpHeaders.AUTHORIZATION, "Bearer " + authorize());

        // 请求body
        MultiValueMap<String, Object> map = JSONUtil.toBean(JSONUtil.toJsonStr(platformRequest), LinkedMultiValueMap.class);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, header);
        ResponseEntity<PlatformResponse> exchangeResult = restTemplate.exchange(url, HttpMethod.POST, request, PlatformResponse.class);

        PlatformResponse response = exchangeResult.getBody();
        if(!exchangeResult.getStatusCode().equals(HttpStatus.OK) ||
                Objects.isNull(response)
        ) {
            throw new RuntimeException("========= 赋能平台接口请求失败 ========");
        }

        return response.getData();
    }


}
