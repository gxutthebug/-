package com.itheima.travel.service;

import com.github.pagehelper.PageInfo;
import com.itheima.travel.pojo.Route;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RouteService {
    PageInfo findRouteByCid(int cid, int pageNum,String rname);
    Route findRouteByRid(Integer rid);


    Route findRoute(int rid);
    /**
     * 首页各种数据获取查询
     * @param cid 所属分类，必输
     * @param num 收藏数量
     * @return
     */
    List<Route> findMostFavoriteRouteByCid(Integer cid, Integer num) ;
}
