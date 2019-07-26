package com.xuecheng.api.auth;

import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;


@Api(value = "用户认证",description = "用户认证接口")
public interface AuthControllerApi {

    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest);
}
