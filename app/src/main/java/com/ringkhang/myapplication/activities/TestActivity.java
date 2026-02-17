package com.ringkhang.myapplication.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ringkhang.myapplication.R;
import com.ringkhang.myapplication.adapters.QuestionsVpAdapter;
import com.ringkhang.myapplication.models.Question;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ArrayList<Question> questionList = new ArrayList<>();
    private ViewPager2 viewPager2;

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
        setQuestions();
        viewPager2 = findViewById(R.id.question_vp);
        QuestionsVpAdapter adapter = new QuestionsVpAdapter(questionList,this);
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        
    }

    private void setQuestions() {

        questionList.add(new Question(
                "What happens if a private method in a superclass has a method with the exact same signature in its subclass?",
                "A compile-time error occurs because private methods cannot be overridden.",
                "The subclass method will be called via polymorphism if accessed through a superclass reference.",
                "The subclass method is a new, unrelated method specific to the subclass, not an override.",
                "A runtime error (NoSuchMethodError) will be thrown when attempting to invoke it polymorphically.",
                "The subclass method is a new, unrelated method specific to the subclass, not an override.",
                "Private methods are not inherited by subclasses. Therefore, a method in a subclass with the same signature as a private method in its superclass is considered a completely new and independent method for the subclass, not an override. Overriding applies only to inherited methods."
        ));

        questionList.add(new Question(
                "Which access modifier allows members to be accessed from anywhere within the same package and also by subclasses, even if those subclasses are in a different package?",
                "private",
                "No modifier (default/package-private)",
                "protected",
                "public",
                "protected",
                "The protected access modifier grants access to members within the same package and to all subclasses, regardless of their package location. Public allows access from everywhere. Default allows access only within the same package. Private restricts access to only within the declaring class."
        ));

        questionList.add(new Question(
                "Consider the following Java code snippet:\ntry {\n    System.out.print(\"A\");\n    throw new NullPointerException();\n} catch (ArithmeticException e) {\n    System.out.print(\"B\");\n} catch (RuntimeException e) {\n    System.out.print(\"C\");\n} finally {\n    System.out.print(\"D\");\n}\nSystem.out.print(\"E\");\nWhat will be the output?",
                "ACDE",
                "ABDE",
                "ADE",
                "ACD",
                "ACDE",
                "The code first prints 'A'. Then, a NullPointerException is thrown. This exception is a subclass of RuntimeException, so it will be caught by the catch (RuntimeException e) block, printing 'C'. The finally block always executes, printing 'D'. Finally, the statement after the try-catch-finally block executes, printing 'E'. Thus, the sequence is ACDE."
        ));

        questionList.add(new Question(
                "Which statement best describes a key difference between Java interfaces and abstract classes regarding class implementation and inheritance?",
                "A class can extend multiple abstract classes but can only implement one interface.",
                "An interface can declare instance variables, while an abstract class cannot.",
                "A class can implement multiple interfaces but can only extend one abstract class.",
                "Abstract classes support true multiple inheritance of implementation, unlike interfaces.",
                "A class can implement multiple interfaces but can only extend one abstract class.",
                "Java supports multiple inheritance of type (through interfaces) but not multiple inheritance of implementation. A class can only extend one superclass, whether concrete or abstract."
        ));

        questionList.add(new Question(
                "Given the following Java code:\nString s1 = \"Hello\";\nString s2 = s1.concat(\" World\");\nString s3 = s1;\ns1 = s1.toUpperCase();\nSystem.out.println(s1 + \", \" + s2 + \", \" + s3);\nWhat will be the output?",
                "HELLO, Hello World, HELLO",
                "HELLO, HELLO World, HELLO",
                "HELLO, Hello World, Hello",
                "Hello, Hello World, Hello",
                "HELLO, Hello World, Hello",
                "Strings in Java are immutable. s2 becomes a new String \"Hello World\". s3 still points to the original \"Hello\". s1 is reassigned to a new String \"HELLO\". Therefore output is: HELLO, Hello World, Hello."
        ));

    }


}