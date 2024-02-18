package org.techtown.codingtest_reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.security.Permissions;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // 데이터베이스 인스턴스
    public static TestDatabase mDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDatabase();

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
    }

    // 종료 시 데이터베이스 닫기.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }
    }

    // 데이터베이스를 여는 함수
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
}
