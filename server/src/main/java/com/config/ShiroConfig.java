package com.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //1. 创建realm对象，需要自定义类
    @Bean(name="userRealm")
    public UserRealm userRealm(){
        UserRealm realm = new UserRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    //2.DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //3.ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro内置的过滤器
        /*
            anon:无需认证就可以访问
            authc：必须认证了才能访问
            user：必须拥有 "记住我" 功能才能使用
            perms：拥有对某个资源的权限才能访问
            role：拥有某个角色权限才能访问
         */
        //拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权 没有授权会跳转到未授权页面
//        filterMap.put("/user/add","perms[user:add]");
//        filterMap.put("/user/update","perms[user:update]");


//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
//        filterMap.put("/user/*","authc");

        bean.setFilterChainDefinitionMap(filterMap);

        //设置登录的请求
        bean.setLoginUrl("/toLogin");

        //未授权跳转
        bean.setUnauthorizedUrl("/auth");

        return bean;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5"); //加密方式为MD5
        credentialsMatcher.setHashIterations(2); //加密次数为3次
        credentialsMatcher.setStoredCredentialsHexEncoded(true);//采用hash散列算法加密
        return credentialsMatcher;

    }
}
