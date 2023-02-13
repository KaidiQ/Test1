package com.example.student;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText EditText_Account, EditText_Password, EditText_Password_queren;
    private CheckBox checkBox;
    private Button Button_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_Account = findViewById(R.id.EditText_Account);
        EditText_Password = findViewById(R.id.EditText_Password);
        checkBox = findViewById(R.id.checkbox);
        Button_Login = findViewById(R.id.Button_Login);
        EditText_Password_queren = findViewById(R.id.EditText_Password_queren);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        //读取数据
        String username = sharedPreferences.getString("Account", "");
        String password = sharedPreferences.getString("Password", "");
        String quren = sharedPreferences.getString("queren", "");

        if (!username.equals("") && !password.equals("")) {
            EditText_Account.setText(username);
            EditText_Password.setText(password);
            EditText_Password_queren.setText(quren);
            checkBox.setChecked(true);
        }

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = EditText_Account.getText().toString();
                String password = EditText_Password.getText().toString();
                String quren = EditText_Password_queren.getText().toString();
                Intent intent = new Intent(Login.this, MainActivity.class);
                if (password.equals(quren)) {
                    if (account.equals("123") && password.equals("123")) {
                        if (checkBox.isChecked()) {
                            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Account", account);
                            editor.putString("Password", password);
                            editor.putString("queren", quren);
                            editor.apply();
                        }//记住密码
                        startActivity(intent);
                        Toast.makeText(Login.this, "登录成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "两次密码不一致，请重新输入！", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }
}