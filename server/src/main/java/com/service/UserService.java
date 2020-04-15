package com.service;

import com.pojo.User;

import java.util.List;

public interface UserService {
    User queryUserByName(String name);
    User findById(int id);
    List<User> USER_LIST();
    List<User> USER_LISTByRole();
    void deleteAdmin(int id);
    int add(User user);
    void updateEdit(User user);
    int delete(int id);
    int update(User user);
    User queryUser(String name, char[] password);
    User queryMail(String email);

    void update_pwd(User user);
    void update_email(User user);
    void update_name(User user);
}
