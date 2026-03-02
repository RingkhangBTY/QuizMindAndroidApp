package com.ringkhang.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ringkhang.myapplication.DTO.QuestionsSubmitDTO;
import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.TestReviewVPAdapter;
import com.ringkhang.myapplication.models_DTO.ScoreHistoryDisplay;
import com.ringkhang.myapplication.models_DTO.TestReview;

import java.util.ArrayList;

public class TestReviewActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Button btnPrevious, btnNext, btnDoneReview, btnTestAgain;
    private TextView tvTotalQuestions, tvCorrectAns, tvScore, tvFeedback;

    private ArrayList<QuestionsSubmitDTO> questionList ;
//    private
    private ScoreHistoryDisplay scoreHistoryDisplay;

    private TestReview testReview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.testReviewPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupButtons();
        loadTestReviewData();

        setupViewPager();
        setupScoreCard();
    }



    private void loadTestReviewData() {
        testReview = (TestReview) getIntent().getSerializableExtra("testReview");
        questionList = new ArrayList<>(testReview.getQuestionDetails());
        scoreHistoryDisplay = testReview.getScoreHistoryDisplay();

    }

    /** Get userAnswers array from TestActivity via Intent */
    private void receiveAnswers() {
//        userAnswers = getIntent().getIntArrayExtra("userAnswers");
//
//        // Fallback: if nothing passed (e.g. launched directly for testing), mock answers
//        if (userAnswers == null) {
//            userAnswers = new int[]{2, 2, 0, 2, 2};  // all correct for demo
//        }
    }

    /** Fills the score card at the top */
    private void setupScoreCard() {
        tvTotalQuestions.setText("Total: " + scoreHistoryDisplay.getTotal_question());
        tvCorrectAns.setText("Correct: " + scoreHistoryDisplay.getCorrect_ans());
        tvScore.setText("Score: " + scoreHistoryDisplay.getCorrect_ans() + " / " + scoreHistoryDisplay.getTotal_question());
        tvFeedback.setText(scoreHistoryDisplay.getFeedback());
    }

    private void setupViewPager() {
        TestReviewVPAdapter adapter = new TestReviewVPAdapter(this, questionList);
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateNavButtons(position);
            }
        });

        updateNavButtons(0);
    }



    private void updateNavButtons(int position) {
        boolean isFirst = (position == 0);
        boolean isLast  = (position == questionList.size() - 1);

        btnPrevious.setEnabled(!isFirst);
        btnNext.setEnabled(!isLast);
        btnNext.setAlpha(isLast ? 0.4f : 1f);
    }

    private void initViews() {
        viewPager2       = findViewById(R.id.viewPager2);
        btnPrevious      = findViewById(R.id.pv_btn_previous);
        btnNext          = findViewById(R.id.pv_btn_next);
        btnDoneReview    = findViewById(R.id.pv_done_review_btn);
        btnTestAgain     = findViewById(R.id.pv_test_again_btn);
        tvTotalQuestions = findViewById(R.id.pv_total_ques_textview);
        tvCorrectAns     = findViewById(R.id.pv_correct_ans_textview);
        tvScore          = findViewById(R.id.pv_score_testview);
        tvFeedback       = findViewById(R.id.pvFeedback_textView);
    }

    private void setupButtons() {
        btnPrevious.setOnClickListener(v -> {
            int cur = viewPager2.getCurrentItem();
            if (cur > 0) viewPager2.setCurrentItem(cur - 1);
        });

        btnNext.setOnClickListener(v -> {
            int cur = viewPager2.getCurrentItem();
            if (cur < questionList.size() - 1) viewPager2.setCurrentItem(cur + 1);
        });

        btnDoneReview.setOnClickListener(v -> {
            startActivity(new Intent(TestReviewActivity.this,MainActivity.class));
            finish();
        });

        btnTestAgain.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}