package com.example.codingtest_reviewe.recyclerView;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    int id;
    String task;
    String date;
    String address;

    public Task(int id, String task, String date, String address){
        this.id = id;
        this.task = task;
        this.date = date;
        this.address = address;
    }
    protected Task(Parcel in) {
        id = in.readInt();
        task = in.readString();
        date = in.readString();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        dest.writeString(date);
        dest.writeString(address);
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

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.date = time;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}
