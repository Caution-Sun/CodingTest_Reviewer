package org.techtown.codingtest_reviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static TestDatabase mDatabase = null;

    ReviewFragment reviewFragment;
    SaveFragment saveFragment;
    ListFragment listFragment;
    SettingsFragment settingsFragment;

    String dateInterval;
    SharedPreferences sharedPreferences;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDatabase();

        reviewFragment = new ReviewFragment();
        saveFragment = new SaveFragment();
        listFragment = new ListFragment();
        settingsFragment = new SettingsFragment();

        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(MainActivity.this, "허용된 권한 개수 : " + data.size(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(MainActivity.this, "거부된 권한 개수 : " + data.size(), Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        dateInterval = sharedPreferences.getString("date_interval", "0");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * Integer.parseInt(dateInterval)));

        setTestData();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, reviewFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:

                        dateInterval = sharedPreferences.getString("date_interval", "0");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        date = simpleDateFormat.format(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * Integer.parseInt(dateInterval)));

                        setTestData();

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, reviewFragment).commit();
                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, saveFragment).commit();
                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();
                        return true;
                    case R.id.tab4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }
    }

    public void openDatabase() {
        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = TestDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if(isOpen){
            Log.d(TAG, "Test database is open.");
        }else{
            Log.d(TAG, "Test database is not open.");
        }
    }

    public void setTestData() {

        String sql = "select * from " + TestDatabase.TABLE_TEST + " where DATE = '" + date + "'";

        reviewFragment.date = date;

        if(mDatabase != null){
            Cursor cursor = mDatabase.rawQuery(sql);

            if(cursor.getCount() == 0){
                reviewFragment.hasTest = false;
            }else{
                cursor.moveToNext();

                String title = cursor.getString(2);
                String imageAddress = cursor.getString(3);
                String answerLink = cursor.getString(4);

                reviewFragment.date = date;
                reviewFragment.title = title;
                reviewFragment.imageAddress = imageAddress;
                reviewFragment.answerLink = answerLink;

                reviewFragment.hasTest = true;
            }
        }
    }
}
