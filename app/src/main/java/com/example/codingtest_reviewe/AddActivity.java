package com.example.codingtest_reviewe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.codingtest_reviewe.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding addBinding;
    private AddViewModel addViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Data binding
        addBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(addBinding.getRoot());

        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        addBinding.setViewModel(addViewModel);
        addBinding.setLifecycleOwner(this);

        addViewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(),s , Toast.LENGTH_SHORT).show();
            }
        });

        addViewModel.getAddTaskEvent().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> event) {
                if(event != null && event.getContentIfNotHandled() != null){

                    Intent intent = new Intent();
                    intent.putExtra("task", addViewModel.getTitle().getValue());
                    intent.putExtra("date", addViewModel.getYearString().getValue() + " " + addViewModel.getDateString().getValue());
                    intent.putExtra("address", addViewModel.getAddress().getValue());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}