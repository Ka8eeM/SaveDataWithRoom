package com.ka8eem.savedatawithroom.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ka8eem.savedatawithroom.models.UserModel;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(UserModel userModel);

    @Query("SELECT * FROM user_table")
    LiveData<List<UserModel>> getAllUsers();
}