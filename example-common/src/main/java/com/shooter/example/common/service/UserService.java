package com.shooter.example.common.service;

import com.shooter.example.common.model.User;

/**
 * ClassName: UserService
 * Package: com.shooter.example.common.service
 * Description:
 * @Author:Shooter
 * @Create 2024/4/7 21:59
 * @Version 1.0
 */
public interface UserService {

    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 测试mock
     * @return
     */
    default short getNumber(){
        return 1;
    }

}
