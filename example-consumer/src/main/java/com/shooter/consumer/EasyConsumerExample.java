package com.shooter.consumer;

import com.shooter.example.common.model.User;
import com.shooter.example.common.service.UserService;
import com.shooter.proxy.ServiceProxy;
import com.shooter.proxy.ServiceProxyFactory;

/**
 * ClassName: EasyConsumerExample
 * Package: com.shooter.consumer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/7 23:09
 * @Version 1.0
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        // 静态代理
//        UserService userService = new UserServiceProxy();
        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yupi");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
