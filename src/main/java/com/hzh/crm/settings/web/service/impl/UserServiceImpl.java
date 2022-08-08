package com.hzh.crm.settings.web.service.impl;

import com.hzh.crm.settings.domain.User;
import com.hzh.crm.settings.mapper.UserMapper;
import com.hzh.crm.settings.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author DAHUANG
 * @date 2022/3/10
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        User user =userMapper.selectUserByLoginActAndPwd(map);
        return user;
    }

    @Override
    public List<User> queryAllUsers() {
       return userMapper.selectAllUsers();
    }
}
