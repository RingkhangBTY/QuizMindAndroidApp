package com.ringkhang.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.QuestionsVpAdapter;
import com.ringkhang.myapplication.models_DTO.QuestionDTO;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ProgressBar progressBar;
    private TextView tvAnswered, tvQuestionCount, tvLeft;
    private Button btnPrevious, btnNext, btnSubmit;

    private QuestionsVpAdapter adapter;
    private ArrayList<QuestionDTO> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
            goToResult();
        }
    }

    private void goToResult() {
        int[] userAnswers = adapter.getUserAnswers();
        // TODO: pass to ResultActivity
        // Intent intent = new Intent(this, ResultActivity.class);
        // intent.putExtra("userAnswers", userAnswers);
        // startActivity(intent);
        // finish();

        Intent intent = new Intent(this, TestReviewActivity.class);
        intent.putExtra("userAnswers", adapter.getUserAnswers());
        startActivity(intent);
        finish();
    }

    private void setQuestions() {
        questionList.add(new QuestionDTO(
                "What happens if a private method in a superclass has a method with the exact same signature in its subclass?",
                "A compile-time error occurs because private methods cannot be overridden.",
                "The subclass method will be called via polymorphism if accessed through a superclass reference.",
                "The subclass method is a new, unrelated method specific to the subclass, not an override.",
                "A runtime error (NoSuchMethodError) will be thrown when attempting to invoke it polymorphically.",
                "The subclass method is a new, unrelated method specific to the subclass, not an override.",
                "Private methods are not inherited by subclasses. Therefore, a method in a subclass with the same signature as a private method in its superclass is considered a completely new and independent method for the subclass, not an override. Overriding applies only to inherited methods."
        ));
        questionList.add(new QuestionDTO(
                "Which access modifier allows members to be accessed from anywhere within the same package and also by subclasses, even if those subclasses are in a different package?",
                "private",
                "No modifier (default/package-private)",
                "protected",
                "public",
                "protected",
                "The protected access modifier grants access to members within the same package and to all subclasses, regardless of their package location."
        ));
        questionList.add(new QuestionDTO(
                "Consider the following Java code snippet:\ntry {\n    System.out.print(\"A\");\n    throw new NullPointerException();\n} catch (ArithmeticException e) {\n    System.out.print(\"B\");\n} catch (RuntimeException e) {\n    System.out.print(\"C\");\n} finally {\n    System.out.print(\"D\");\n}\nSystem.out.print(\"E\");\nWhat will be the output?",
                "ACDE", "ABDE", "ADE", "ACD",
                "ACDE",
                "The code first prints 'A'. Then, a NullPointerException is thrown. This is caught by catch (RuntimeException e), printing 'C'. The finally block prints 'D'. Then 'E' is printed."
        ));
        questionList.add(new QuestionDTO(
                "Which statement best describes a key difference between Java interfaces and abstract classes?",
                "A class can extend multiple abstract classes but can only implement one interface.",
                "An interface can declare instance variables, while an abstract class cannot.",
                "A class can implement multiple interfaces but can only extend one abstract class.",
                "Abstract classes support true multiple inheritance of implementation, unlike interfaces.",
                "A class can implement multiple interfaces but can only extend one abstract class.",
                "Java supports multiple inheritance of type (through interfaces) but not multiple inheritance of implementation."
        ));
        questionList.add(new QuestionDTO(
                "Given the following Java code:\nString s1 = \"Hello\";\nString s2 = s1.concat(\" World\");\nString s3 = s1;\ns1 = s1.toUpperCase();\nSystem.out.println(s1 + \", \" + s2 + \", \" + s3);\nWhat will be the output?",
                "HELLO, Hello World, HELLO",
                "HELLO, HELLO World, HELLO",
                "HELLO, Hello World, Hello",
                "Hello, Hello World, Hello",
                "HELLO, Hello World, Hello",
                "Strings in Java are immutable. s3 still points to the original \"Hello\". s1 is reassigned to \"HELLO\"."
        ));
    }
}