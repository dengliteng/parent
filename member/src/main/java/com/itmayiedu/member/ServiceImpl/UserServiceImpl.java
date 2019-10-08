package com.itmayiedu.member.ServiceImpl;

import com.itmayiedu.api.service.UserSerice;
import com.itmayiedu.common.api.BaseApiService;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.member.ServiceImpl.manage.UserSeriveManage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 功能描述(用户服务)
 */
@RestController
@Slf4j
public class UserServiceImpl extends BaseApiService implements UserSerice {

    @Autowired
    private UserSeriveManage userSeriveManage;

    @Override
    public Map<String, Object> regist(@RequestBody UserEntity userEntity) {

       if(StringUtils.isEmpty(userEntity.getUserName())){
            return setResutParameterError("用户名称不能为空");
        }
        if(StringUtils.isEmpty(userEntity.getPassword())){
            return setResutParameterError("用户密码不能为空");
        }
       try{
           userSeriveManage.regist(userEntity);
           return setResutSuccess();
       }catch (Exception e){
           log.error("###regist() Error"+e);
           return setResutError("注册失败");

       }
    }

    @Override
    public Map<String, Object> login(@RequestBody UserEntity userEntity) {
        String userNameame = userEntity.getUserName();
        if(StringUtils.isEmpty(userNameame)){
            return setResutError("用户名不能为空");
        }
        String password = userEntity.getPassword();
        if(StringUtils.isEmpty(password)){
            return setResutError("密码不能为空");
        }
        return userSeriveManage.login(userEntity);
    }

    @Override
    public Map<String, Object> getToken(@RequestParam("token") String  token) {
        if(StringUtils.isEmpty(token)){
            return setResutError("token不能为空");
        }
        return userSeriveManage.getToken(token);
    }

    @Override
    public Map<String, Object> userLoginOpenid(@RequestParam("openid") String openid) {
        return userSeriveManage.userLoginOpenid(openid);
    }


}
