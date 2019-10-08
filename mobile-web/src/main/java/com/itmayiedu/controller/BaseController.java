package com.itmayiedu.controller;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.common.api.BaseApiCommon;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    private UserFeign userFeign;

    public UserEntity getUserEntity(String token) {

        Map<String, Object> userMap = userFeign.getToken(token);
        Integer code = (Integer) userMap.get(BaseApiCommon.HTTP_CODE_NAME);
        if (!code.equals(BaseApiCommon.HTTP_200_CODE)){
            return null;
        }
        //获取data的参数
        LinkedHashMap linkedHashMap = (LinkedHashMap) userMap.get(BaseApiCommon.HTTP_DATA_NAME);
        String json = new JSONObject(linkedHashMap).toJSONString();
        UserEntity userEntity = new JSONObject().parseObject(json, UserEntity.class);
        return userEntity;
    }
}
