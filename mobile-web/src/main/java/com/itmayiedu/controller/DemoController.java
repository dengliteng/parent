package com.itmayiedu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DemoController extends BaseController{
    @RequestMapping("/token")
    public String index(String token){
        log.info("我的web工程搭建成功啦!userName:{}"+getUserEntity(token).getUserName());
        return "index";
    }

}
