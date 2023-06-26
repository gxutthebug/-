package com.itheima.travel.pojo;

import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class TelephoneLogin {


    private String telephone;
    private  String  password;
    private  String  smsCode;

}
