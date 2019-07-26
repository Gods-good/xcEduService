package com.xuecheng.auth.service;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        XcUserExt userext = new XcUserExt();
        if(userext == null){
            return null;
        }
        //取出正确密码，这里暂时使用静态密码
        String password = "123";


        //权限标识串
        String user_permission_string  = "";
        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));

       /* UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
//                AuthorityUtils.createAuthorityList("course_get_baseinfo","course_get_list"));
        return userDetails;
    }
}
