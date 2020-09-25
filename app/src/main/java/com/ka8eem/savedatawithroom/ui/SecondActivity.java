package com.ka8eem.savedatawithroom.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.ka8eem.savedatawithroom.R;
import com.ka8eem.savedatawithroom.adapter.UserAdapter;
import com.ka8eem.savedatawithroom.models.UserModel;
import com.ka8eem.savedatawithroom.viewmodel.UserViewModel;

import java.util.List;

public class SecondActivity extends AppCompatActivity {


    // vars
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
    }

    private void initViews() {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        LiveData<List<UserModel>> ret = userViewModel.getAllUsers();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        ret.observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                progressDialog.dismiss();
                if (userModels != null) {
                    userAdapter.setList(userModels);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    Toast.makeText(SecondActivity.this, "no such users!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}