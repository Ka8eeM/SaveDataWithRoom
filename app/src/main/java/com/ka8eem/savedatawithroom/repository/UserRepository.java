package com.ka8eem.savedatawithroom.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ka8eem.savedatawithroom.interfaces.UserDAO;
import com.ka8eem.savedatawithroom.models.UserModel;

import java.util.List;

public class UserRepository {

    private UserDAO userDAO;
    private LiveData<List<UserModel>> userModelLiveData;

    public UserRepository(Application application) {
        UserDataBase userDataBase = UserDataBase.getInstance(application);
        userDAO = userDataBase.userDAO();
    }

    public void insert(UserModel userModel) {
        InsertDataAsyncTask insertDataAsyncTask = new InsertDataAsyncTask(userDAO);
        insertDataAsyncTask.execute(userModel);
    }

    public LiveData<List<UserModel>> getAllUsers() {
        userModelLiveData = userDAO.getAllUsers();
        return userModelLiveData;
    }


    private static class InsertDataAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDAO userDAO;

        private InsertDataAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(UserModel... userModels) {
            userDAO.insertUser(userModels[0]);
            return null;
        }
    }
}
