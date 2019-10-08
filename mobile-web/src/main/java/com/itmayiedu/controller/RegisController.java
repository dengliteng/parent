package com.itmayiedu.controller;

import com.itmayiedu.common.api.BaseApiCommon;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.feign.UserFeign;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class RegisController extends BaseController{

    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/locaRegist")
    public String locaRegist(){
        return "locaRegist";
    }

    @RequestMapping("/regist")
    public String regist(UserEntity userEntity, HttpServletRequest request){
       try{
           Map<String, Object> registMap = userFeign.regist(userEntity);
           Integer code = (Integer) registMap.get(BaseApiCommon.HTTP_CODE_NAME);
           if(!code.equals(BaseApiCommon.HTTP_200_CODE)){
               String msg = (String) registMap.get("msg");
               request.setAttribute("error",msg);
               return "locaRegist";
           }
           //注册成功
           return "login";
       }catch (Exception e){
           request.setAttribute("error","注册失败");
           return "locaRegist";
       }
    }

}
