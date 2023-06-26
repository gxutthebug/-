package com.itheima.travel.service;

import com.itheima.travel.entity.Result;
import com.itheima.travel.pojo.TelephoneLogin;
import com.itheima.travel.pojo.User;

public interface UserService {

    Result register(User registerUser , String code);

    Result login(User loginUser);

    Result loginByTelephone(TelephoneLogin loginUser);


    User findByUsername(String telephone);

    Result sendSms(String telephone);
}
