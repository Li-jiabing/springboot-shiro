package com.itheima.shiro;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 * @author LiJiaBing
 * @date 3/12/2020 上午9:28
 */
public class UserRealm extends AuthorizingRealm {
    //执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加资源得授权字符串 ,直接写页面显得有点死，用数据库查询配置得方法使其变活
        //info.addStringPermission("user:add");

        //到数据库查询当前登录用户的授权字符串
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User user1 = userService.findById(user.getId());
        info.addStringPermission(user1.getPerms());

        return info;
    }

    //注入业务
    @Autowired
    private UserService userService;


    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        //1.编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByName(token.getUsername());

        if(user == null){
            //用户名不存在
            return null;//Shiro底层会抛出UnKnowAccountException
        }

        //2.判断密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
