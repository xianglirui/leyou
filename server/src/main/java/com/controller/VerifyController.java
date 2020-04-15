package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;
import com.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Random;

@RestController
@ApiIgnore
public class VerifyController {

    @Autowired
    JavaMailSenderImpl mailSender;

    MimeMessage mimeMessage = null;
    User u = null;//保存邮箱验证时查询出来的信息，修改密码时无需查询数据库，直接用它

    @Autowired
    private UserService userService;

    @GetMapping("/sendMail")
    public String SignalSend(@RequestParam String param) throws MessagingException, JsonProcessingException {
        String hash = new SecureRandomNumberGenerator().nextBytes().toString();
        System.out.println("收到邮箱请求"+param);
        String subject = "乐游注册验证";
        String text = "验证成功";

        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();

        User user = userService.queryMail(param);
        if(user!=null){
            hs.put("err","该邮箱已被注册");
        }else {
            try {
                //一个复杂邮件
                mimeMessage = mailSender.createMimeMessage();
                //组装
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
                //正文
                helper.setSubject(subject);
                helper.setText(text,false);

                helper.setTo(param);
                helper.setFrom("1341621465@qq.com");

                mailSender.send(mimeMessage);

            }catch (MailSendException e){
                hs.put("err","邮箱错误");
                return objectMapper.writeValueAsString(hs);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }


    @GetMapping("/send_code")
    public String send_code(@RequestParam String param) throws JsonProcessingException, MessagingException {
        int random = new Random().nextInt(9999);
        System.out.println("收到邮箱请求"+param);
        String subject = "乐游验证码";
        String text = random+"";

        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();

        User user = userService.queryMail(param);
        u = user;
        if(user==null) {
            hs.put("err", "邮箱错误");
        }else {
            //一个复杂邮件
            mimeMessage = mailSender.createMimeMessage();
            //组装
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            //正文
            helper.setSubject(subject);
            helper.setText(text,false);

            helper.setTo(param);
            helper.setFrom("1341621465@qq.com");

            mailSender.send(mimeMessage);

            hs.put("code",random);
        }
        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/amend_pwd")
    public String amend_pwd(@RequestParam String pass,@RequestParam String checkPass) throws JsonProcessingException {
        System.out.println("收到修改密码请求"+pass+" "+checkPass);
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();

        if(!pass.equals(u.getPassword())){
            hs.put("err","密码输入错误");
        }else {
            u.setPassword(checkPass);
            userService.update(u);
            hs.put("ok","修改完成，请登录");
        }
        return objectMapper.writeValueAsString(hs);
    }

}
