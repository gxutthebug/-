package com.itheima.travel.controller;


import com.itheima.travel.entity.Result;
import com.itheima.travel.pojo.TelephoneLogin;
import com.itheima.travel.pojo.User;
import com.itheima.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param registerUser 用户名,密码,手机号
     * @return 统一结果
     */
    @RequestMapping("/register")
    public Result register(@RequestBody Map<String ,Object> registerUser) throws InvocationTargetException, IllegalAccessException {



         //  这里不能写 Object userMap =  registerUser.get("user") ; 这样写你会发现userMap.get爆红
        //  上面用的是Object接收 , 但是你下面要用的话 , 要把Object转回它真正对应的类型才能用！
        //  Map 继承于 Object , 所以Object类型是无法使用Map中的.get方法的
        Map<String, String> userMap = (Map<String, String>) registerUser.get("user");
        String code  = (String) registerUser.get("smsCode");


        if (userMap.get("username")==null || userMap.get("password")==null || userMap.get("telephone")==null) {
            return  new Result(false, "注册失败, 注册信息不能为空!");
        }

        //
        User user = new User();
        user.setUsername(userMap.get("username"));
        user.setPassword(userMap.get("password"));
        user.setTelephone(userMap.get("telephone"));

        // 调用业务层注册
        Result result = userService.register(user,code);

        // 响应统一结果
        return result;
    }


    /**
     * 用户登录
     * @param loginUser 用户名,密码
     * @return
     */
    @RequestMapping("/login")
        public Result login(@RequestBody User loginUser, HttpSession session) {
        // 调用业务层登录
        Result result = userService.login(loginUser);


        //2.如果登录成功，将用户信息放在会话域中
        if (result.getSuccess()) {
            System.out.println(result.getData());
            session.setAttribute("user", result.getData());
            //5.返回登录成功，并且封装user对象
        }

        return result;
    }


    @RequestMapping("/loginByTelephone")
    public Result loginByTelephone(@RequestBody TelephoneLogin loginUser, HttpSession session) {

        // 调用业务层验证登录是否成功(验证Redis中是否有这个手机号 与 验证码的映射关系)
        Result result = userService.loginByTelephone(loginUser);


        //2.如果登录成功，将用户信息放在会话域中
        if (result.getSuccess()) {
            session.setAttribute("user", result.getData());
        }

        //5.返回登录成功，并且封装user对象
        return result;
    }


    /**
     * 登录发送手机短信
     * @return
     */
    @RequestMapping("/sendSms")
    public Result loginSendMessage(String telephone) {

        //通过用户名查询用户对象
        User user = userService.findByUsername(telephone);

        //如果用户对象为空，则返回结果提示用户名不存在
        if (user == null) {
            return new Result(false, "用户名不存在");
        }
        //发送短信验证码
        Result resultInfo = userService.sendSms(telephone);

        //返回发送的结果
        return resultInfo;
    }


    /**
     * 注册发送手机短信
     * @return
     */
    @RequestMapping("/sendSms2")
    public Result registerSendMessage(String telephone) {


//        //随机生成验证码
//        String authCode = RandomStringUtils.randomNumeric(6);
//        System.out.println("给手机：" + telephone + " 发送登录验证码：" + authCode);

        //发送短信验证码
        Result resultInfo = userService.sendSms(telephone);

        //返回发送的结果
        return resultInfo;
    }


    /**
     * 判断用户是否登录
     * @param session 会话对象
     * @return 判断结果
     */
    @GetMapping("/isLogged")
    public Result isLogged(HttpSession session) {
        //获取会话域中是否包含用户信息
        User user = (User) session.getAttribute("user");

        System.out.println("判断session对象是否为空" + user);

        //不为空表示已经登录
        if (user != null) {
            //封装true和user对象
            return new Result(true, "登录成功", user.getUsername());
        }
        else {
            //封装false
            return new Result(false);
        }
    }


    /**
     * 退出的方法
     * @param session 会话对象
     */
    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

}
