package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId} ")
    Credential getCredential(Integer credentialId, Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES (#{url}, #{userName}, #{key} , #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);


    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialPerUser(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId}")
    int deleteCredential(int credentialId, int userId);

    @Update("UPDATE  CREDENTIALS SET url = #{url}, userName = #{username}, password = #{password}  WHERE credentialId = #{credentialId} and userId = #{userId}")
    int updateCredential (int credentialId, int userId, String url, String username, String password);



}
