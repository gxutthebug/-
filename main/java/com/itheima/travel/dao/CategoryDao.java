package com.itheima.travel.dao;

import com.itheima.travel.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDao {

    /**
     查询所有的线路分类
     */
    @Select("SELECT * FROM tab_category ORDER BY cid")
    List<Category> findAll();
}
