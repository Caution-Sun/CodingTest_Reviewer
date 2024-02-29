package org.techtown.codingtest_reviewer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class ReviewFragment extends Fragment {

    TextView textDate;
    TextView textTitle;
    ImageView imageView;
    Button buttonLink;

    String date;
    String title;
    String imageAddress;
    Bitmap bitmap;
    String answerLink;

    boolean isList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_review, container, false);

        textDate = rootView.findViewById(R.id.textDate);
        textTitle = rootView.findViewById(R.id.textTitle);
        imageView = rootView.findViewById(R.id.imageView);
        buttonLink = rootView.findViewById(R.id.buttonToAnswer);

        buttonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(answerLink));

                startActivity(intent);
            }
        });

        return rootView;
    }

    public void setTest(String dateInput){

        date = dateInput;

        TestDatabase database = TestDatabase.getInstance(getContext());

        String sql = "select * from " + TestDatabase.TABLE_TEST + " where DATE = '" + date + "'";

        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            if(cursor.getCount() == 0){
                Toast.makeText(getContext(), "해당 날짜에 저장된 문제가 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                cursor.moveToNext();

                textDate.setText(date);

                title = cursor.getString(2);
                textTitle.setText(title);

                imageAddress = cursor.getString(3);
                try{
                    bitmap = BitmapFactory.decodeFile(imageAddress);
                    imageView.setImageBitmap(bitmap);
                }catch (Exception e){
                    Toast.makeText(getContext(), "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }

                answerLink = cursor.getString(4);
            }
        }
    }

    public void setTest(int id){

        TestDatabase database = TestDatabase.getInstance(getContext());

        String sql = "select * from " + TestDatabase.TABLE_TEST + " where _id = '" + id + "'";

        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            if(cursor.getCount() == 0){
                Toast.makeText(getContext(), "해당 날짜에 저장된 문제가 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                cursor.moveToNext();

                date = cursor.getString(1);
                textDate.setText(date);

                title = cursor.getString(2);
                textTitle.setText(title);

                imageAddress = cursor.getString(3);
                try{
                    bitmap = BitmapFactory.decodeFile(imageAddress);
                    imageView.setImageBitmap(bitmap);
                }catch (Exception e){
                    Toast.makeText(getContext(), "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }

                answerLink = cursor.getString(4);
            }
        }
    }
}