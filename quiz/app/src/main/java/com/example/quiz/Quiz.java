package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Quiz extends AppCompatActivity implements View.OnClickListener {

    int index = 0;
    int currentScore = 0;
    int bestScore;
    TextView tvQuestion;
    TextView tvBestScore;
    TextView tvCurrentScore;
    FloatingActionButton btnSettings;
    FloatingActionButton btnShare;
    Button btnFalse;
    Button btnTrue;
    Button btnPrevious;
    Button btnNext;

    String[] questions = new String[]{"Question 1", "Question 2", "Question 3", "Question 4", "Question 5"};
    Boolean[] reponses = new Boolean[]{true, false, false, true, true};
    Boolean[] answered = new Boolean[]{false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        createComponents();
        manageButtons();
        createView();
    }

    @Override
    protected void onStop() {
        writeBestScore();
        super.onStop();
    }

    void readBestScore() {
        // Récupère le meilleur score du joueur.
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        bestScore = sharedPref.getInt(getString(R.string.best), 0);
    }

    void writeBestScore() {
        // Enregistre le record du joueur.
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.best), bestScore);
        editor.apply();
    }

    void createView() {
        // Mise-à-jour de la vue de la question.
        // Active ou désactive les bouttons de la vue.
        if (index >= 0 && index < 5)
            tvQuestion.setText(questions[index]);
        else
            tvQuestion.setText(R.string.finish);

        btnTrue.setEnabled(!answered[index]);
        btnFalse.setEnabled(!answered[index]);

        if (index >= 4)
            btnNext.setEnabled(false);
        else if (index <= 0)
            btnPrevious.setEnabled(false);
        else {
            btnNext.setEnabled(true);
            btnPrevious.setEnabled(true);
        }


        tvCurrentScore.setText(String.valueOf(currentScore));
        readBestScore();
        tvBestScore.setText(String.valueOf(bestScore));
    }

    void manageButtons() {
        btnSettings.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnFalse.setOnClickListener(this);
        btnTrue.setOnClickListener(this);
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey! Viens me battre à 'Quiz, by Dan Lévy'." +
                        "Mon score actuelle est: " + currentScore + ". Et mon record est: " + bestScore);
                startActivity(Intent.createChooser(intent, ""));
                break;
            case R.id.btn_true:
                checkAnswer(true);
                break;
            case R.id.btn_false:
                checkAnswer(false);
                break;
            case R.id.btn_previous:
                moveQuestion(-1);
                break;
            case R.id.btn_next:
                moveQuestion(1);
                break;
        }
    }

    void checkAnswer(boolean value) {
        // Vérification de la réponse de l'utilisateur.
        if (reponses[index].equals(value))
            currentScore += 1;
        else
            currentScore -= currentScore > 0 ? 1 : 0;
        answered[index] = true;
        moveQuestion(index < 4 ? 1 : 0);
    }

    void moveQuestion(int moving) {
        // Change de question
        if (index >= 0 && index <= 4) {
            index += moving;
        }
        createView();
    }
}