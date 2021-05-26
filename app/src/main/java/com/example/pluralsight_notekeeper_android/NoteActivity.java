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
    public static final int DEFAULT_POSITION = -1;

    Spinner spinner_courses_view;
    EditText textView_note_title, textView_note_text;
    private Note messageNoteInformation;
    private int notePosition;

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
            displayExtraDataByNotePosition(spinner_courses_view, textView_note_title,
                    textView_note_text,notePosition);
    }

    private void displayExtraDataByNoteInformation(Spinner spinner_courses,
                                  EditText text_note_title,
                                  EditText text_note_text) {

        List<Course> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(messageNoteInformation.getCourse());

        spinner_courses.setSelection(courseIndex);

        text_note_title.setText(messageNoteInformation.getTitle());
        text_note_text.setText(messageNoteInformation.getText());


    }

    private void displayExtraDataByNotePosition(Spinner spinner_courses,
                                                   EditText text_note_title,
                                                   EditText text_note_text, int notePosition) {

        List<Course> courses = DataManager.getInstance().getCourses();
        Note note = DataManager.getInstance().getNotes().get(notePosition);
        int courseIndex = courses.indexOf(note.getCourse());

        spinner_courses.setSelection(courseIndex);

        text_note_title.setText(note.getTitle());
        text_note_text.setText(note.getText());


    }


    private Boolean hasExtraData() {
        Intent messageObject = getIntent();
        notePosition = messageObject
                .getIntExtra(NOTE_POSITION, DEFAULT_POSITION);

        return (notePosition == DEFAULT_POSITION);
    }
}