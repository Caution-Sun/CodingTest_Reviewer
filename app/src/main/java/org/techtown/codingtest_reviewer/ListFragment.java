package org.techtown.codingtest_reviewer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    TestAdapter adapter;

    int start, end;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TestAdapter();

        adapter.setOnItemClickListener(new OnTestClickListener() {
            @Override
            public void onItemClick(TestAdapter.ViewHolder holder, View view, int position) {
                Test item = adapter.getItem(position);

                Intent intent = new Intent(getContext(), ListTActivity.class);

                intent.putExtra("id", item.getid());

                intent.putExtra("start", start);
                intent.putExtra("end", end);

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        loadQuestionListData();

        return rootView;
    }

    // 리사이클러뷰에 문제 추가 메소드
    public void loadQuestionListData() {

        int recordCount = 0;

        String sql = "select _id, DATE, TITLE, IMAGE, LINK FROM " + TestDatabase.TABLE_TEST + " order by _id ASC";

        TestDatabase database = TestDatabase.getInstance(getContext());
        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            recordCount = cursor.getCount();

            for(int i = 0; i < recordCount; i++){
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                if(i == 0)
                    start = _id;
                if(i == recordCount -1)
                    end = _id;

                String Date = cursor.getString(1);
                String Title = cursor.getString(2);
                String Image = cursor.getString(3);
                String Link = cursor.getString(4);

                adapter.addItem(new Test(_id, Date, Title, Image, Link));
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }

    }
}