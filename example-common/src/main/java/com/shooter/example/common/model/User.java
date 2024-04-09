package com.shooter.example.common.model;

import java.io.Serializable;

/**
 * ClassName: User
 * Package: com.shooter.example.common.model
 * Description:
 * @Author:Shooter
 * @Create 2024/4/7 21:58
 * @Version 1.0
 */
public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
