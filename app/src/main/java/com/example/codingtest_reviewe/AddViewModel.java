package com.example.codingtest_reviewe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class AddViewModel extends ViewModel {

    private MutableLiveData<String> title = new MutableLiveData<>();
    public LiveData<String> getTitle() {
        return title;
    }
    private MutableLiveData<String> yearString = new MutableLiveData<>();
    public LiveData<String> getYearString() {
        return yearString;
    }
    private MutableLiveData<String> dateString = new MutableLiveData<>();
    public LiveData<String> getDateString() {
        return dateString;
    }

    private MutableLiveData<String> address = new MutableLiveData<>();
    public LiveData<String> getAddress() {
        return address;
    }

    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    private MutableLiveData<Event<String>> addTaskEvent = new MutableLiveData<>();
    public LiveData<Event<String>> getAddTaskEvent() {
        return addTaskEvent;
    }

    public void setTitle(CharSequence s, int start, int before, int count) {
        title.setValue(s.toString());
    }

    public void setAddress(CharSequence s, int start, int before, int count){
        address.setValue(s.toString());
    }

    public void showDatePicker(Context context){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                yearString.setValue(Integer.toString(year));
                dateString.setValue(month + "/" + day);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    public void onButtonClicked(){
        if(title.getValue() == null || title.getValue().isEmpty()) {
            setToast("제목을 입력해주세요");
        } else if(yearString.getValue() == null || yearString.getValue().isEmpty() || dateString.getValue() == null || dateString.getValue().isEmpty()){
            setToast("날짜를 입력해주세요");
        } else if(address.getValue() == null || address.getValue().isEmpty()){
            setToast("주소를 입력해주세요");
        } else {
            addTaskEvent.setValue(new Event<String>("add event"));
        }
    }

    public void setToast(String s) {
        toastMessage.setValue(s);
    }
}
