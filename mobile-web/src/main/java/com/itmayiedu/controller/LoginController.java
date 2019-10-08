package com.itmayiedu.controller;

import com.itmayiedu.common.api.BaseApiCommon;
import com.itmayiedu.common.web.CookieUtil;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.feign.UserFeign;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/locaLogin")
    public String locaLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> loginMap = userFeign.login(userEntity);
        Integer code = (Integer) loginMap.get(BaseApiCommon.HTTP_CODE_NAME);
        if(!code.equals(BaseApiCommon.HTTP_200_CODE)){
            String msg = (String) loginMap.get("msg");
            request.setAttribute("error",msg);
            return "login";
        }
        String token = (String) loginMap.get("token");
        CookieUtil.addCookie(response,"token",token,30*30);
        return "index";
    }

    /**
     * 功能描述:
     *         生成QQ授权
     * @param request
     * @return
     * @throws QQConnectException
     */
    @RequestMapping("/authorizeUrl")
    public String authorizeUrl(HttpServletRequest request) throws QQConnectException {
        String authorizeURL = new Oauth().getAuthorizeURL(request);
        return "redirect:"+authorizeURL;
    }

    @RequestMapping("qqLoginCallback")
    public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws QQConnectException {
        //第一步获取授权码
        //第二步获取accesstoken
        AccessToken accessTokenByRequest = new Oauth().getAccessTokenByRequest(request);
        String accessToken = accessTokenByRequest.getAccessToken();
        if (StringUtils.isEmpty(accessToken)){
            request.setAttribute("QQ授权失败","errror" );
            return "error";
        }
        //第三步获取openid
        OpenID openID = new OpenID(accessToken);
        if (openID==null){
            request.setAttribute("QQ授权失败","errror" );
            return "error";
        }
        //数据查找openid是否关联,如何没有关联先跳转到关联账号页面,反之直接登录
        String userOpenID = openID.getUserOpenID();
        Map<String, Object> userLoginObjectMap = userFeign.userLoginOpenid(userOpenID);
        Integer  code = (Integer) userLoginObjectMap.get(BaseApiCommon.HTTP_CODE_NAME);
        if(code.equals(BaseApiCommon.HTTP_200_CODE)){
            String token = (String) userLoginObjectMap.get("data");
            CookieUtil.addCookie(response,"token",token,30*30);
            return "redirect:index";
        }
        session.setAttribute("openid",userOpenID);
        return "associatedAccount";
    }
}
