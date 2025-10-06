package com.example.firstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GPACalculatorActivity extends AppCompatActivity {
    EditText CourseCode, Grade, Credit;
    Button btnAddSubject, btnCalculateGPA, btnRemoveSubject;
    TextView SubjectsList, Welcome;
    int totalSubjects;
    String curYear, semester, userName;

    ArrayList<String> courseCodes = new ArrayList<>();
    ArrayList<String> grades = new ArrayList<>();
    ArrayList<Integer> credits = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpacalculator);

        totalSubjects = getIntent().getIntExtra("TOTAL_SUBJECTS", 1);
        curYear = getIntent().getStringExtra("YEAR");
        semester = getIntent().getStringExtra("SEMESTER");
        userName = getIntent().getStringExtra("USERNAME");

        CourseCode = findViewById(R.id.CourseCode);
        Grade = findViewById(R.id.Grade);
        Credit = findViewById(R.id.Credit);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        btnCalculateGPA = findViewById(R.id.btnCalculateGPA);
        btnRemoveSubject = findViewById(R.id.btnRemoveSubject);
        SubjectsList = findViewById(R.id.SubjectsList);
        Welcome = findViewById(R.id.Welcome);

        Welcome.setText("Welcome, " + userName + "!");

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseCodes.size() >= totalSubjects) {
                    Toast.makeText(GPACalculatorActivity.this, "You already added all subjects!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = CourseCode.getText().toString().trim();
                String grade = Grade.getText().toString().trim().toUpperCase();
                String creditStr = Credit.getText().toString().trim();

                if (code.isEmpty() || grade.isEmpty() || creditStr.isEmpty()) {
                    Toast.makeText(GPACalculatorActivity.this, "Enter course code , credit & grade", Toast.LENGTH_SHORT).show();
                    return;
                }

                int credit = Integer.parseInt(creditStr);

                courseCodes.add(code);
                grades.add(grade);
                credits.add(credit);

                SubjectsList.append(code + " ("+credit+" credits) - " + grade + "\n");

                CourseCode.setText("");
                Grade.setText("");
                Credit.setText("");
            }
        });

        btnRemoveSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastIndex = courseCodes.size() - 1;
                String removedCode = courseCodes.remove(lastIndex);
                grades.remove(lastIndex);
                credits.remove(lastIndex);

                SubjectsList.setText("Subjects\n");
                for(int i=0;i<courseCodes.size();i++){
                    SubjectsList.append(courseCodes.get(i) + " ("+credits.get(i)+" credits) - " + grades.get(i) + "\n");
                }
                Toast.makeText(GPACalculatorActivity.this, "Removed subject: " + removedCode, Toast.LENGTH_SHORT).show();

            }
        });

        btnCalculateGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPoints = 0;
                int totalCredits = 0;

                for (int i = 0; i < courseCodes.size(); i++) {
                    double gradePoint = getGradePoint(grades.get(i));

                    totalPoints += gradePoint * credits.get(i);
                    totalCredits += credits.get(i);
                }

                double gpa = (totalCredits == 0) ? 0 : totalPoints / totalCredits;

                Intent intent = new Intent(GPACalculatorActivity.this, SummaryActivity.class);
                intent.putExtra("GPA", gpa);
                intent.putExtra("USERNAME",userName);
                startActivity(intent);
            }
        });
    }

    private double getGradePoint(String grade) {
        switch (grade){
            case "A+" : return 4.0;
            case "A" : return 4.0;
            case "A-" : return 3.7;
            case "B+" : return 3.3;
            case "B" : return 3.0;
            case "B-" : return 2.7;
            case "C+" : return 2.3;
            case "C" : return 2.0;
            case "C-" : return 1.7;
            case "D+" : return 1.3;
            case "D" : return 1.0;
            case "E" : return 0.0;
            default : return 0.0;
        }
    }
}