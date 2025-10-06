package com.example.firstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarSubjects;
    TextView SubjectCount;
    Button btnNext;
    EditText Year, Name;
    RadioGroup radioGroup;
    int totalSubjects = 1; // default

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        seekBarSubjects = findViewById(R.id.seekBarSubjects);
        SubjectCount = findViewById(R.id.SubjectCount);
        btnNext = findViewById(R.id.btnNext);
        Year = findViewById(R.id.Year);
        Name = findViewById(R.id.Name);
        radioGroup = findViewById(R.id.radioGroupSem);

        //SeekBar to select number of subjects
        seekBarSubjects.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) progress = 1; // minimum 1 subject
                totalSubjects = progress;
                SubjectCount.setText("Select Number of Subjects: " + totalSubjects);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curYear = Year.getText().toString().trim();
                String userName = Name.getText().toString().trim();

                if(userName.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter your name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (curYear.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter the year", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Get selected semester
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String semester = (selectedId == R.id.radioSem1) ? "Semester 1" : "Semester 2";

                //Send data to GPA Calculator
                Intent intent = new Intent(MainActivity.this, GPACalculatorActivity.class);

                intent.putExtra("TOTAL_SUBJECTS",totalSubjects);
                intent.putExtra("USERNAME",userName);
                startActivity(intent);

            }
        });

    }
}