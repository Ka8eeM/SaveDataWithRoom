package com.ka8eem.savedatawithroom.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ka8eem.savedatawithroom.R;
import com.ka8eem.savedatawithroom.models.UserModel;
import com.ka8eem.savedatawithroom.viewmodel.UserViewModel;

public class FirstActivity extends AppCompatActivity {

    // widgets
    private TextInputLayout inputLayoutUserName, inputLayoutUserAge, inputLayoutUserJob;
    Button saveUser, showUser;
    RadioGroup radioGroup;

    // vars
    private UserViewModel userViewModel;
    private String userName, gender, age, job;
    private boolean flag;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initViews(savedInstanceState);
    }

    private void initViews(Bundle bundle) {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        preferences = getSharedPreferences("user_found", MODE_PRIVATE);
        editor = preferences.edit();
        flag = true;
        gender = "N/A";
        inputLayoutUserName = findViewById(R.id.user_name);
        inputLayoutUserAge = findViewById(R.id.age);
        inputLayoutUserJob = findViewById(R.id.job);
        saveUser = findViewById(R.id.btn_save);
        showUser = findViewById(R.id.btn_show);
        radioGroup = findViewById(R.id.radio_group_gender);
        if (bundle != null) {
            inputLayoutUserName.getEditText().setText(bundle.getString("name"));
            inputLayoutUserAge.getEditText().setText(bundle.getString("age"));
            inputLayoutUserJob.getEditText().setText(bundle.getString("job"));
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_radio_btn:
                        gender = getString(R.string.male);
                        break;
                    case R.id.female_radio_btn:
                        gender = getString(R.string.female);
                        break;
                }
            }
        });

        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = inputLayoutUserName.getEditText().getText().toString();
                age = inputLayoutUserAge.getEditText().getText().toString();
                job = inputLayoutUserJob.getEditText().getText().toString();
                flag = true;
                validateInput();
                if (flag == false)
                    return;
                UserModel userModel = new UserModel(userName, age, gender, job);
                userViewModel.insert(userModel);
                editor.putBoolean("found", true);
                editor.commit();
                editor.apply();
                clearTextFields();
                Toast.makeText(FirstActivity.this, getString(R.string.user_saved), Toast.LENGTH_SHORT).show();
            }
        });
        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = preferences.getBoolean("found", false);
                if (!ok) {
                    Toast.makeText(FirstActivity.this, getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("id", "1");
                startActivity(intent);
            }
        });
    }

    private void validateInput() {
        if (gender.equals("N/A")) {
            flag = false;
            Toast.makeText(this, getString(R.string.please_check), Toast.LENGTH_SHORT).show();
        }

        if (job.equals("")) {
            flag = false;
            inputLayoutUserJob.getEditText().setError(getString(R.string.job_required));
            inputLayoutUserJob.getEditText().requestFocus();
        }
        if (age.equals("")) {
            flag = false;
            inputLayoutUserAge.getEditText().setError(getString(R.string.age_required));
            inputLayoutUserAge.getEditText().requestFocus();
        } else if (age != null && !age.equals("")) {
            boolean ok = true;
            age = age.trim();
            for (int i = 0; i < age.length(); i++) {
                ok &= Character.isDigit(age.charAt(i));
            }
            if (!ok) {
                flag = false;
                inputLayoutUserAge.getEditText().setError(getString(R.string.enter_valid_age));
                inputLayoutUserAge.getEditText().requestFocus();
            }
        }
        if (userName.equals("")) {
            flag = false;
            inputLayoutUserName.getEditText().setError(getString(R.string.name_required));
            inputLayoutUserName.getEditText().requestFocus();
        }
    }

    private void clearTextFields() {
        inputLayoutUserName.getEditText().setText("");
        inputLayoutUserAge.getEditText().setText("");
        inputLayoutUserJob.getEditText().setText("");
        radioGroup.check(-1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        userName = inputLayoutUserName.getEditText().getText().toString();
        age = inputLayoutUserAge.getEditText().getText().toString();
        job = inputLayoutUserJob.getEditText().getText().toString();
        outState.putString("name", userName);
        outState.putString("age", age);
        outState.putString("job", job);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}