package com.itheima.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * @author LiJiaBing
 * @date 3/12/2020 上午9:26
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactorBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactorBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加shiro内置过滤器,可以实现权限相关得拦截器
        //常用得过滤器：
        //      anon：无需认证（登录）就可以访问
        //      authc：必须认证才可以访问
        //      user:如果使用了remenberMe得功能可以直接访问
        //      role：该资源必须得到角色权限才可以访问
        /**
         * 需求：在进入add和update页面的时候拦截下来，让用户进行登录
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
//        filterMap.put("/add","authc");
//        filterMap.put("/update","authc");

        filterMap.put("/testThymeleaf","anon");

        //放行login.html页面
        filterMap.put("/login","anon");

        //授权过滤器
        filterMap.put("/add","perms[user:add]");

        //拦截所有页面
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");
        filterMap.put("/*","authc");

        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        //修改调整得登录页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }


    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }


    /**
     * 创建Realm对象
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
