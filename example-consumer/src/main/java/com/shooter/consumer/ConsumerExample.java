package com.shooter.consumer;

import com.shooter.config.RpcConfig;
import com.shooter.constant.RpcConstant;
import com.shooter.example.common.model.User;
import com.shooter.example.common.service.UserService;
import com.shooter.proxy.ServiceProxyFactory;
import com.shooter.registry.LocalRegistry;
import com.shooter.utils.ConfigUtils;

/**
 * ClassName: ConsumerExample
 * Package: com.shooter.consumer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 17:00
 * @Version 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("shooter");
        // 调用(3次)，测试缓存
        /*for (int i = 0; i < 3; i++) {
            User newUser = userService.getUser(user);
            if (newUser != null){
                System.out.println(newUser.getName());
            }else{
                System.out.println("user == null");
            }
        }*/
        User newUser = userService.getUser(user);
        if (newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }
}
