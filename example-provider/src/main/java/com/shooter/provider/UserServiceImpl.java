package com.shooter.provider;

import com.shooter.example.common.model.User;
import com.shooter.example.common.service.UserService;

/**
 * ClassName: UserServiceImpl
 * Package: com.shooter.provider
 * Description:
 * @Author:Shooter
 * @Create 2024/4/7 22:59
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名为：" + user.getName());
        return user;
    }
}
