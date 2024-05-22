package com.example.codingtest_reviewe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    EditText editTextTask;
    EditText editTextAdd;
    Button buttonSetTime;
    Button buttonEdit;
    TextView textYear;
    TextView textDate;

    String taskString;
    String yearString;
    String dateString;
    String addString;

    int id;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Views
        editTextTask = findViewById(R.id.editTextTask_edit);
        editTextAdd = findViewById(R.id.editTextAdd_edit);
        buttonSetTime = findViewById(R.id.buttonSetTime_edit);
        buttonEdit = findViewById(R.id.buttonEdit);
        textYear = findViewById(R.id.textYear_edit);
        textDate = findViewById(R.id.textDate_edit);

        // get data from MainActivity
        Intent intent = getIntent();
        if(intent != null){
            String task = intent.getStringExtra("task");
            String date = intent.getStringExtra("date");
            String address = intent.getStringExtra("address");
            id = intent.getIntExtra("id", -1);

            taskString = task;
            editTextTask.setText(taskString);

            String[] dates = date.split(" ");
            yearString = dates[0];
            textYear.setText(yearString);
            dateString = dates[1];
            textDate.setText(dateString);

            addString = address;
            editTextAdd.setText(addString);
        }

        // init DatePicker
        initDatePicker();

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskString = editTextTask.getText().toString();
                dateString = yearString + " " + dateString;
                addString = editTextAdd.getText().toString();

                if(taskString == null || taskString.equals("")){
                    Toast.makeText(getApplicationContext(), "할 일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(yearString == null || yearString.equals("")){
                    Toast.makeText(getApplicationContext(), "날짜를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(addString == null || addString.equals("")){
                    Toast.makeText(getApplicationContext(), "주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("task", taskString);
                    intent.putExtra("date", dateString);
                    intent.putExtra("address", addString);
                    intent.putExtra("id", id);
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
        int year;
        int month;
        int day;
        int style = AlertDialog.THEME_HOLO_LIGHT;

        if(yearString == null || yearString.equals("")){
            year = calendar.get(Calendar.YEAR);
        }else{
            year = Integer.parseInt(yearString);
        }

        if(dateString == null || dateString.equals("")){
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }else{
            String[] dates = dateString.split("/");
            month = Integer.parseInt(dates[0]) - 1;
            day = Integer.parseInt(dates[1]);
        }

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
}