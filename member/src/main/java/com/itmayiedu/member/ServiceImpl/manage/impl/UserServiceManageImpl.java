package com.itmayiedu.member.ServiceImpl.manage.impl;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.common.Redis.BaserRedisService;
import com.itmayiedu.common.Redis.Constants;
import com.itmayiedu.common.api.BaseApiService;
import com.itmayiedu.common.api.DBTableName;
import com.itmayiedu.common.token.TokenUtil;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.member.ServiceImpl.dao.UserDao;
import com.itmayiedu.member.ServiceImpl.manage.ActiveMQ.RegisterMailboxProducer;
import com.itmayiedu.member.ServiceImpl.manage.UserSeriveManage;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Map;

@Service
@Slf4j
public class UserServiceManageImpl extends BaseApiService implements UserSeriveManage {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BaserRedisService baserRedisService;

    @Value("${messages.queue}")
    private String messages_queue;

    //会员注册和MQ信息推送
    @Override
    public void regist(UserEntity userEntity) {
        userEntity.setCreated(com.itmayiedu.utils.DateUtils.getTimestamp());
        userEntity.setUpdated(com.itmayiedu.utils.DateUtils.getTimestamp());
        String userName = userEntity.getUserName();
        String password = userEntity.getPassword();
        userEntity.setPassword(md5PassSalt(userName,password));
        userDao.save(userEntity, DBTableName.TABLE_MB_USER);

        //MQ消息推送
        Destination destination = new ActiveMQQueue(messages_queue);
        String message = message(userEntity.getEmail(),userEntity.getUserName());
        log.info("###regist()注册发送邮件报文message:{}"+message);
        registerMailboxProducer.send(destination,message);
    }

    //密码加密
    public String md5PassSalt(String userName, String password) {
        String newPass = com.itmayiedu.utils.MD5Util.MD5(userName + password);
        return newPass;
   }

    //解析报文的封装
    private String message(String email,String userName) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", "sms_mail");
        JSONObject content = new JSONObject();
        content.put("userName",userName);
        content.put("email", email);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();

    }

    /**
     * 用户登录
     */
    @Override
    public Map<String, Object> login(UserEntity userEntity) {
        //往数据库进行查找数据
        String userName = userEntity.getUserName();
        String password = userEntity.getPassword();
        String newPwd = md5PassSalt(userName,password);
        UserEntity userNameAndPwd = userDao.getUserNameAndPwd(userName, newPwd);
        if(userNameAndPwd ==null){
            return setResutError(userName);
        }
        //生成对应的token
        String token = tokenUtil.getToken();
        //将用户的userId作为value,key为自定义的令牌 存放在redis中
        Long userId = userNameAndPwd.getId();
        //key作为自定义令牌,用户的userID作为value 存放在redis中
        baserRedisService.setString(token,userId+"", Constants.USER_TOKEN_TERMVALIDITY);
        //返回token
        return setResutSuccessDate(token);
    }

    /**
     * 根据token查询
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> getToken(String token) {
        //从Redis中查询userId
      String  userID = baserRedisService.getString(token);
      if(StringUtils.isEmpty(userID)){
          return setResutError("用户已经过期");
      }
      int id = Integer.parseInt(userID);
        UserEntity userInfo = userDao.getUserInfo(id);
        //密码不需要返回客户端,进行处理
        userInfo.setPassword(null);
        return setResutSuccess(userInfo);
    }

    @Override
    public Map<String, Object> userLoginOpenid(String openid) {
        UserEntity userEntity = userDao.findUserOpenId(openid);
        if(userEntity==null){
            return setResutError("没有关联用户");
        }
        //生成对应的token
        String token = tokenUtil.getToken();
        //将用户的userId作为value,key为自定义的令牌 存放在redis中
        Long userId = userEntity.getId();
        //key作为自定义令牌,用户的userID作为value 存放在redis中
        baserRedisService.setString(token,userId+"", Constants.USER_TOKEN_TERMVALIDITY);
        //自动登录
        return setResutSuccessDate(token);
    }


}
