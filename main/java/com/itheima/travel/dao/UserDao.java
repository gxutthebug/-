package com.itheima.travel.dao;

import com.itheima.travel.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {


    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    @Select("select * from tab_user where username = #{username};")
    User findByUsername(String username);

    @Select("select * from tab_user where telephone = #{telephone};")
    User findByTelephone(String telephone);

    @Insert("insert into tab_user (username, password, telephone) values (#{username}, #{password}, #{telephone});")
    void register(User registerUser);
}
