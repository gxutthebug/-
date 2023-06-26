package com.itheima.travel.service.impl;

import com.itheima.travel.dao.UserDao;
import com.itheima.travel.entity.Result;
import com.itheima.travel.pojo.TelephoneLogin;
import com.itheima.travel.pojo.User;
import com.itheima.travel.service.UserService;
import com.itheima.travel.utils.SMSUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户注册
     * @param registerUser 用户名,密码,手机号
     * @return 统一结果
     */
    @Override
    public Result register(User registerUser ,String code) {


        // 调用DAO查看用户名是否被注册
        User queryUser = userDao.findByUsername(registerUser.getUsername());
        if (queryUser != null) {
            return new Result(false, "注册失败, 用户名已被注册");
        }

        // 调用DAO查看查看手机号是否被注册
        User telephoneUser = userDao.findByTelephone(registerUser.getTelephone());
        if (telephoneUser != null) {
            return new Result(false, "注册失败, 手机号已被注册!");
        }

        // 验证验证码是否正确
        // 从Redis中获取该手机号对应的验证码
        String storedCode = (String) redisTemplate.opsForValue().get(registerUser.getTelephone());

        if (storedCode != null && storedCode.equals(code)) {
            // 给密码加密
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String bcryptPassword = encoder.encode(registerUser.getPassword());

            // 将加密后的密码替换前端传过来的原始密码(registerUser对象中)
            registerUser.setPassword(bcryptPassword);

            // 调用DAO把用户数据添加到数据库
            userDao.register(registerUser);

            return new Result(true, "注册成功");
        } else {
            System.out.println("验证码错误");
            return new Result(false, "注册失败, 验证码错误!",null);
        }



    }

    /**
     * 用户登录
     * @param loginUser 用户名,密码
     * @return
     */
    @Override
    public Result login(User loginUser) {
        // 调用DAO通过用户名查询用户
        User queryUser = userDao.findByUsername(loginUser.getUsername());
        if (queryUser == null) {
            return new Result(false, "登录失败, 用户名不存在!");
        }

        // 用户名找到用户,对比密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(loginUser.getPassword(), queryUser.getPassword());
        System.out.println("matches = " + matches);
        if (matches) {
            // 记得要传第三个参数(用户信息) , 控制层需要拿到这个用户信息注入一个session对象
            return new Result(true, "登录成功",queryUser);
        } else {
            return new Result(false, "登录失败, 密码错误!",null);
        }
    }

    @Override
    public Result loginByTelephone(TelephoneLogin loginUser) {

        //  取出用户发来的手机号与验证码
        String  telephone = loginUser.getTelephone();
        String   code  = loginUser.getSmsCode();

        // 从Redis中获取该手机号对应的验证码
        String storedCode = (String) redisTemplate.opsForValue().get(telephone);

        // 检查验证码是否存在且与给定的验证码相匹配
        if (storedCode != null && storedCode.equals(code)) {
            System.out.println("验证码正确");
            // 调用DAO通过手机号查询用户
            User user= userDao.findByTelephone(telephone);
            return new Result(true, "登录成功",user);
        } else {
            System.out.println("验证码错误");
            return new Result(false, "登录失败, 密码错误!",null);
        }


    }

    @Override
    public User findByUsername(String telephone) {

        // 调用DAO通过用户名查询用户
        User queryUser = userDao.findByTelephone(telephone);

        return queryUser;
    }

    @Override
    public Result sendSms(String telephone) {

         // 随机生成验证码
        String authCode = RandomStringUtils.randomNumeric(6);
        System.out.println("给手机：" + telephone + " 发送登录验证码：" + authCode);

        SMSUtils.sendTelephoneLoginNotice(telephone,authCode);

        // 将手机号和验证码的对应关系存储到Redis中
        redisTemplate.opsForValue().set(telephone, authCode, 3, TimeUnit.MINUTES);

        return new Result(true, "返回验证码成功!",authCode);
    }
}
