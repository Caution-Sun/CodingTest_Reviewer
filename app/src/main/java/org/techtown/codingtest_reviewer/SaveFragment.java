package org.techtown.codingtest_reviewer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class SaveFragment extends Fragment {

    TextView textDate;
    ImageView imageView;
    Button buttonSetImage;
    Button buttonSave;
    EditText editTextTitle;
    EditText editTextLink;

    String solveLink;
    String imageAddress;
    String imgName;
    String title;
    String date;

    Bitmap imgBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_save, container, false);

        textDate = rootView.findViewById(R.id.textDate);
        imageView = rootView.findViewById(R.id.imageView);
        buttonSetImage = rootView.findViewById(R.id.buttonSetImage);
        buttonSave = rootView.findViewById(R.id.buttonSave);
        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextLink = rootView.findViewById(R.id.editTextLink);

        buttonSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solveLink = editTextLink.getText().toString();
                title = editTextTitle.getText().toString().replaceAll("\\s", "_");

                if(title == null || title.equals("")){
                    Toast.makeText(getActivity(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(solveLink == null || solveLink.equals("")){
                    Toast.makeText(getActivity(), "풀이 링크를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(imgBitmap == null || imgBitmap.equals("")){
                    Toast.makeText(getActivity(), "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    imgName = title + ".png";

                    addTest();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(System.currentTimeMillis());
        textDate.setText(date);
    }

    public void openGallery(){
        Intent intent = new Intent();
        // MIME 타입이 image로 시작하는 데이터 가져오기
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            Uri fileUri = data.getData();

            ContentResolver resolver = getActivity().getContentResolver();

            try{
                InputStream instream = resolver.openInputStream(fileUri);
                imgBitmap = BitmapFactory.decodeStream(instream);

                imageView.setImageBitmap(imgBitmap);

                instream.close();
            }catch (Exception e){
                Toast.makeText(getContext(), "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean saveBitmapToJpeg(Bitmap bitmap) {
        File tempFile = new File(getActivity().getCacheDir(), imgName);
        try {
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.close();

            imageAddress = getActivity().getCacheDir() + "/" + imgName;

            return true;
        } catch (Exception e) {
            Toast.makeText(getContext(), "이미지를 저장하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void addTest() {
        TestDatabase database = TestDatabase.getInstance(getContext());

        String sql = "select * from " + TestDatabase.TABLE_TEST + " where TITLE = '" + title + "'";;

        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            if(cursor.getCount() != 0){
                Toast.makeText(getActivity(), "같은 제목의 문제가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
            }else{
                sql = "insert into " + TestDatabase.TABLE_TEST +
                        "(DATE, TITLE, IMAGE, LINK) values(" +
                        "'" + date + "', " +
                        "'" + title + "', " +
                        "'" + imageAddress + "', " +
                        "'" + solveLink + "')";

                boolean imageSaved = saveBitmapToJpeg(imgBitmap);

                if(imageSaved){
                    database.execSQL(sql);
                    Toast.makeText(getActivity(), "문제를 저장했습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "문제를 저장하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}