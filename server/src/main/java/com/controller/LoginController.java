package com.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.config.MyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;
import com.service.TokenService;
import com.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@ApiIgnore
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Resource
    private MyUtils myUtils;


    @PostMapping("/toLogin")
    @ResponseBody
    public String login(@RequestBody User user) throws JsonProcessingException {
        System.out.println("收到登录请求"+user);

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(),user.getPassword());

        try {
            subject.login(token);//执行登录操作

            HashMap<String,Object> hs=new HashMap<>();
            User u = (User) subject.getSession().getAttribute(user.getName());
            int role = u.getRole();
//            hs.put("token","token"+user.getName()+" "+user.getPassword()+role);
            hs.put("Authorization",tokenService.getToken(u));

            hs.put("role",role);

            hs.put("msg","登录成功！");
            ObjectMapper objectMapper=new ObjectMapper();
            System.out.println("已发送");
            return objectMapper.writeValueAsString(hs);

        }catch (UnknownAccountException e){//用户名不存在
            return "登录失败，该用户名不存在";
        }catch (IncorrectCredentialsException e){//密码错误
            return "登录失败，密码错误";
        }
    }

    @PostMapping("/toRegister")
    @ResponseBody
    public String register(@RequestBody User user){
        System.out.println("收到注册请求"+user);
        User user1 = userService.queryUserByName(user.getName());
        if(user1==null){
            String[] en = myUtils.encryption(user.getPassword());
            User u = new User();
            u.setName(user.getName());
            u.setSalt(en[0]);
            u.setEmail(user.getEmail());
            u.setPassword(en[1]);
            u.setRole(user.getRole());
            userService.add(u);
            return "注册成功";
        }else {
            return "该用户名已被注册";
        }
    }
    @GetMapping("/getRole")
    @ResponseBody
    public String getRole(HttpServletRequest request) throws JsonProcessingException {
        String token = request.getHeader("token");
        System.out.println("token "+token);
        // 获取 token 中的 user id
        String userId;
        String code="200";
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();

        if(token==null){
            code = "401";
            hs.put("code",code);
            return objectMapper.writeValueAsString(hs);
        }
        try {
            userId = JWT.decode(token).getAudience().get(0);
            System.out.println(userId);
        } catch (JWTDecodeException j) {
            code = "401";
            hs.put("code",code);
            return objectMapper.writeValueAsString(hs);
        }
        User user = userService.findById(Integer.parseInt(userId));
        System.out.println(user);
        if (user == null) {
            code = "401";
            hs.put("code",code);
            return objectMapper.writeValueAsString(hs);
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            code = "401";
            hs.put("code",code);
            return objectMapper.writeValueAsString(hs);
        }

        hs.put("code",code);
        hs.put("userInfo",user);

        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/update_name")
    public String update_name(@RequestParam String name,@RequestParam int id){
        User user = new User();
        user.setName(name);
        user.setId(id);
        userService.update_name(user);
        return name;
    }

    @GetMapping("/update_pwd")
    public String update_pwd(@RequestParam String password,@RequestParam int id){
        String[] en = myUtils.encryption(password);
        User user = new User();
        user.setPassword(en[1]);
        user.setId(id);
        user.setSalt(en[0]);
        userService.update_pwd(user);
        return "update_pwd_ok";
    }

    @GetMapping("/update_email")
    public String update_email(@RequestParam String email,@RequestParam int id){
        User user = new User();
        user.setEmail(email);
        user.setId(id);
        userService.update_name(user);
        return "update_email_ok";
    }

    @GetMapping("/getUserList")
    public String getUserList() throws JsonProcessingException {
        List<User> userList = userService.USER_LISTByRole();
        return new ObjectMapper().writeValueAsString(userList);
    }

    @GetMapping("/deleteAdmin")
    public String deleteAdmin(@RequestParam int id){
        userService.deleteAdmin(id);
        return "ok";
    }

    @PostMapping("/addAdmin")
    public String addAdmin(@RequestBody User form){
        String[] en = myUtils.encryption(form.getPassword());
        form.setPassword(en[1]);
        form.setSalt(en[0]);
        userService.add(form);
        return "ok";
    }

    @PostMapping("/editAdmin")
    public String editAdmin(@RequestBody User form){
        userService.updateEdit(form);
        return "ok";
    }
}
