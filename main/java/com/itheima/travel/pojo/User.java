package com.itheima.travel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
public class User implements Serializable {

    private int uid;//用户id
    private String username;//用户名，账号
    private String password;//密码
    private String telephone;//手机号
    private String salt; // 盐

}
