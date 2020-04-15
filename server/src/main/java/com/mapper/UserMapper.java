package com.mapper;

import com.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 两种方法，一种是在mapper接口上加@Mapper注解，另一个是在启动类上加@MapperScan扫描mapper下面的
 */
@Mapper//这个注解表示了这是一个mybatis的mapper类
@Repository//将这个类加入到spring容器中
public interface UserMapper {
    User queryUserByName(String name);
    User findById(int id);
    List<User> USER_LIST();
    List<User> USER_LISTByRole();
    void deleteAdmin(int id);
    void updateEdit(User user);
    int add(User user);
    int delete(int id);
    int update(User user);
    User queryUser(String name,String password);
    User queryMail(String email);
    void update_pwd(User user);
    void update_email(User user);
    void update_name(User user);
}
