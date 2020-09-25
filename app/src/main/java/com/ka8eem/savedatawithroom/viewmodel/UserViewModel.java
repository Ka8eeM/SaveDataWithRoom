package com.ka8eem.savedatawithroom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ka8eem.savedatawithroom.models.UserModel;
import com.ka8eem.savedatawithroom.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<UserModel> userViewModel;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserModel>> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void insert(UserModel userModel) {
        userRepository.insert(userModel);
    }
}
