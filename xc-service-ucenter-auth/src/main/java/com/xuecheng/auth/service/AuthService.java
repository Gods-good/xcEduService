package com.xuecheng.auth.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class AuthService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    /**
     * 用户认证方法
     * 1、请求spring security的/oauth/token 申请令牌
     * 2、存储到redis
     * 3、返回令牌给controller
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    public AuthToken login(String username,String password,String clientId,String clientSecret){

        //申请令牌，使用restTemplatey请求spring security
        AuthToken authToken = applyToken(username, password, clientId, clientSecret);
        if(authToken == null){
            ExceptionCast.cast(AuthCode.AUTH_APPLYTOKEN_FAIL);
        }

        //存储令牌到redis
        boolean result = saveTokenToRedis(authToken);
        if(!result){
            ExceptionCast.cast(AuthCode.AUTH_SAVETOKEN_FAIL);
        }
        return authToken;
    }

    //存储token到redis
    private boolean saveTokenToRedis(AuthToken authToken){
        String access_token = authToken.getAccess_token();
        String key = "token:"+access_token;

        String authTokenString = JSON.toJSONString(authToken);

        //存储令牌到 redis
        stringRedisTemplate.boundValueOps(key).set(authTokenString,tokenValiditySeconds, TimeUnit.SECONDS);

        //是否存储成功, 查询token的过期时间，如果查询不到返回-1
        Long expire = stringRedisTemplate.getExpire(key);
        if(expire>0){
            return true;//存储成功
        }
        return false;
    }
    //申请令牌
    private AuthToken applyToken(String username,String password,String clientId,String clientSecret){

        //采用客户端负载均衡，从eureka获取认证服务的ip 和端口和/auth根路径
        ServiceInstance serviceInstance = loadBalancerClient.choose(XcServiceList.XC_SERVICE_UCENTER_AUTH);
        URI uri = serviceInstance.getUri();
        String authUrl = uri+"/auth/oauth/token";
        //URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType
        // url就是 申请令牌的url /oauth/token
        //method http的方法类型
        //requestEntity请求内容
        //responseType，将响应的结果生成的类型

        //请求的内容分两部分
        //1、header信息，包括了http basic认证信息
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        String httpbasic = httpbasic(clientId, clientSecret);
        //"Basic WGNXZWJBcHA6WGNXZWJBcHA="
        headers.add("Authorization", httpbasic);
        //2、包括：grant_type、username、passowrd
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);

        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        //远程调用申请令牌
        ResponseEntity<Map> exchange = null;
        try {
            exchange = restTemplate.exchange(authUrl, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }

        // 返回的令牌信息
        Map body1 = exchange.getBody();
        String access_token = (String) body1.get("jti");
        String jwt_token = (String) body1.get("access_token");
        String refresh_token = (String) body1.get("refresh_token");
        if(StringUtils.isEmpty(access_token) ||
                StringUtils.isEmpty(jwt_token) ||
                StringUtils.isEmpty(refresh_token) ){
            return null;
        }
        //将map中数据封装成AuthToken对象
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token(access_token);
        authToken.setJwt_token(jwt_token);
        authToken.setRefresh_token(refresh_token);


        return authToken;
    }

    //拼接http basic认证串
    private String httpbasic(String clientId,String clientSecret){

        //将客户端id和客户端密码拼接，按“客户端id:客户端密码”
        String string = clientId+":"+clientSecret;
        //进行base64编码
        byte[] encode = Base64.encode(string.getBytes());
        return "Basic "+new String(encode);
    }

}
