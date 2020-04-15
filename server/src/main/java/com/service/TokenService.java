package com.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/***
 * token 下发
 */
@Service
public class TokenService {

    public String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(String.valueOf(user.getId())).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

//    public String getToken(User user){
//        UUID token = UUID.fromString(user.getName());
//        return token.toString();
//    }
}