package com.xuecheng.auth.web.controller;

import com.xuecheng.api.auth.AuthControllerApi;
import com.xuecheng.auth.service.AuthService;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.utils.CookieUtil;
import com.xuecheng.framework.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController extends BaseController implements AuthControllerApi {

    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    @Autowired
    AuthService authService;


    @Override
    public LoginResult login(LoginRequest loginRequest) {

        if(loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())){
            //账号没有输入
        }


        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //申请令牌
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);
        String access_token = authToken.getAccess_token();
        //将令牌存储到cookie
        saveTokenToCookie(access_token);
        return new LoginResult(CommonCode.SUCCESS,access_token);
    }

    //存储令牌到cookie
    private void saveTokenToCookie(String token){
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,true);

    }
}
