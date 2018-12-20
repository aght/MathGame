package android.bcit.ca.mathgame.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.bcit.ca.mathgame.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        setStatus();

        Button nextQuestionButton = findViewById(R.id.next_button);
        nextQuestionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int score = getIntent().getIntExtra(MainActivity.SCORE_KEY, -1);
        int questionNumber = getIntent().getIntExtra(MainActivity.QUESTION_NUMBER_KEY, -1);

        if (questionNumber == MainActivity.MAX_QUESTIONS) {
            overridePendingTransition(0, 0);

            Intent i = new Intent(this, FinishActivity.class);
            i.putExtra(MainActivity.SCORE_KEY, score);
            i.putExtra(MainActivity.QUESTION_NUMBER_KEY, questionNumber);

            startActivity(i);
            finish();
        } else {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
            overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void setStatus() {
        boolean isCorrect = getIntent().getBooleanExtra(MainActivity.STATUS_KEY, false);

        TextView statusTextView = findViewById(R.id.status_textView);
        View rootView = statusTextView.getRootView();

        if (isCorrect) {
            statusTextView.setText(R.string.correct);
            int color = getResources().getColor(R.color.colorCorrect);
            rootView.setBackgroundColor(color);
            setStatusBarColor(color);
        } else {
            statusTextView.setText(R.string.incorrect);
            int color = getResources().getColor(R.color.colorIncorrect);
            rootView.setBackgroundColor(color);
            setStatusBarColor(color);
        }
    }

    private void setStatusBarColor(int color) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(color);
    }
}
