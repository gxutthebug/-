package com.itheima.travel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UCode  implements Serializable {
    private String smsCode;
    private User user;
}
