package com.example.codingtest_reviewe.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingtest_reviewe.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements onTaskClickListener {

    ArrayList<Task> items = new ArrayList<>();
    onTaskClickListener listener = null;

    public void setOnItemClickListener(onTaskClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Task item){
        items.add(item);
    }

    public Task getItem(int position){
        return items.get(position);
    }

    public void deleteAllItem() {
        items.clear();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTask;
        TextView textTime;

        public ViewHolder(@NonNull View itemView, final onTaskClickListener listener) {
            super(itemView);

            textTask = itemView.findViewById(R.id.textTask);
            textTime = itemView.findViewById(R.id.textTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Task item){
            textTask.setText(item.getTask());

            textTime.setText(item.getDate());
        }
    }
}
