package com.ringkhang.myapplication.activities;

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

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.TestReviewVPAdapter;
import com.ringkhang.myapplication.models.QuestionDTO;

import java.util.ArrayList;

public class TestReviewActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Button btnPrevious, btnNext, btnDoneReview, btnTestAgain;
    private TextView tvTotalQuestions, tvCorrectAns, tvScore, tvFeedback;

    private ArrayList<QuestionDTO> questionList = new ArrayList<>();
    private int[] userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        loadQuestions();
        receiveAnswers();    // get userAnswers passed from TestActivity
        setupScoreCard();
        setupViewPager();
        setupButtons();
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

    /** Hardcoded for now — replace with API call later */
    private void loadQuestions() {
        questionList.add(new QuestionDTO(
                "What happens if a private method in a superclass has a method with the exact same signature in its subclass?",
                "A compile-time error occurs because private methods cannot be overridden.",
                "The subclass method will be called via polymorphism if accessed through a superclass reference.",
                "The subclass method is a new, unrelated method specific to the subclass, not an override.",
                "A runtime error (NoSuchMethodError) will be thrown when attempting to invoke it polymorphically.",
                "The subclass method is a new, unrelated method specific to the subclass, not an override.",
                "Private methods are not inherited by subclasses. Therefore, a method in a subclass with the same signature as a private method in its superclass is considered a completely new and independent method for the subclass, not an override."
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
                "NullPointerException is caught by catch(RuntimeException), printing C. Finally prints D. Then E is printed."
        ));
        questionList.add(new QuestionDTO(
                "Which statement best describes a key difference between Java interfaces and abstract classes?",
                "A class can extend multiple abstract classes but can only implement one interface.",
                "An interface can declare instance variables, while an abstract class cannot.",
                "A class can implement multiple interfaces but can only extend one abstract class.",
                "Abstract classes support true multiple inheritance of implementation, unlike interfaces.",
                "A class can implement multiple interfaces but can only extend one abstract class.",
                "Java supports multiple inheritance of type through interfaces but not multiple inheritance of implementation."
        ));
        questionList.add(new QuestionDTO(
                "Given the following Java code:\nString s1 = \"Hello\";\nString s2 = s1.concat(\" World\");\nString s3 = s1;\ns1 = s1.toUpperCase();\nSystem.out.println(s1 + \", \" + s2 + \", \" + s3);\nWhat will be the output?",
                "HELLO, Hello World, HELLO",
                "HELLO, HELLO World, HELLO",
                "HELLO, Hello World, Hello",
                "Hello, Hello World, Hello",
                "HELLO, Hello World, Hello",
                "Strings in Java are immutable. s3 still points to the original Hello. s1 is reassigned to HELLO."
        ));
    }

    /** Get userAnswers array from TestActivity via Intent */
    private void receiveAnswers() {
        userAnswers = getIntent().getIntArrayExtra("userAnswers");

        // Fallback: if nothing passed (e.g. launched directly for testing), mock answers
        if (userAnswers == null) {
            userAnswers = new int[]{2, 2, 0, 2, 2};  // all correct for demo
        }
    }

    /** Fills the score card at the top */
    private void setupScoreCard() {
        int total   = questionList.size();
        int correct = countCorrect();

        tvTotalQuestions.setText("Total: " + total);
        tvCorrectAns.setText("Correct: " + correct);
        tvScore.setText("Score: " + correct + " / " + total);
        tvFeedback.setText(getFeedback(correct, total));
    }

    private int countCorrect() {
        int count = 0;
        for (int i = 0; i < questionList.size(); i++) {
            QuestionDTO q = questionList.get(i);
            int correctIndex = getCorrectIndex(q);
            if (userAnswers[i] == correctIndex) count++;
        }
        return count;
    }

    private int getCorrectIndex(QuestionDTO q) {
        String correct = q.getAnswer();
        if (correct.equals(q.getOptionA())) return 0;
        if (correct.equals(q.getOptionB())) return 1;
        if (correct.equals(q.getOptionC())) return 2;
        return 3;
    }

    private String getFeedback(int correct, int total) {
        float pct = (correct / (float) total) * 100;
        if (pct == 100) return "Perfect! Excellent work!";
        if (pct >= 80)  return "Great job! Keep it up!";
        if (pct >= 60)  return "Good effort! Review the wrong ones.";
        if (pct >= 40)  return "Keep practicing, you'll get there!";
        return "Don't give up! Review and try again.";
    }

    private void setupViewPager() {
        TestReviewVPAdapter adapter = new TestReviewVPAdapter(this, questionList, userAnswers);
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

    private void updateNavButtons(int position) {
        boolean isFirst = (position == 0);
        boolean isLast  = (position == questionList.size() - 1);

        btnPrevious.setEnabled(!isFirst);
        btnNext.setEnabled(!isLast);
        btnNext.setAlpha(isLast ? 0.4f : 1f);
    }
}