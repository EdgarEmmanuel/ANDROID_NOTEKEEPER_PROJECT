package com.example.pluralsight_notekeeper_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pluralsight_notekeeper_android.NoteActivity;
import com.example.pluralsight_notekeeper_android.R;
import com.example.pluralsight_notekeeper_android.database.dao.DataManager;
import com.example.pluralsight_notekeeper_android.database.models.Course;
import com.example.pluralsight_notekeeper_android.database.models.Note;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_note_list);

        initializeListContentAndHandleClick();
    }

    private void initializeListContentAndHandleClick() {
        final ListView list_note = findViewById(R.id.list_note);
        List<Note> notes = DataManager.getInstance().getNotes();

        ArrayAdapter<Note> notesAdapter =
                new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1,
                        notes);

        list_note.setAdapter(notesAdapter);

        //handle an item click

       list_note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long l) {
                Intent goToOneItemDetails = new Intent(NoteListActivity.this,
                        NoteActivity.class);

                Note clickedItemNoteInformation = (Note) list_note.getItemAtPosition(position);

                goToOneItemDetails.putExtra(NoteActivity.NOTE,clickedItemNoteInformation);
                startActivity(goToOneItemDetails);
            }
        });
    }


}
