package com.itmayiedu.common.mybatis;

import com.itmayiedu.util.ReflectionUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BaseProvider {

    //功能描述:(自定义封装sql语句)
    public String save(Map<String,Object> map){
        //实体类
        Object object = map.get("obj");
        //表名称
        String table = (String) map.get("table");
        //生成添加的sql语句,使用反射机制
        //步骤:使用反射机制
        SQL sql = new SQL(){{
            INSERT_INTO(table);
            VALUES(ReflectionUtils.fatherAndSonFields(object),ReflectionUtils.fatherAndSonValue(object));

        }};
        return sql.toString();
    }

}
