package org.techtown.codingtest_reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {
    int id;
    String date;
    String title;
    String question;
    String link;

    public Test(int id, String date, String title, String question, String link) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.question = question;
        this.link = link;
    }

    public Test(Parcel src){
        id = src.readInt();
        title = src.readString();
        question = src.readString();
        link = src.readString();
    }

    public int getid() {
        return id;
    }

    public void setid(int num) {
        this.id = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Test createFromParcel(Parcel in){
            return new Test(in);
        }
        public Test[] newArray(int size){
            return new Test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(question);
        dest.writeString(link);
    }
}
