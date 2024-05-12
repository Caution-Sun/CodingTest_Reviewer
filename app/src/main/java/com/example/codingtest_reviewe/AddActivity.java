package com.example.codingtest_reviewe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    EditText editTextTask;
    EditText editTextAdd;
    Button buttonSetTime;
    Button buttonAdd;
    TextView textYear;
    TextView textDate;

    String yearString;
    String dateString;
    String addString;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Views
        editTextTask = findViewById(R.id.editTextTask_add);
        editTextAdd = findViewById(R.id.editTextAdd_add);
        buttonSetTime = findViewById(R.id.buttonSetTime_add);
        buttonAdd = findViewById(R.id.buttonAdd);
        textYear = findViewById(R.id.textYear_add);
        textDate = findViewById(R.id.textDate_add);

        // init DatePicker
        initDatePicker();

        // button set Date
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        // button transmit data to ListActivity and finish
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = editTextTask.getText().toString();
                String date = yearString + " " + dateString;
                addString = editTextAdd.getText().toString();

                if(task == null || task.equals("")){
                    Toast.makeText(getApplicationContext(), "할 일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(yearString == null || yearString.equals("")){
                    Toast.makeText(getApplicationContext(), "날짜를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(addString == null || addString.equals("")){
                    Toast.makeText(getApplicationContext(), "주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("task", task);
                    intent.putExtra("date", date);
                    intent.putExtra("address", addString);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    // init DatePicker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                yearString = Integer.toString(year);
                dateString = month + "/" + day;

                textYear.setText(yearString);
                textDate.setText(dateString);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
}