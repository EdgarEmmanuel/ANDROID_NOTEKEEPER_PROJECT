package com.example.pluralsight_notekeeper_android.database.models;

import android.os.Parcel;
import android.os.Parcelable;

public final class Note {
    private Course mCourse;
    private String mTitle;
    private String mText;
    private int mId;

    public Note(Course course, String title, String text) {
        mCourse = course;
        mTitle = title;
        mText = text;
    }

    private Note(Parcel source) {
        mCourse = source.readParcelable(Course.class.getClassLoader());
        mTitle = source.readString();
        mText = source.readString();
    }

    public Note(int id, Course noteCourse, String noteTitle, String noteText) {
        mId = id;
        mCourse = noteCourse;
        mTitle = noteTitle;
        mText = noteText;

    }

    public Course getCourse() {
        return mCourse;
    }

    public void setCourse(Course course) {
        mCourse = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey() {
        return mCourse.getCourseId() + "|" + mTitle + "|" + mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note that = (Note) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
