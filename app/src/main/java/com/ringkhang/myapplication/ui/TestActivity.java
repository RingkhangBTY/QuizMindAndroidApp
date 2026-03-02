package com.ringkhang.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ringkhang.myapplication.DTO.QuestionsSubmitDTO;
import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.QuestionsVpAdapter;
import com.ringkhang.myapplication.data.MyDataResponseCallBack;
import com.ringkhang.myapplication.data.TestDatasource;
import com.ringkhang.myapplication.models_DTO.QuestionDetails;
import com.ringkhang.myapplication.models_DTO.SubmitQuizRequest;
import com.ringkhang.myapplication.models_DTO.TestReview;
import com.ringkhang.myapplication.models_DTO.UserInForQuizTest;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ProgressBar progressBar;
    private TextView tvAnswered, tvQuestionCount, tvLeft;
    private Button btnPrevious, btnNext, btnSubmit;

    private QuestionsVpAdapter adapter;
    private ArrayList<QuestionDetails> questionList = new ArrayList<>();
    private TestReview testReview;

    private TestDatasource testDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.testPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        testDatasource = new TestDatasource(TestActivity.this);

        initViews();
        setQuestions();
        setUpViewPager();
        setupButtons();
        refreshUI(0);
    }

    private void initViews() {
        viewPager2      = findViewById(R.id.tv_question_vp);
        progressBar     = findViewById(R.id.tv_progress_bar);
        tvAnswered      = findViewById(R.id.tv_answered);
        tvQuestionCount = findViewById(R.id.tv_question_count);
        tvLeft          = findViewById(R.id.tv_left);
        btnPrevious     = findViewById(R.id.pv_btn_previous);
        btnNext         = findViewById(R.id.pv_btn_next);
        btnSubmit       = findViewById(R.id.tv_btn_submit);
    }

    private void setUpViewPager() {
        adapter = new QuestionsVpAdapter(questionList, this, () -> {
            // Called whenever user selects an answer — refresh counters
            refreshUI(viewPager2.getCurrentItem());
        });

        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setUserInputEnabled(false); // navigate only via buttons
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                refreshUI(position);
            }
        });
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

        btnSubmit.setOnClickListener(v -> handleSubmit());
    }

    /** Refreshes progress bar, counters, and button states */
    private void refreshUI(int position) {
        int total    = questionList.size();
        int answered = adapter.getAnsweredCount();
        int left     = total - answered;

        // Progress bar by page position
        progressBar.setProgress((int) (((position + 1) / (float) total) * 100));

        tvAnswered.setText("Answered: " + answered);
        tvLeft.setText("Left: " + left);
        tvQuestionCount.setText((position + 1) + " / " + total);

        // Prev button
        btnPrevious.setEnabled(position > 0);

        // Next vs Submit
        boolean isLast = (position == total - 1);
        btnNext.setVisibility(isLast ? View.GONE : View.VISIBLE);
        btnSubmit.setVisibility(isLast ? View.VISIBLE : View.GONE);
    }

    private void handleSubmit() {
        int total    = questionList.size();
        int answered = adapter.getAnsweredCount();

        if (answered < total) {
            new AlertDialog.Builder(this)
                    .setTitle("Unanswered Questions")
                    .setMessage((total - answered) + " question(s) not answered. Submit anyway?")
                    .setPositiveButton("Submit", (d, w) -> goToResult())
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            submitTest();
//            goToResult();
        }
    }

    private void submitTest() {
        MyDataResponseCallBack<TestReview> callBack = new MyDataResponseCallBack<TestReview>() {
            @Override
            public void onSuccess(TestReview data) {
                testReview = data;
                goToResult();
            }

            @Override
            public void onError(int errorCode, String massage) {
                Toast.makeText(TestActivity.this,
                        "Error: "+errorCode+" Massage: "+massage,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(TestActivity.this,
                        "Error: "+t.getCause(),Toast.LENGTH_SHORT).show();
            }
        };

        SubmitQuizRequest submitQuizRequest = new SubmitQuizRequest();
        submitQuizRequest.setUserInput(
                (UserInForQuizTest) getIntent().getSerializableExtra("userTestFormInput")
        );
        submitQuizRequest.setQuestionsSubmitDTO(buildSubmitList());
        testDatasource.submitQuiz(callBack,submitQuizRequest);
    }

    /**
     * Converts each QuestionDetails into QuestionsSubmitDTO
     * and binds the user's selected answer from the adapter's userAnswers[]
     */
    private ArrayList<QuestionsSubmitDTO> buildSubmitList() {
        int[] userAnswers = adapter.getUserAnswers();
        ArrayList<QuestionsSubmitDTO> list = new ArrayList<>();

        for (int i = 0; i < questionList.size(); i++) {
            QuestionDetails q = questionList.get(i);

            QuestionsSubmitDTO dto = new QuestionsSubmitDTO();
            dto.setQuestion(q.getQuestion());
            dto.setOptionA(q.getOptionA());
            dto.setOptionB(q.getOptionB());
            dto.setOptionC(q.getOptionC());
            dto.setOptionD(q.getOptionD());
            dto.setAnswer(q.getAnswer());
            dto.setExplanation(q.getExplanation());

            // Map int index → actual option String
            // -1 means skipped → userAnswer stays null
            String userAnswer = null;
            switch (userAnswers[i]) {
                case 0: userAnswer = q.getOptionA(); break;
                case 1: userAnswer = q.getOptionB(); break;
                case 2: userAnswer = q.getOptionC(); break;
                case 3: userAnswer = q.getOptionD(); break;
            }
            dto.setUserAnswer(userAnswer);

            list.add(dto);
        }

        return list;
    }

    private void goToResult() {
        int[] userAnswers = adapter.getUserAnswers();
        Intent intent = new Intent(this, TestReviewActivity.class);
        intent.putExtra("testReview",testReview);
        startActivity(intent);
        finish();
    }

    private void setQuestions() {
        questionList = (ArrayList<QuestionDetails>) getIntent()
                .getSerializableExtra("quizDetails");
    }
}