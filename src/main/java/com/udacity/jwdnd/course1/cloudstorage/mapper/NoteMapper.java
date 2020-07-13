package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface NoteMapper {

//    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId} ")
//    Note getNote(Integer noteId, Integer userId);
//

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);


    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotesPerUser(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
    int deleteNote(int noteId, int userId);

    @Update("UPDATE  NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId} and userId = #{userId}")
    int updateNote (int noteId, int userId, String noteTitle, String noteDescription);




}
