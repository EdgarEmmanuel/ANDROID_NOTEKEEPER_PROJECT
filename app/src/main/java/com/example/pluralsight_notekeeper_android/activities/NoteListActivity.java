package com.example.pluralsight_notekeeper_android.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pluralsight_notekeeper_android.R;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    ListView list_note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_note_list);

        list_note = findViewById(R.id.list_note);

        initializeListContent();
    }

    private void initializeListContent() {

    }
}
