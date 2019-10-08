package com.itmayiedu.member.ServiceImpl.dao;

import com.itmayiedu.common.mybatis.BaseDao;
import com.itmayiedu.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseDao {

    @Select("SELECT id,userName,PASSWORD,phone,email,created,updated FROM mb_user WHERE username = #{username} AND PASSWORD=#{password}")
    public UserEntity getUserNameAndPwd(@Param("username") String userName, @Param("password") String password);

    @Select("SELECT id,userName,PASSWORD,phone,email,created,updated FROM mb_user WHERE id = #{id}")
    public UserEntity getUserInfo(@Param("id") int id);

    @Select("SELECT id,userName,PASSWORD,phone,email,created,updated FROM mb_user WHERE openid = #{openid}")
    public UserEntity findUserOpenId(@Param("openid") String openid);

}
