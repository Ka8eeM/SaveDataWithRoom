package com.ka8eem.savedatawithroom.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ka8eem.savedatawithroom.interfaces.UserDAO;
import com.ka8eem.savedatawithroom.models.UserModel;

@Database(entities = {UserModel.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase instance;

    public abstract UserDAO userDAO();

    public static synchronized UserDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDataBase.class,
                    "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
