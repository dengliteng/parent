package com.itmayiedu.util;

import com.itmayiedu.common.entity.TestEntity;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * 功能描述 : (反射工具类)
 */
public class ReflectionUtils {

    /**
     * 功能描述 : (父类和子类一起获取属性)
     * @return
     */
    public static String fatherAndSonFields(Object obj){
        if(obj==null){
            return null;
        }
        //获取class文件
        Class classInfo = obj.getClass();
        //获取当前类属性sql
        //获取子类的
        Field[] declaredSon = classInfo.getDeclaredFields();
        String sonString = getField(declaredSon);
        //获取父类的属性
        Field[] declaredFather = classInfo.getSuperclass().getDeclaredFields();
        String fatherString = getField(declaredFather);
        return fatherString+","+sonString;
    }


    /**
     * 功能描述: (获取类的属性,拼接属性sql)
     * @param obj
     * @return
     */
//    public static String getInsertFields(Object obj){
//        if(obj==null){
//            return null;
//        }
//        //获取class文件
//        Class classInfo = obj.getClass();
//        //获取当前类的属性
//        Field[] declaredFields = classInfo.getDeclaredFields();
//        return  getField(declaredFields);
//
//    }

    //获取属性
    public static String getField( Field[] declaredFields){
        StringBuffer sf = new StringBuffer();
        for(int i=0;i<declaredFields.length;i++){
            sf.append(declaredFields[i].getName());
            if(i<declaredFields.length-1){
                sf.append(",");
            }
        }
        return sf.toString();
    }

    //获取属性值
    public static String getFieldValue(Object obj, Field[] declaredFields){
        StringBuffer sf = new StringBuffer();
        for(int i=0;i<declaredFields.length;i++){
           try {
               declaredFields[i].setAccessible(true);
               Object value = declaredFields[i].get(obj);
               boolean flag = false;
               if (value!=null&&(value instanceof String) || (value instanceof Timestamp)) {
                    flag = true;
               }
               if(flag){
                   sf.append("'");
                   sf.append(value);
                   sf.append("'");
               }else{
                   sf.append(value);
               }
               if(i<declaredFields.length-1){
                   sf.append(",");
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        return sf.toString();
    }

    //获取属性值
    public static String fatherAndSonValue(Object obj){
        if(obj==null){
            return null;
        }
        //获取class文件
        Class classInfo = obj.getClass();
        //获取当前类属性sql
        //获取子类的
        Field[] declaredSon = classInfo.getDeclaredFields();
        String sonString = getFieldValue(obj,declaredSon);
        //获取父类的属性
        Field[] declaredFather = classInfo.getSuperclass().getDeclaredFields();
        String fatherString = getFieldValue(obj,declaredFather);
        return fatherString+","+sonString;
    }

    public static void main(String[] args){
        TestEntity testEntity = new TestEntity();
        testEntity.setUsername("zhangsan");
        testEntity.setPassword("123456");
        testEntity.setPhone("13052537938");
        testEntity.setEmail("1915441820@qq.com");
        testEntity.setCreated(com.itmayiedu.utils.DateUtils.getTimestamp());
        testEntity.setUpdated(com.itmayiedu.utils.DateUtils.getTimestamp());
        String value = fatherAndSonValue(testEntity);
        String fields = fatherAndSonFields(testEntity);
       // System.out.println(value);
        //封装sql语句
        SQL sql = new SQL(){{
            INSERT_INTO("mb_user");
            VALUES(fields,value);

        }};
        System.out.println(sql);
    }

}
