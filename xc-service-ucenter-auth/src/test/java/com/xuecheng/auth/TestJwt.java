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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHQiOiIxIiwicm9sZXMiOiJyMDEscjAyIiwibmFtZSI6Iml0Y2FzdCIsImlkIjoiMTIzIn0.btekjvgc4U37IWNDICHGlzbWdcC5Bws0z7QVIFv4275MdIbAcu4TjwXOfaI80z-TVg5OR3lUQWsi4UMzvN79xHuil509BrKfthch8CnJjCNdaMIv66lUvf4c7KP2EPXTltI5Yg-PDaAvYBBUPQZi5RMQ8xJOFdlv-7k_R1D-U4XzjwJpG8Ykif-plFgGE45IGrvq_Z22zThVDobtQe_LXRmEjjklwEqo8-9Vd5G_S3xmAqRJoaw0hAVJk_bwUFNjPijWBglNybisyTov4gAm_Qmnk1zDTsWcRhYxcPS6DWVzezaKVx80syFvgJNO5HxzlHU2edKxTW02rGyzWu1kkQ";

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
