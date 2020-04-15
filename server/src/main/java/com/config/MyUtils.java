package com.config;

import com.pojo.Area;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.utils.RedisUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyUtils {

    /**
     * md5盐值加密
     * @param password
     * @return
     */
    public String[] encryption(String password){
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = "md5";

        String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();
        return new String[]{salt,encodedPassword};
    }


    @Autowired
    JavaMailSenderImpl mailSender;
    public void sendMail(String subject,String text,String setTo) throws MessagingException {
        //一个复杂邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        //正文
        helper.setSubject(subject);
        helper.setText(text,true);

        helper.setTo(setTo);
        helper.setFrom("1341621465@qq.com");

        mailSender.send(mimeMessage);
    }

    public HashMap<String,Object> getFileListByAName(String pre,String name) throws UnsupportedEncodingException {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String accessKey = "DdOsfnc82xUFTBBZ2VriRHVn4_zPk9qXGXCh5Hvn";
        String secretKey = "kWooGUSKxHYgaYssq8o2XtpQYIzi6mtLh5ABOKbK";
        String bucket = "xlr";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "/"+pre+"/"+name+"/";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        HashMap<String,Object> fileList = new HashMap<>();
//        long expireInSeconds = 7200;//1小时，可以自定义链接过期时间
        String domainOfBucket = "http://q8nrnc5g8.bkt.clouddn.com";
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                String encodedFileName = URLEncoder.encode(item.key, StandardCharsets.UTF_8).replace("+", "%20");
                String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
//                String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
                String finalUrl = auth.privateDownloadUrl(publicUrl);
                fileList.put(item.key.substring(item.key.lastIndexOf("/")+1),finalUrl);
            }
        }
        return fileList;
    }

    public void deleteDocument(String filename){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String accessKey = "DdOsfnc82xUFTBBZ2VriRHVn4_zPk9qXGXCh5Hvn";
        String secretKey = "kWooGUSKxHYgaYssq8o2XtpQYIzi6mtLh5ABOKbK";
        String bucket = "xlr";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, filename);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
