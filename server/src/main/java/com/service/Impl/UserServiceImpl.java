package com.service.Impl;

import com.mapper.UserMapper;
import com.pojo.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> USER_LIST() {
        return userMapper.USER_LIST();
    }

    @Override
    public List<User> USER_LISTByRole() {
        return userMapper.USER_LISTByRole();
    }

    @Override
    public void deleteAdmin(int id) {
        userMapper.deleteAdmin(id);
    }

    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public void updateEdit(User user) {
        userMapper.updateEdit(user);
    }
    @Override
    public int delete(int id) {
        return userMapper.delete(id);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User queryUser(String name, char[] password) {
        return null;
    }

    @Override
    public User queryMail(String email) {
        return userMapper.queryMail(email);
    }

    @Override
    public void update_pwd(User user) {
        userMapper.update_pwd(user);
    }

    @Override
    public void update_email(User user) {
        userMapper.update_email(user);
    }

    @Override
    public void update_name(User user) {
        userMapper.update_name(user);
    }
}
