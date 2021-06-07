package com.example.pluralsight_notekeeper_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pluralsight_notekeeper_android.NoteActivity;
import com.example.pluralsight_notekeeper_android.R;
import com.example.pluralsight_notekeeper_android.database.dao.DataManager;
import com.example.pluralsight_notekeeper_android.database.models.Course;
import com.example.pluralsight_notekeeper_android.database.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    FloatingActionButton floating_action_button;
    private ArrayAdapter<Note> notesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_note_list);

        initializeWidgetView();

        initializeListContentAndHandleClick();
    }

    public void initializeWidgetView(){
        floating_action_button = findViewById(R.id.floating_action_button);

        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this,NoteActivity.class));
            }
        });
    }



    private void initializeListContentAndHandleClick() {
        ListView list_note = initializeListContent();

        HandleOneItemClickEvent(list_note);
    }

    public ListView initializeListContent(){
        final ListView list_note = findViewById(R.id.list_note);
        List<Note> notes = DataManager.getInstance().getNotes();

        notesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        list_note.setAdapter(notesAdapter);

        return list_note;
    }

    public void HandleOneItemClickEvent(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent goToNoteActivity = new Intent(NoteListActivity.this,
                        NoteActivity.class);

               // Note clickedItemNoteInformation = (Note) list.getItemAtPosition(position);

                goToNoteActivity.putExtra(NoteActivity.NOTE_POSITION,position);
                startActivity(goToNoteActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesAdapter.notifyDataSetChanged();
    }












}
