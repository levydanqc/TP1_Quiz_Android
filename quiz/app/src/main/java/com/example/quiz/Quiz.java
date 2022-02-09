package com.example.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity implements View.OnClickListener {

    int index = 0;
    TextView tvQuestion;
    TextView tvBestScore;
    TextView tvCurrentScore;
    Button btnSettings;
    Button btnShare;
    Button btnFalse;
    Button btnTrue;
    Button btnPrevious;
    Button btnNext;

    String[] questions = new String[]{"Question 1", "Question 2", "Question 3", "Question 4", "Question 5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        createComponents();
        manageButtons();
        createQuestion();

    }

    void createQuestion() {
        tvQuestion.setText(questions[index]);
    }

    void manageButtons() {
        btnShare.setOnClickListener(this);
    }

    void createComponents() {
        tvQuestion = findViewById(R.id.tv_question);
        tvBestScore = findViewById(R.id.tv_bestScore);
        tvCurrentScore = findViewById(R.id.tv_currentScore);
        btnSettings = findViewById(R.id.btn_settings);
        btnShare = findViewById(R.id.btn_share);
        btnFalse = findViewById(R.id.btn_false);
        btnTrue = findViewById(R.id.btn_true);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_settings:
                break;
            case R.id.btn_share:
                break;
            case R.id.btn_true:
                break;
            case R.id.btn_false:
                break;
            case R.id.btn_previous:
                break;
            case R.id.btn_next:
                break;
        }
    }
}