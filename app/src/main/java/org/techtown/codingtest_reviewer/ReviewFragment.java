package org.techtown.codingtest_reviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

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

    boolean hasTest = false;

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
                if(answerLink == null || answerLink.equals("")){
                    Toast.makeText(getContext(), "저장된 문제가 없습니다", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(answerLink));
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        if(hasTest)
            setTest();
        else
            initView();
    }

    public void setTest(){

        textDate.setText(date);

        textTitle.setText(title);
        try{
            bitmap = BitmapFactory.decodeFile(imageAddress);
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            Toast.makeText(getContext(), "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    public void initView(){
        textDate.setText(date);

        textTitle.setText(null);
        imageView.setImageBitmap(null);
        answerLink = null;
    }

}