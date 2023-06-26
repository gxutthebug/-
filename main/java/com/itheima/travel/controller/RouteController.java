package com.itheima.travel.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.travel.pojo.*;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.utils.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/route", produces = "application/json;charset=utf-8")
public class RouteController {
    @Autowired
    private RouteService routeService;



    
    @RequestMapping("/findRouteListByCid")
    public PageInfo findRouteListByCid(@RequestParam(defaultValue = "5") int cid,
                                       @RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "") String rname) {
        PageInfo pageInfo = routeService.findRouteByCid(cid,pageNum,rname);
        return pageInfo;
    }
    





    /**
     * 通过rid查询线路的详情
     * @param rid 线路主键
     * @return 封装四张表的线路对象
     */
    @RequestMapping("/findRouteByRid")
    public Route findRouteByRid(@RequestParam(defaultValue = "1")Integer rid) {
        Route route = routeService.findRouteByRid(rid);

        return route;
    }



    /**
     * 首页各种数据获取查询
     * @param cid 所属分类，必输
     * @param num 收藏数量
     * @return
     */
    @RequestMapping("/findMostFavoriteRouteByCid")
    public List<Route> findMostFavoriteRouteByCid(Integer cid, Integer num)  {
        return routeService.findMostFavoriteRouteByCid(cid, num);
    }




}
