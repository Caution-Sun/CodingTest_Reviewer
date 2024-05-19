package com.example.codingtest_reviewe;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.codingtest_reviewe.recyclerView.Task;
import com.example.codingtest_reviewe.recyclerView.TaskAdapter;
import com.example.codingtest_reviewe.recyclerView.TaskItemTouchHelperCallback;
import com.example.codingtest_reviewe.recyclerView.onItemSwipeListener;
import com.example.codingtest_reviewe.recyclerView.onTaskClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements onItemSwipeListener {

    private static final String TAG = "MainActivity";
    public static TaskDatabase mDatabase = null;

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    TaskAdapter adapter;

    private ActivityResultLauncher<Intent> makeResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDatabase();

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        // AddActivity ActivityResultCallback
        makeResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode() == RESULT_OK){
                    Intent intent = o.getData();
                    String task = intent.getStringExtra("task");
                    String date = intent.getStringExtra("date");
                    String address = intent.getStringExtra("address");

                    // Insert Database
                    TaskDatabase database = TaskDatabase.getInstance(getApplicationContext());
                    String SQL = "INSERT INTO " + database.TABLE_USERS + "(TASK, DATE, ADDRESS) VALUES(" + "'" + task + "', '" + date + "', '" + address + "'" + ")";
                    if(database != null){
                        database.execSQL(SQL);
                        loadTaskListData();
                    }
                }
            }
        });

        // floatingButton goto AddActivity
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                makeResultLauncher.launch(intent);
            }
        });

        // set recyclerView
        adapter = new TaskAdapter();
        adapter.setOnItemClickListener(new onTaskClickListener() {
            @Override
            public void onItemClick(TaskAdapter.ViewHolder holder, View view, int position) {
                Task task = adapter.getItem(position);

                TaskDatabase database = TaskDatabase.getInstance(getApplicationContext());
                String SQL = "SELECT ADDRESS FROM " + database.TABLE_USERS + " WHERE _id = " + task.getId();
                if(database != null){
                    Cursor cursor = database.rawQuery(SQL);

                    cursor.moveToNext();
                    String address = cursor.getString(0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                    startActivity(intent);

                    cursor.close();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskItemTouchHelperCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        loadTaskListData();
    }

    // open Database
    private void openDatabase(){
        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = TaskDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if(isOpen)
            Log.d(TAG, "Task database is open.");
        else
            Log.d(TAG, "Task database is not open.");
    }

    // set recyclerView method
    private void loadTaskListData() {
        adapter.deleteAllItem();
        adapter.notifyDataSetChanged();

        TaskDatabase database = TaskDatabase.getInstance(this);
        String SQL = "SELECT _id, TASK, DATE FROM " + database.TABLE_USERS + " ORDER BY DATE";
        if(database != null){
            Cursor cursor = database.rawQuery(SQL);

            int recordCount = cursor.getCount();

            for(int i = 0; i < recordCount; i++){
                cursor.moveToNext();

                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String date = cursor.getString(2);

                adapter.addItem(new Task(id, task, date));
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    // implements onItemSwipeListener editItem
    @Override
    public void editItem(int position) {
        // Item 수정
    }

    // implements onItemSwipeListener removeItem
    @Override
    public void removeItem(int position) {
        Task task = adapter.getItem(position);

        TaskDatabase database = TaskDatabase.getInstance(this);
        String SQL = "DELETE FROM " + database.TABLE_USERS + " WHERE _id = " + task.getId();
        if(database != null){
            database.execSQL(SQL);
            loadTaskListData();
        }
    }

    // onDestroy Close Database
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }
    }

}