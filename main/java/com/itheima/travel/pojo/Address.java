package com.itheima.travel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址实体类
 * 标准的JavaBean:
 *  1.成员变量私有
 *  2.给提供get/set方法
 *  3.必须要有无参构造
 *  4.建议有满参构造
 */
@Data // get/set/toString/hashCode/equals   原理,在类编译后形成Class文件时添加方法
@NoArgsConstructor // 无参构造器
@AllArgsConstructor // 满参构造
public class Address implements Serializable {

    private Integer aid;

    private String contact;

    private String address;

    private String telephone;

    private String isdefault;

    private User user;

}
