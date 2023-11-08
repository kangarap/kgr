## kgr-spring-boot-starter-platformAPI

> 一个不成熟的自用starter,仅**自用**,根据业务需求随缘更新

读取配置：
```yaml
platform-config:
  # redis中保存的时候前缀
  redis-key: starter_platformAPI_
  api:
    # 赋能平台的接口根地址
    baseUrl: http://xxx.xxx.xxx/api/kd-api-platform-api
    # 获取token接口
    getToken: ${platform-config.api.baseUrl}/v1/oauth2/api/token
    # 调用接口
    execute: ${platform-config.api.baseUrl}/v1/open/api
    # 上传文件
    executeFile: ${platform-config.api.execute}/file

  # 在赋能平台的认证信息
  auth:
    # 固定值为client_credentials
    grant_type: client_credentials
    # 应用参数clientId
    client_id:
    # 应用参数clientSecret
    client_secret:

  # 赋能平台的能力接口
  core:
      # 自定义
    - name: aaa
      # 赋能平台能力接口apiId
      apiId: 123
      # 赋能平台能力接口apiUuid
      apiUuid: abcdefg
      # 赋能平台能力接口apiId
      # 多接口配置，可以调用该api下的接口列表
      urls:
          # 接口名称，自定义
        - name: 登录接口
          # 标识，自定义，但唯一
          tag: Login
          method: post
          url: /xxx/xxx/xxx/Login

        - name: 获取用户
          tag: GetUserByToken
          method: post
          url: /xxx/xxx/xxx/GetUserByToken

    - name: bbb
      apiId: 456
      apiUuid: abcdefg
```