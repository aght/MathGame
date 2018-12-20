package android.bcit.ca.mathgame.activity;

import android.bcit.ca.mathgame.R;
import android.bcit.ca.mathgame.utility.ColorGenerator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        getSupportActionBar().hide();

        TextView finishMessageTextView = findViewById(R.id.finish_message_textView);

        int score = getIntent().getIntExtra(MainActivity.SCORE_KEY, -1);
        int questionNumber = getIntent().getIntExtra(MainActivity.QUESTION_NUMBER_KEY, -1);

        View rootView = finishMessageTextView.getRootView();

        if (score == questionNumber) {
            finishMessageTextView.setText(R.string.win);
            int color = ColorGenerator.getMaterialColor(this, MainActivity.COLOR_PALETTE_KEY);
            rootView.setBackgroundColor(color);
            setStatusBarColor(color);
        } else {
            finishMessageTextView.setText(R.string.lose);
            int color = getResources().getColor(R.color.colorIncorrect);
            rootView.setBackgroundColor(color);
            setStatusBarColor(color);
        }

        findViewById(R.id.play_again_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishActivity.this, MainActivity.class));
                finish();
            }
        });

        TextView scoreTextView = findViewById(R.id.score_textView);
        TextView percentageTextView = findViewById(R.id.percentage_textView);

        scoreTextView.setText(score + "/" + questionNumber);
        percentageTextView.setText(String.valueOf(100 * ((double) score / questionNumber)) + "%");
    }

    @Override
    public void onBackPressed() {

    }

    private void setStatusBarColor(int color) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(color);
    }
}
