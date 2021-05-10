package com.example.pluralsight_notekeeper_android.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public final class Course implements Parcelable{
    private final String mCourseId;
    private final String mTitle;
    private final List<Module> mModules;

    public Course(String courseId, String title, List<Module> modules) {
        mCourseId = courseId;
        mTitle = title;
        mModules = modules;
    }

    private Course(Parcel source) {
        mCourseId = source.readString();
        mTitle = source.readString();
        mModules = new ArrayList<>();
        source.readTypedList(mModules, Module.CREATOR);
    }

    public String getCourseId() {
        return mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<Module> getModules() {
        return mModules;
    }

    public boolean[] getModulesCompletionStatus() {
        boolean[] status = new boolean[mModules.size()];

        for(int i=0; i < mModules.size(); i++)
            status[i] = mModules.get(i).isComplete();

        return status;
    }

    public void setModulesCompletionStatus(boolean[] status) {
        for(int i=0; i < mModules.size(); i++)
            mModules.get(i).setComplete(status[i]);
    }

    public Module getModule(String moduleId) {
        for(Module module : mModules) {
            if(moduleId.equals(module.getModuleId()))
                return module;
        }
        return null;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course that = (Course) o;

        return mCourseId.equals(that.mCourseId);

    }

    @Override
    public int hashCode() {
        return mCourseId.hashCode();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCourseId);
        dest.writeString(mTitle);
        dest.writeTypedList(mModules);
    }

    public static final Parcelable.Creator<Course> CREATOR =
            new Parcelable.Creator<Course>() {

                @Override
                public Course createFromParcel(Parcel source) {
                    return new Course(source);
                }

                @Override
                public Course[] newArray(int size) {
                    return new Course[size];
                }
            };

}
