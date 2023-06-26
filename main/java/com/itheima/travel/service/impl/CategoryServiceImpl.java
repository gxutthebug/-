package com.itheima.travel.service.impl;

import com.itheima.travel.dao.CategoryDao;
import com.itheima.travel.pojo.Category;
import com.itheima.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 查询所有的线路分类
     * 第1次从mysql中查询，以后从redis中查询
     */
    @Override
    public List<Category> findAll() {
        //1. 先查Redis缓存
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        //2. 获取指定的线路分类键
        List<Category> categories = (List<Category>) ops.get("categories");

        //3. 如果为空，则查询数据库，得到List集合
        if (categories == null) {
            categories = categoryDao.findAll();
            //4. 向redis中存入集合
            ops.set("categories", categories);
        }
        //5. 返回List对象
        return categories;
    }
 }
