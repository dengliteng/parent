package com.itmayiedu.api.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/demo")
public interface DemoApiService {

    @GetMapping("/demo")
    public Map<String,Object> test();

    @GetMapping("/setString")
    public  Map<String,Object> setString(String key,String value);

    @GetMapping("/getString")
    public  Map<String,Object> getString(String key);

}
