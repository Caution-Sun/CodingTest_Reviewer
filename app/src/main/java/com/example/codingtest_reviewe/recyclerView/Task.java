package com.example.codingtest_reviewe.recyclerView;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    int id;
    String task;
    String time;

    public Task(int id, String task, String time){
        this.id = id;
        this.task = task;
        this.time = time;
    }
    protected Task(Parcel in) {
        id = in.readInt();
        task = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
