package com.itmayiedu.common.token;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {

    public String getToken(){
       return UUID.randomUUID().toString();
    }


}
