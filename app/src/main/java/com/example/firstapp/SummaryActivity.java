package com.example.firstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SummaryActivity extends AppCompatActivity {
    TextView FinalGPA, AwardClass, Motivation, Greeting;
    ConstraintLayout summaryLayout;
    Button btnBack;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);

        FinalGPA = findViewById(R.id.FinalGPA);
        AwardClass = findViewById(R.id.AwardClass);
        Motivation = findViewById(R.id.Motivation);
        Greeting = findViewById(R.id.Greeting);
        summaryLayout = findViewById(R.id.summaryLayout);
        btnBack = findViewById(R.id.btnBack);

        double gpa = getIntent().getDoubleExtra("GPA", 0);
        String userName = getIntent().getStringExtra("USERNAME");

        Greeting.setText("Welldone, " + userName + "!");
        FinalGPA.setText("GPA: " + String.format("%.2f", gpa));
        AwardClass.setText("Award of Class: " + getAwardClass(gpa));
        Motivation.setText(getMotivationalMessage(gpa));

        //Change background color randomly from array
        int[] colors = {
                Color.CYAN,
                Color.GRAY,
                Color.DKGRAY,
                Color.BLUE,
                Color.LTGRAY
        };

        Random random = new Random();

        summaryLayout.setBackgroundColor(colors[random.nextInt(colors.length)]);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getMotivationalMessage(double gpa) {
        if (gpa >= 3.5)
            return "Excellent work! Keep it up!";
        else if(gpa < 2.5)
            return "You can improve next semester!";
        else
            return "Good effort! Stay consistent!";
    }

    private String getAwardClass(double gpa) {
        if(gpa >= 3.70)
            return "First Class";
        else if(gpa >= 3.30)
            return "Second Class(Upper Division)";
        else if(gpa >= 3.00)
            return "Second Class(Lower Division)";
        else
            return "No Class";
    }
}
