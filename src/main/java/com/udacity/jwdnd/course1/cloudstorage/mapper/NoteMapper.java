package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);


    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);


    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotesPerUser(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
    int deleteNote(int noteId, int userId);




}
