package org.techtown.codingtest_reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTActivity extends AppCompatActivity {

    ReviewFragment reviewFragment;
    Button buttonDelete;
    Button buttonEdit;
    Button buttonBack;
    Button buttonNext;

    int id;

    int start;
    int end;

    Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_t);

        reviewFragment = new ReviewFragment();
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonBack = findViewById(R.id.buttonBack);
        buttonNext = findViewById(R.id.buttonNext);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, reviewFragment).commit();

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();

            id = bundle.getInt("id");

            start = bundle.getInt("start");
            end = bundle.getInt("end");
        }

        initQuestion();

        if(id == start)
            buttonBack.setEnabled(false);
        if(id == end)
            buttonNext.setEnabled(false);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor.moveToPrevious();

                setTestData();
                reviewFragment.setTest();

                if(id == start){
                    buttonBack.setEnabled(false);
                }else{
                    buttonBack.setEnabled(true);
                }

                if(id == end){
                    buttonNext.setEnabled(false);
                }else{
                    buttonNext.setEnabled(true);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor.moveToNext();

                setTestData();
                reviewFragment.setTest();

                if(id == start){
                    buttonBack.setEnabled(false);
                }else{
                    buttonBack.setEnabled(true);
                }

                if(id == end){
                    buttonNext.setEnabled(false);
                }else{
                    buttonNext.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(id == start)
            buttonBack.setEnabled(false);
        if(id == end)
            buttonNext.setEnabled(false);

        setTestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(cursor != null){
            cursor.close();
            cursor = null;
        }

    }

    private void setTestData(){
        reviewFragment.hasTest = true;

        String date = cursor.getString(1);
        String title = cursor.getString(2);
        String imageAddress = cursor.getString(3);
        String answerLink = cursor.getString(4);

        reviewFragment.date = date;
        reviewFragment.title = title;
        reviewFragment.imageAddress = imageAddress;
        reviewFragment.answerLink = answerLink;
    }

    private void initQuestion() {

        TestDatabase database = null;

        if(cursor != null){
            cursor.close();
            cursor = null;
        }

        database = TestDatabase.getInstance(this);

        String sql = "select _id" + TestDatabase.TABLE_TEST + " order by _id ASC";

        cursor = database.rawQuery(sql);

        int recordCount = cursor.getCount();

        for(int i = 0; i < recordCount; i++){
            cursor.moveToNext();

            int _id = cursor.getInt(0);

            if(_id == id){
                setTestData();
                break;
            }
        }
    }
}