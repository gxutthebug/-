package com.itheima.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itheima.travel.entity.Result;

@RestController
public class TestController {
    @RequestMapping("/hello")
    public String hello() {

        return "YYDS";
    }
}
