package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-15 10:50
 **/
public class TestJwt {

    @Test
    public void testCreateJwt(){
        //密钥库地址（基于classpath）
        String keystore_location = "xc.keystore";
        //密钥库密码
        String keystore_password = "xuechengkeystore";

        //生成jwt令牌
        //CharSequence content 令牌内容, Signer signer 签名

        //加载密钥库
        //Resource resource, char[] password
        ClassPathResource classPathResource = new ClassPathResource(keystore_location);
        //密钥库
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_password.toCharArray());

        //取出密钥对
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("xckey", "xuecheng".toCharArray());

        //取出私钥
        PrivateKey aPrivate = keyPair.getPrivate();
        //创建签名对象,采用RSA算法
        RsaSigner rsaSigner = new RsaSigner((RSAPrivateKey) aPrivate);
        //定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "123");
        tokenMap.put("name", "itcast");
        tokenMap.put("roles", "r01,r02");
        tokenMap.put("ext", "1");
        String toJSONString = JSON.toJSONString(tokenMap);

        Jwt encode = JwtHelper.encode(toJSONString, rsaSigner);
        //取出jwt令牌
        String encoded = encode.getEncoded();
        System.out.println(encode);
        //校验jwt令牌合法
//        JwtHelper.decodeAndVerify()

    }
    //校验方法
    @Test
    public void testVerifyJwt(){
        //jwt令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOiIyIiwidXNlcnBpYyI6bnVsbCwidXNlcl9uYW1lIjoidDEiLCJzY29wZSI6WyJhcHAiXSwibmFtZSI6IuiAgeW4iDEiLCJ1dHlwZSI6IjEwMTAwMiIsImlkIjoiNTIiLCJleHAiOjE1NjQ3Nzk2ODcsImF1dGhvcml0aWVzIjpbInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYmFzZSIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfZGVsIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9saXN0IiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9wbGFuIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZSIsImNvdXJzZV9maW5kX2xpc3QiLCJ4Y190ZWFjaG1hbmFnZXIiLCJ4Y190ZWFjaG1hbmFnZXJfY291cnNlX21hcmtldCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfcHVibGlzaCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYWRkIl0sImp0aSI6ImIyNzU4OWMwLTczYzktNGI4Yy1hY2QyLWUyMTc4MjEwMWFiMiIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.Ki8BDE97kgeSkzvxlR_rbC2H9o8NSSPQITcJtI38wlMFHtK9AxrUJnDh04drZCkLFMRmlnQTOAJALMCoXXR0ThrluSM53wB9qR08aXH6EjljlwSmSdF6hgkF_IO0VzlYIMBlBCBxqOTvFZbbJwsUIFq4vzu-3SddJ1pzX4cWHBJUFbRZEabv-bIrQvjgX9GeeowIacJrXaJmja9Z9Wmf1LZhw2ibRJWtYN6_e7S-kNwlPfW3l3DOtXPU87amnSISVKXWXQ5kJY9RtRaw75SPmwrKghxp_0bUlFFWCOOAtfPcmiQgjeDpwtZ9HXPgZ2BuXsZeDipR6O-RKwB88evpaQ";

        //公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtoyc1OdgaQSvizDsvdQMPLvZeb8iUn22xBh9pekAnoXMtRltBT0wcBv6on+SiZ2n05w6bv4iMNZU7bu3/9OvN9qzTjD1nnVSLA5BHHlm0VPPlSVqHTFYGPprz0CpTBfTy9lk5CcOFQmaHrTwhId83Z5kW2Alv6CLRdW9xjlW+n9IBCCVydlJBjBXz79u65ATGT5Oadf1Tw8LV7uXlH73FS9KiClbH6ogKrxOCv7aKUbd/AVmCO5hO/N9wmbHaJckw8EnZny7dyHWFLCgreFyqDsYO+yKqOvxiBq/QirOkOQ4W/uJj3Qd6q1PYAurlYZEoKGQ0XFASBYmrGo4so5g6QIDAQAB-----END PUBLIC KEY-----";

        //创建RSA签名校验对象
        RsaVerifier rsaVerifier = new RsaVerifier(publicKey);

        Jwt jwt = JwtHelper.decodeAndVerify(token, rsaVerifier);
        //校验jwt令牌
        String claims = jwt.getClaims();
        System.out.println(claims);
    }






}
