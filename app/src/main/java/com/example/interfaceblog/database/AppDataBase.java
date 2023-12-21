package com.example.interfaceblog.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.interfaceblog.DAO.BlogDAO;
import com.example.interfaceblog.entities.Blog;


@Database(entities = {Blog.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
  public static AppDataBase instance ;
  public abstract BlogDAO blogDAO();
  public static AppDataBase getInstance(Context context){
      if(instance== null){
          instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "blog")
                  .allowMainThreadQueries()
                  .build();

      }
      return instance;

  }


}
