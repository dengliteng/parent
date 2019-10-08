package com.itmayiedu.api.service;

import com.itmayiedu.entity.UserEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 功能描述(用户服务)
 */
@RequestMapping("/member")
public interface UserSerice {

    /**
     * 功能描述: (用户注册)
     * @param userEntity
     * @return
     */
    @PostMapping("/regist")
    public Map<String,Object> regist(@RequestBody UserEntity userEntity);


    /**
     * 功能描述 : (用户注册)
     *         登录成功后,生成token,作为key,将用户userId作为Valuec存放在redis中,
     *       返回token给客户端
     */
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody UserEntity userEntity);

    /**
     * 功能描述 : (使用token查找用户信息)
     * @param token
     * @return
     */
    @PostMapping("/getToken")
    public Map<String,Object> getToken(@RequestParam("token") String token);


    /**
     * 功能描述 : (使用openid关联用户信息)
     * @param openid
     * @return
     */
    @PostMapping("/userLoginOpenid")
    public Map<String,Object> userLoginOpenid(@RequestParam("openid") String openid);
}
