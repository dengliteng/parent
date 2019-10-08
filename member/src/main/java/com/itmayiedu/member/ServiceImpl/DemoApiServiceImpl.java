package com.itmayiedu.member.ServiceImpl;

import com.itmayiedu.api.service.DemoApiService;
import com.itmayiedu.common.Redis.BaserRedisService;
import com.itmayiedu.common.api.BaseApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class DemoApiServiceImpl extends BaseApiService implements DemoApiService {

    @Autowired
    private BaserRedisService baserRedisService;

    @Override
    public Map<String, Object> test() {
        log.info("this log 日志");
        return setResutSuccess();
    }

    @Override
    public Map<String, Object> setString(String key, String value) {
        baserRedisService.setString(key,value,null);
        return setResutSuccess();
    }

    @Override
    public Map<String, Object> getString(String key) {
        baserRedisService.getString(key);
        return setResutSuccess(key);
    }


}
