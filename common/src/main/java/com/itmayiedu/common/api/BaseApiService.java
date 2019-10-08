package com.itmayiedu.common.api;


import java.util.HashMap;
import java.util.Map;

/**
 * @ClassDesc:   功能描述:(通用baseApi 父类 )
 */
public class BaseApiService {

    public Map<String,Object> setResutError(String success){
        return setResut(BaseApiCommon.HTTP_500_CODE,success,null);
    }

    public Map<String,Object> setResutSuccess(Object data){
        return setResut(BaseApiCommon.HTTP_200_CODE,BaseApiCommon.HTTP_SUCCESS_NAME,data);
    }

    public Map<String,Object> setResutSuccess(){
        return setResut(BaseApiCommon.HTTP_200_CODE,BaseApiCommon.HTTP_SUCCESS_NAME,null);
    }
    public Map<String,Object> setResutSuccessDate(String Data){
        return setResut(BaseApiCommon.HTTP_200_CODE,BaseApiCommon.HTTP_200_NAME,Data);
    }

    public Map<String,Object> setResutParameterError(String success){
        return setResut(BaseApiCommon.HTTP_400_CODE,BaseApiCommon.HTTP_200_NAME,null);
    }

    public Map<String,Object> setResut(Integer code,String success,Object data){
        Map<String,Object> result =  new HashMap<String,Object>();
        result.put(BaseApiCommon.HTTP_CODE_NAME,code);
        result.put(BaseApiCommon.HTTP_SUCCESS_NAME,success);
        if (data!=null){
            result.put(BaseApiCommon.HTTP_DATA_NAME,data);
        }
        return result;
    }

}
