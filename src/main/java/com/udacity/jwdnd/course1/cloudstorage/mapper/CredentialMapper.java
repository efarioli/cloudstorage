package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId} ")
    Note getNote(Integer noteId, Integer userId);


    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES (#{url}, #{userName}, #{key} , #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);


    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialPerUser(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
    int deleteNote(int noteId, int userId);

    @Update("UPDATE  NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId} and userId = #{userId}")
    int updateNote (int noteId, int userId, String noteTitle, String noteDescription);

}
