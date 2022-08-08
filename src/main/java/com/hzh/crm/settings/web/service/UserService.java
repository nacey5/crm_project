package com.hzh.crm.settings.web.service;

import com.hzh.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author DAHUANG
 * @date 2022/3/10
 */
public interface UserService {

    /**
     * 根据用户的id和密码查找用户
     * @param map
     * @return
     */
    public User queryUserByLoginActAndPwd(Map<String,Object> map);

    /**
     *
     * @return
     */
    List<User> queryAllUsers();
}
