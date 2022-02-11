package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    EditText etUser;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        createComponents();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");
        actionBar.setDisplayHomeAsUpEnabled(true);

        etUser.setHint(getIntent().getStringExtra("username"));

        btnSave.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void createComponents() {
        etUser = findViewById(R.id.et_user);
        btnSave = findViewById(R.id.btn_save);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String username = etUser.getText().toString();
                Intent quiz = new Intent();
                quiz.putExtra("username", username);
                setResult(RESULT_OK, quiz);
                finish();
        }
    }
}