package com.example.interfaceblog.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.interfaceblog.entities.Blog;

import java.util.List;

@Dao
public interface BlogDAO {

    @Insert
    void addBlog(Blog b);
    @Query("select * from Blog")
    List <Blog> getAll();
}
