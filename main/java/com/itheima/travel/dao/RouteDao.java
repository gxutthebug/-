package com.itheima.travel.dao;

import com.itheima.travel.pojo.Route;
import com.itheima.travel.pojo.RouteImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface RouteDao {



    List<Route> findRouteByCid(@Param("cid") int cid, @Param("rname") String rname);

    /**
     * 通过rid查询三张表的信息：分类tab_category，商家tab_seller，线路tab_route
     * @param rid 线路id
     * @return 封装成Map对象，一个Map代表一条记录，键是：字段名，值是：记录的值
     */
    @Select("select * from tab_category inner join tab_route on tab_route.cid=tab_category.cid inner join tab_seller on tab_route.sid=tab_seller.sid where rid = #{rid};")
    Map<String, Object> findRouteByRid(Integer rid);

    /**
     * 通过rid查询1条线路的所有图片
     * @param rid 线路id
     * @return 某条线路的所有图片
     */
    @Select("select * from tab_route_img where rid=#{rid};")
    List<RouteImg> findRouteImgsByRid(Integer rid);


    @Select("select * from tab_route where rid=#{rid}")
    Route findRoute(int rid);

    /**
     * 首页各种数据获取查询
     * @param cid 所属分类，必输
     * @param num 收藏数量
     * @return
     */
    @Select("select * from tab_route where cid = #{cid} order by num limit #{num}")
    List<Route> findMostFavoriteRouteByCid(Integer cid, Integer num);
}
