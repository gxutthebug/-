package com.itheima.travel.service;

import com.itheima.travel.pojo.Category;

import java.util.List;

public interface CategoryService {
    /**
     查询所有的线路分类
     第1次从mysql中查询，以后从redis中查询
     */
    List<Category> findAll();
}
