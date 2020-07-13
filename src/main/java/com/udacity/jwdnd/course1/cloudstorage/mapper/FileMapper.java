package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userId = #{userId} ")
    File getFile(Integer fileId, Integer userId);




    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES (#{filename}, #{contentType}, #{fileSize} , #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);


    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesPerUser(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    int deleteFile(int fileId, int userId);
}
