package com.xuecheng.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-15 11:15
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        String key  ="token:c7d26ea7-d2ba-486c-8425-57be797ba016";
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHQiOiIxIiwicm9sZXMiOiJyMDEscjAyIiwibmFtZSI6Iml0Y2FzdCIsImlkIjoiMTIzIn0.btekjvgc4U37IWNDICHGlzbWdcC5Bws0z7QVIFv4275MdIbAcu4TjwXOfaI80z-TVg5OR3lUQWsi4UMzvN79xHuil509BrKfthch8CnJjCNdaMIv66lUvf4c7KP2EPXTltI5Yg-PDaAvYBBUPQZi5RMQ8xJOFdlv-7k_R1D-U4XzjwJpG8Ykif-plFgGE45IGrvq_Z22zThVDobtQe_LXRmEjjklwEqo8-9Vd5G_S3xmAqRJoaw0hAVJk_bwUFNjPijWBglNybisyTov4gAm_Qmnk1zDTsWcRhYxcPS6DWVzezaKVx80syFvgJNO5HxzlHU2edKxTW02rGyzWu1kkQ";
        //存储数据
        stringRedisTemplate.boundValueOps(key).set(jwt,60, TimeUnit.SECONDS);

        //校验
        Long expire = stringRedisTemplate.getExpire(key);

        //根据key取数据
        String s = stringRedisTemplate.opsForValue().get(key);
        System.out.println(s);


    }
}
