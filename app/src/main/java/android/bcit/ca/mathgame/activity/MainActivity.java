package android.bcit.ca.mathgame.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.bcit.ca.mathgame.generator.Question;
import android.bcit.ca.mathgame.generator.QuestionGenerator;
import android.bcit.ca.mathgame.R;
import android.bcit.ca.mathgame.utility.ColorGenerator;
import android.bcit.ca.mathgame.utility.RandomNumberGenerator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int FAKE_ANSWER_THRESHOLD = 5;
    private static final int RESULT_REQUEST_CODE = 1;
    private static final String SCORE = "Score: ";
    private static final String QUESTION = "Question: ";

    public static final String COLOR_PALETTE_KEY = "A100";
    public static final int MAX_QUESTIONS = 5;
    public static final String STATUS_KEY = "status";
    public static final String SCORE_KEY = "score";
    public static final String QUESTION_NUMBER_KEY = "question_number";

    private TextView questionTextView;
    private TextView questionNumberTextView;
    private TextView scoreTextView;
    private List<Button> answerButtons = new ArrayList<>();

    private int rootBackgroundColor = 0;
    private int score = 0;
    private int questionNumber = 0;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        questionTextView = findViewById(R.id.question_textView);
        scoreTextView = findViewById(R.id.score_textView);
        questionNumberTextView = findViewById(R.id.question_number_textView);

        answerButtons.add((Button) findViewById(R.id.answer_1));
        answerButtons.add((Button) findViewById(R.id.answer_2));
        answerButtons.add((Button) findViewById(R.id.answer_3));

        addListeners();

        FloatingActionButton cycleQuestionButton = findViewById(R.id.cycle_question_button);
        cycleQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuestion();
            }
        });

        createQuestion();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, ResultActivity.class);

        ActivityOptions options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_right,
                R.anim.hold
        );

        if (validateAnswer(((Button) view).getText().toString())) {
            i.putExtra(STATUS_KEY, true);
            score++;
        } else {
            i.putExtra(STATUS_KEY, false);
        }

        questionNumber++;

        i.putExtra(SCORE_KEY, score);
        i.putExtra(QUESTION_NUMBER_KEY, questionNumber);

        if (questionNumber == MAX_QUESTIONS) {
            startActivityForResult(i, RESULT_REQUEST_CODE, options.toBundle());
            finish();
        }

        startActivityForResult(i, RESULT_REQUEST_CODE, options.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            createQuestion();
        }
    }

    @Override
    public void onBackPressed() {

    }

    private boolean validateAnswer(String answerString) {
        try {
            int answer = Integer.parseInt(answerString);

            if (answer == question.getAnswer()) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return false;
    }

    private void addListeners() {
        for (Button button : answerButtons) {
            button.setOnClickListener(this);
        }
    }

    private void createQuestion() {
        question = QuestionGenerator.generateQuestion();

        questionTextView.setText(question.toString());

        int answer = question.getAnswer();
        int answerIndex = RandomNumberGenerator.randomInt(0, answerButtons.size() - 1);

        Set<Integer> usedNumbers = new HashSet<>();
        usedNumbers.add(answer);

        for (int i = 0; i < answerButtons.size(); i++) {
            if (i == answerIndex) {
                answerButtons.get(i).setText(String.valueOf(question.getAnswer()));
            } else {
                int n = usedNumbers.iterator().next();

                while (usedNumbers.contains(n)) {
                    boolean above = RandomNumberGenerator.randomInt(0, 1) == 1 ? true : false;

                    if (above) {
                        n = Math.abs(randomIntNAboveM(answer, FAKE_ANSWER_THRESHOLD));
                    } else {
                        n = Math.abs(randomIntNBelowM(answer, FAKE_ANSWER_THRESHOLD));
                    }
                }

                usedNumbers.add(n);

                answerButtons.get(i).setText(String.valueOf(n));
            }
        }

        int color = ColorGenerator.getMaterialColor(this, COLOR_PALETTE_KEY);

        while (color == rootBackgroundColor) {
            color = ColorGenerator.getMaterialColor(this, COLOR_PALETTE_KEY);
        }

        rootBackgroundColor = color;

        questionTextView.getRootView().getRootView().setBackgroundColor(color);
        setStatusBarColor(color);

        scoreTextView.setText(SCORE + score);
        questionNumberTextView.setText(QUESTION + questionNumber);
    }

    private int randomIntNAboveM(int m, int n) {
        return RandomNumberGenerator.randomInt(m + 1, m + n);
    }

    private int randomIntNBelowM(int m, int n) {
        return RandomNumberGenerator.randomInt(m - n, m - 1);
    }

    private void setStatusBarColor(int color) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(color);
    }
}
