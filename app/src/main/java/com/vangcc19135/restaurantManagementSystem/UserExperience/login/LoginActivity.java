package com.vangcc19135.restaurantManagementSystem.UserExperience.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.ActivityLoginBinding;
import com.vangcc19135.restaurantManagementSystem.MainActivity;
import com.vangcc19135.restaurantManagementSystem.UserExperience.Common;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;


    String employee = "employee";
    String manager = "admin";
    String password = "admin";

    String passswordE= "1234";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            binding.tvLogin.setOnClickListener(v -> {
                if (validateLogin()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    Common.putBoolean(LoginActivity.this, Common.IS_LOGIN, true);

                } else {
                    Toast.makeText(LoginActivity.this, "Account or Password invalid!!!", Toast.LENGTH_SHORT).show();
                }
            });

            binding.tipUserName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilUserName.setError(null);

                }
            });

            binding.tipUserName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilUserName.setError(null);

                }
            });

            binding.tipPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.tilPassword.setError(null);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean validateLogin() {
        //user name
        if (binding.tipUserName.getText().toString().isEmpty()) {
            binding.tilUserName.setError(getString(R.string.not_user_name));
        } else {
            binding.tilUserName.setError(null);
        }

        //password
        if (binding.tipPassword.getText().toString().isEmpty()) {
            binding.tilPassword.setError(getString(R.string.not_password));
        } else {
            binding.tilPassword.setError(null);
        }
        //
        if ( binding.tipUserName.getText().toString().trim().equals(manager)
                && binding.tipPassword.getText().toString().trim().equals(password)) {

                Common.putBoolean(LoginActivity.this, Common.IS_MANAGER, true);

            binding.tilUserName.setError(null);
            binding.tilPassword.setError(null);
            return true;
        }
        if (binding.tipUserName.getText().toString().trim().equals(employee)
                && binding.tipPassword.getText().toString().trim().equals(passswordE)){

                Common.putBoolean(LoginActivity.this, Common.IS_EMPLOYEE, true);
                Common.putBoolean(LoginActivity.this, Common.IS_MANAGER, false);

            binding.tilUserName.setError(null);
            binding.tilPassword.setError(null);
            return true;
        }

        return false;
    }

}