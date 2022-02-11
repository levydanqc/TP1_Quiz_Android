package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Quiz extends AppCompatActivity implements View.OnClickListener {
    int REQUEST_CODE = 420;

    int index = 0;
    int currentScore = 0;
    int bestScore;
    String username;

    TextView tvQuestion;
    TextView tvBestScore;
    TextView tvCurrentScore;
    FloatingActionButton btnSettings;
    FloatingActionButton btnShare;
    Button btnFalse;
    Button btnTrue;
    Button btnPrevious;
    Button btnNext;

    String[] questions = new String[]{"La terre est plate.", "Elon Musk est un réptilien.",
            "JFK n'a pas été assiné par la CIA.", "Michael Jackson est mort.", "L'amant de Marilyn Monroe était Winston Churchill."};
    boolean[] reponses = new boolean[]{true, false, false, true, true};
    boolean[] answered = new boolean[]{false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        username = getIntent().getStringExtra("username");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        createComponents();
        manageButtons();
        createView();

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

        getSupportActionBar().setTitle(username.equals("") ? "Quiz" : username);

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
                Intent settings = new Intent(this, Settings.class);
                settings.putExtra("username", username);
                startActivityForResult(settings, REQUEST_CODE);
                break;
            case R.id.btn_share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "Hey! Viens me battre à 'Quiz, by Dan Lévy'." +
                        "Mon score actuelle est: " + currentScore + ". Et mon record est: " + bestScore);
                startActivity(Intent.createChooser(share, ""));
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
        if (reponses[index] == value) {
            currentScore += 1;
            Toast.makeText(getApplicationContext(), R.string.goodAnswer, Toast.LENGTH_SHORT).show();
        } else {
            currentScore -= currentScore > 0 ? 1 : 0;
            Toast.makeText(getApplicationContext(), R.string.wrongAnswer, Toast.LENGTH_SHORT).show();
        }
        answered[index] = true;

        if (currentScore > bestScore)
            bestScore = currentScore;
        writeBestScore();

        moveQuestion(index < 4 ? 1 : 0);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("currentScore", currentScore);
        outState.putInt("index", index);
        outState.putString("username", username);
        outState.putBooleanArray("answered", answered);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentScore = savedInstanceState.getInt("currentScore");
        index = savedInstanceState.getInt("index");
        username = savedInstanceState.getString("username");
        answered = savedInstanceState.getBooleanArray("answered");
    }

    void moveQuestion(int moving) {
        // Change de question
        if (index >= 0 && index <= 4) {
            index += moving;
        }
        createView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Update le username après la modification
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            username = data.getStringExtra("username");
            createView();
        }
    }
}