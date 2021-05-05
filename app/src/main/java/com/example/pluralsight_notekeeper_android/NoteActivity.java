package com.example.pluralsight_notekeeper_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class NoteActivity extends AppCompatActivity {

    Spinner spinner_courses;
    EditText text_note_title,text_note_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        spinner_courses = findViewById(R.id.spinner_courses);
        text_note_title = findViewById(R.id.text_note_title);
        text_note_text = findViewById(R.id.text_note_text);

    }
}