package com.example.pluralsight_notekeeper_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private int createdNotePosition;
    private ArrayAdapter<Course> coursesAdapter;
    private List<Course> coursesList;
    Boolean isCancelling = false;
    private Boolean hasNewNote;
    private String OriginalmessageNoteInformationCourseId;
    private String OriginalmessageNoteInformationTitle;
    private String OriginalmessageNoteInformationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initializeViewWidgets();

        FillTheSpinnerViewWidget();

        displayDataIfExtraData();

        saveOriginalNoteValues();
    }

    private void saveOriginalNoteValues() {
        if(hasNewNote)
            return ;
        OriginalmessageNoteInformationCourseId = messageNoteInformation.getCourse().getCourseId();
        OriginalmessageNoteInformationTitle = messageNoteInformation.getTitle();
        OriginalmessageNoteInformationText = messageNoteInformation.getText();
    }

    public void initializeViewWidgets(){
        spinner_courses_view = findViewById(R.id.spinner_courses);
        textView_note_title = findViewById(R.id.text_note_title);
        textView_note_text = findViewById(R.id.text_note_text);
    }


    public void FillTheSpinnerViewWidget(){

        coursesList = DataManager.getInstance().getCourses();

        coursesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,coursesList);

        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_courses_view.setAdapter(coursesAdapter);

    }


    public void displayDataIfExtraData(){
        if(!hasExtraData())
            displayExtraDataByNotePosition(spinner_courses_view, textView_note_title,
                    textView_note_text,notePosition);
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveOrNotSaveNoteByActionOnCancelButton();
    }

    public void saveOrNotSaveNoteByActionOnCancelButton(){
        if(isCancelling){
            if(hasNewNote){
                DataManager.getInstance().removeNote(notePosition);
            } else {
                storePreviousNoteValues();
            }
        } else {
            saveNote();
        }
    }

    private void storePreviousNoteValues() {
        Course course = DataManager.getInstance().getCourse(OriginalmessageNoteInformationCourseId);
        messageNoteInformation.setCourse(course);
        messageNoteInformation.setTitle(OriginalmessageNoteInformationTitle);
        messageNoteInformation.setText(OriginalmessageNoteInformationText);
    }


    private void saveNote() {
        messageNoteInformation.setCourse((Course)spinner_courses_view.getSelectedItem());
        messageNoteInformation.setText(textView_note_text.getText().toString().trim());
        messageNoteInformation.setTitle(textView_note_title.getText().toString().trim());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_send_email){
            sendMail();
            return true;
        } else if (id == R.id.action_cancel){
            isCancelling = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMail(){
       String courseName = spinner_courses_view.getSelectedItem().toString();
       String note_title_value = textView_note_title.getText().toString().trim();
       String note_text_value = textView_note_text.getText().toString().trim();
       String finalContent ="Course : "+ courseName
               +"\n\n"+" title : "+note_title_value+"\n\n"+ " Content : \n "+note_text_value;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, courseName);
        intent.putExtra(Intent.EXTRA_TEXT, finalContent);
        intent.setType("message/rf2822");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

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

        hasNewNote = (notePosition == DEFAULT_POSITION);
        if(hasNewNote){
            createNewNote();
            return true;
        }else{
            messageNoteInformation = DataManager.getInstance().getNotes().get(notePosition);
            return false;
        }
    }

    private void createNewNote() {
        DataManager dataManager = DataManager.getInstance();
        createdNotePosition = dataManager.createNewNote();
        messageNoteInformation =
                DataManager.getInstance().getNotes().get(createdNotePosition);
    }


}