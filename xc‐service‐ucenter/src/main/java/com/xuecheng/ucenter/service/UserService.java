package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther Jack
 * @create 2019-07-27 12:07
 */
@Service
public class UserService {

    @Autowired
    private XcUserRepository xcUserRepository;
    @Autowired
    XcMenuMapper xcMenuMapper;
    @Autowired
    XcCompanyUserRepository xcCompanyUserRepository;
    //根据账号查询用户信息(包括用户基本信息用户所属企业,用户的权限)
    public XcUserExt getUserext(String username) {
        //返回xcUserExt对象
        XcUserExt xcUserExt = new XcUserExt();
        //根据账号查询
        XcUser xcUser = xcUserRepository.findByUsername(username);
        if (xcUser == null){
            return null;
        }
        //将xcUser属性拷贝到xcUserExt
        BeanUtils.copyProperties(xcUser,xcUserExt);
        //用户id
        String userId = xcUser.getId();
        //查询用户所属企业
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(userId);
        if (xcCompanyUser != null){
            xcUserExt.setCompanyId(xcCompanyUser.getCompanyId());
        }
        //查询用户的权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userId);
        //将权限设置到XcUserExt对象中
        xcUserExt.setPermissions(xcMenus);
        return xcUserExt;
    }
}
