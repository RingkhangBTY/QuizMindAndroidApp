package com.ringkhang.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.data.MyDataResponseCallBack;
import com.ringkhang.myapplication.data.TestDatasource;
import com.ringkhang.myapplication.models_DTO.QuestionDetails;
import com.ringkhang.myapplication.models_DTO.UserInForQuizTest;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestFormActivity extends AppCompatActivity implements InitialUISetUp{
    private Button testStartBtn;

    private EditText subjectInput,topicInput,numberOfQuestions;
    private Spinner quizLevel;

    private ArrayList<QuestionDetails> questionDetails;
    private TestDatasource testDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.testFormPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpViews();
        setInitialListeners();

        testDatasource = new TestDatasource(TestFormActivity.this);


    }

    public void getTestDetails() {

        String subject = subjectInput.getText().toString().trim();
        String topic = topicInput.getText().toString().trim();
        String noOfQuiz = numberOfQuestions.getText().toString().trim();
        String level = quizLevel.getSelectedItem().toString();

        if (subject.isEmpty()) {
            subjectInput.setError("Required");
            return;
        }

        if (topic.isEmpty()) {
            topicInput.setError("Required");
            return;
        }

        if (noOfQuiz.isEmpty()) {
            numberOfQuestions.setError("Required");
            return;
        }

        int number;
        try {
            number = Integer.parseInt(noOfQuiz);
        } catch (NumberFormatException e) {
            numberOfQuestions.setError("Invalid number");
            return;
        }

        UserInForQuizTest userTestInput = new UserInForQuizTest();
        userTestInput.setProgrammingLanguage_Subject(subject);
        userTestInput.setShortDes_Topic_Concepts(topic);
        userTestInput.setNoOfQ(number);
        userTestInput.setLevel(level);

        MyDataResponseCallBack<ArrayList<QuestionDetails>> callBack =
                new MyDataResponseCallBack<ArrayList<QuestionDetails>>() {

                    @Override
                    public void onSuccess(ArrayList<QuestionDetails> data) {

                        if (data == null || data.isEmpty()) {
                            Toast.makeText(TestFormActivity.this,
                                    "No questions received",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        questionDetails = data;
                        Logger.getLogger(TestFormActivity.class.getName())
                                .log(Level.INFO,"Get's the quiz details...");

                        Intent intent = new Intent(TestFormActivity.this, TestActivity.class);
                        intent.putExtra("quizDetails", questionDetails);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        Toast.makeText(TestFormActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(TestFormActivity.this,
                                "Network error",
                                Toast.LENGTH_SHORT).show();
                    }
                };

        testDatasource.getTestQuestions(callBack,userTestInput);
    }

    @Override
    public void setUpViews() {
        testStartBtn = findViewById(R.id.startTestBtn);

        subjectInput = findViewById(R.id.subjectInput);
        topicInput = findViewById(R.id.subjectInput);
        numberOfQuestions = findViewById(R.id.numberOfQuestions);

        quizLevel = findViewById(R.id.quizLevel);
    }

    @Override
    public void setInitialListeners() {
        testStartBtn.setOnClickListener(v->{
            getTestDetails();
            Intent intent = new Intent(TestFormActivity.this,TestActivity.class);
            intent.putExtra("quizDetails",questionDetails);
            startActivity(intent);
        });
    }
}