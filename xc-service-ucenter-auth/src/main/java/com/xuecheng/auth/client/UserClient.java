package com.xuecheng.auth.client;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.framework.client.XcServiceList;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by cl on 2019/7/27.
 */
@FeignClient(value = XcServiceList.XC_SERVICE_UCENTER)
public interface UserClient extends UcenterControllerApi {
}
