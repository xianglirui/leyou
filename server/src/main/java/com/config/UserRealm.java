package com.config;

import com.pojo.User;
import com.service.StepService;
import com.service.TokenService;
import com.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private StepService stepService;
    @Autowired
    private TokenService tokenService;
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();//拿到user对象

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthorizationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String userName= token.getPrincipal().toString();
        String password = new String(userToken.getPassword());

        //连接真实数据库
        User user = userService.queryUserByName(userName);

//        if(user==null){
//            return null;//抛出异常， UnknownAccountException
//        }else {
//            String  passwordEncoded = new SimpleHash("md5",password,user.getSalt(),2).toString();
//            if(!passwordEncoded.equals(user.getPassword())){
//                throw new IncorrectCredentialsException();
//            }
////            String tokens = tokenService.getToken(user);
////            Var.token = tokens;
//        }
        if(user==null){
            throw new UnknownAccountException();
        }else {
            SecurityUtils.getSubject().getSession().setAttribute(user.getName(),user);
            ByteSource salt = ByteSource.Util.bytes(user.getSalt().getBytes());
            return new SimpleAuthenticationInfo(user,user.getPassword(),salt,getName());
        }
    }

    public static void main(String[] args) {
        String i = "J3w+nqKrzshEaOw1ICzdkg==";
        String sa = new SecureRandomNumberGenerator().nextBytes().toString();
        ByteSource salt = ByteSource.Util.bytes("456");
        ByteSource salt1 = ByteSource.Util.bytes(salt.toString().getBytes());
        Object pwd = new SimpleHash("MD5","123",salt,2);
        Object pwd1 = new SimpleHash("md5","123",salt1,2);
        System.out.println(salt1);
        System.out.println(salt);
        System.out.println(pwd1);
        System.out.println(pwd);

    }
}
