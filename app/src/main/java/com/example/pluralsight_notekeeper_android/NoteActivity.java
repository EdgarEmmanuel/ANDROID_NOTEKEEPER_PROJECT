package com.example.pluralsight_notekeeper_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pluralsight_notekeeper_android.database.dao.DataManager;
import com.example.pluralsight_notekeeper_android.database.models.Course;
import com.example.pluralsight_notekeeper_android.database.models.Note;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION="com.example.pluralsight_notekeeper_android.activities.NOTE_POSITION";

    Spinner spinner_courses_view;
    EditText textView_note_title, textView_note_text;
    private Note messageNoteInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        spinner_courses_view = findViewById(R.id.spinner_courses);
        textView_note_title = findViewById(R.id.text_note_title);
        textView_note_text = findViewById(R.id.text_note_text);

        List<Course> coursesList = DataManager.getInstance().getCourses();

        ArrayAdapter<Course> coursesAdapter =
           new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,coursesList);

        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_courses_view.setAdapter(coursesAdapter);

        if(!hasExtraData())
            displayExtraData(spinner_courses_view, textView_note_title, textView_note_text);
    }

    private void displayExtraData(Spinner spinner_courses,
                                  EditText text_note_title, EditText text_note_text) {

        List<Course> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(messageNoteInformation.getCourse());

        spinner_courses.setSelection(courseIndex);

        text_note_title.setText(messageNoteInformation.getTitle());
        text_note_text.setText(messageNoteInformation.getText());


    }

    /**
     * this function reads extra data coming from other Activtiy
     */
    private Boolean hasExtraData() {
        Intent messageObject = getIntent();
        messageNoteInformation = messageObject
                .getParcelableExtra(NOTE_POSITION);

        return (messageNoteInformation == null);
    }
}