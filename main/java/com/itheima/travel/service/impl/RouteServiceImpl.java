package com.itheima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.travel.dao.RouteDao;
import com.itheima.travel.pojo.Category;
import com.itheima.travel.pojo.Route;
import com.itheima.travel.pojo.RouteImg;
import com.itheima.travel.pojo.Seller;
import com.itheima.travel.service.RouteService;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;

    @Override
    public PageInfo findRouteByCid(int cid, int pageNum,String rname) {
        PageHelper.startPage(pageNum,3);
        List<Route> routes = routeDao.findRouteByCid(cid,rname);
        PageInfo<Route> pageInfo = new PageInfo<>(routes);
        return pageInfo;
    }





    @SneakyThrows
    @Override
    public Route findRouteByRid(Integer rid) {
// 1. 调用DAO得到一个Map对象，包含三张表的信息：线路，分类，商家
        Map<String,Object> map = routeDao.findRouteByRid(rid);
// 2. 实例化三个对象Route, Category, Seller
        Route route = new Route();
        Category category = new Category();
        Seller seller = new Seller();
// 3. 使用BeanUtils将三个对象的属性都从Map中复制
        BeanUtils.populate(route,map);
        BeanUtils.populate(category,map);
        BeanUtils.populate(seller,map);
// 4. 调用DAO得到线路对应的图片集合
        List<RouteImg> imgList = routeDao.findRouteImgsByRid(rid);
// 5. 将分类，商家，图片集合封装到Route对象的属性中
        route.setCategory(category);
        route.setSeller(seller);
        route.setRouteImgList(imgList);
// 6. 返回封装好的Route对象
        return route;
    }


    @Override
    public Route findRoute(int rid) {
        return routeDao.findRoute(rid);
    }

    /**
     * 首页各种数据获取查询
     * @param cid 所属分类，必输
     * @param num 收藏数量
     * @return
     */
    @Override
    public List<Route> findMostFavoriteRouteByCid(Integer cid, Integer num) {
        return routeDao.findMostFavoriteRouteByCid(cid,num);
    }
}
