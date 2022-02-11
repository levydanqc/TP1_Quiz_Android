package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edUsername = findViewById(R.id.et_username);
        EditText edPwd = findViewById(R.id.et_pwd);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            if (edUsername.getText().toString().equals("cegep") && edPwd.getText().toString().equals("123")) {
                Intent i = new Intent(v.getContext(), Quiz.class);
                i.putExtra("username", "cegep");
                startActivity(i);
            }
        });
    }
}