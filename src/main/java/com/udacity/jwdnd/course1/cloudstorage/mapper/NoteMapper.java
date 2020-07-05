package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);


    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{notetitle}, #{notedescription}, #{users.userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);


}
