package com.itmayiedu.member.ServiceImpl.manage;

import com.itmayiedu.entity.UserEntity;

import java.util.Map;

public interface UserSeriveManage {
    //功能描述(注册功能)
    public void regist(UserEntity userEntity);

    //MD5密码加密
   // public String md5PassSalt(String password,String phone);

    //用户登录
    public Map<String, Object> login(UserEntity userEntity);

    //根据token查询用户信息
    public Map<String,Object> getToken(String token);

    //使用openid关联用户信息
    public Map<String, Object> userLoginOpenid(String openid);
}
