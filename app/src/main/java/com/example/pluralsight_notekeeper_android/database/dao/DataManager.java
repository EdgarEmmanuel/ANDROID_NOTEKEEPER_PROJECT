package com.example.pluralsight_notekeeper_android.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pluralsight_notekeeper_android.database.models.Course;
import com.example.pluralsight_notekeeper_android.database.models.Module;
import com.example.pluralsight_notekeeper_android.database.models.Note;
import com.example.pluralsight_notekeeper_android.database.utilities.CourseTableUtilities;
import com.example.pluralsight_notekeeper_android.database.utilities.NoteTableUtilities;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;

    private List<Course> mCourses = new ArrayList<>();
    private List<Note> mNotes = new ArrayList<>();

    public static DataManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DataManager();
           ourInstance.initializeCourses();
           ourInstance.initializeExampleNotes();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(NoteDatabase dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        final String[] courseColumns = {
                CourseTableUtilities.COLUMN_COURSE_ID,
                CourseTableUtilities.COLUMN_COURSE_TITLE};

        final Cursor courseCursor = db.query(CourseTableUtilities.TABLE_NAME, courseColumns,
                null, null, null, null, CourseTableUtilities.COLUMN_COURSE_TITLE + " DESC");

        loadCoursesFromDatabase(courseCursor);

        final String[] noteColumns = {
                NoteTableUtilities.COLUMN_NOTE_TITLE,
                NoteTableUtilities.COLUMN_NOTE_TEXT,
                NoteTableUtilities.COLUMN_COURSE_ID,
                NoteTableUtilities._ID};
        String noteOrderBy = NoteTableUtilities.COLUMN_COURSE_ID + "," + NoteTableUtilities.COLUMN_NOTE_TITLE;

        final Cursor noteCursor = db.query(NoteTableUtilities.TABLE_NAME, noteColumns,
                null, null, null, null, noteOrderBy);

        loadNotesFromDatabase(noteCursor);
    }

    private static void loadNotesFromDatabase(Cursor cursor) {
        int noteTitlePos = cursor.getColumnIndex(NoteTableUtilities.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(NoteTableUtilities.COLUMN_NOTE_TEXT);
        int courseIdPos = cursor.getColumnIndex(NoteTableUtilities.COLUMN_COURSE_ID);
        int idPos = cursor.getColumnIndex(NoteTableUtilities._ID);

        DataManager dm = getInstance();
        dm.mNotes.clear();

        while(cursor.moveToNext()) {
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);
            String courseId = cursor.getString(courseIdPos);
            int id = cursor.getInt(idPos);

            Course noteCourse = dm.getCourse(courseId);
            Note note = new Note(id,noteCourse, noteTitle, noteText);
            dm.mNotes.add(note);
        }
        cursor.close();
    }

    private static void loadCoursesFromDatabase(Cursor cursor) {

        int courseIdPos = cursor.getColumnIndex(CourseTableUtilities.COLUMN_COURSE_ID);
        int courseTitlePos = cursor.getColumnIndex(CourseTableUtilities.COLUMN_COURSE_TITLE);

//        Log.d("debug", "courseIdPos = "+courseIdPos);
//        Log.d("debug", "courseTitlePos = "+courseTitlePos);

        DataManager dm = getInstance();
        dm.mCourses.clear();

        while(cursor.moveToNext()) {
            String courseId = cursor.getString(courseIdPos);
            String courseTitle = cursor.getString(courseTitlePos);
            Course course = new Course(courseId, courseTitle, null);

            dm.mCourses.add(course);
        }
        cursor.close();
    }

    public String getCurrentUserName() {
        return "Jim Wilson";
    }

    public String getCurrentUserEmail() {
        return "jimw@jwhh.com";
    }

    public List<Note> getNotes() {
        return mNotes;
    }

    public int createNewNote() {
        Note note = new Note(null, null, null);
        mNotes.add(note);
        return mNotes.size() - 1;
    }

    public int findNote(Note note) {
        for(int index = 0; index < mNotes.size(); index++) {
            if(note.equals(mNotes.get(index)))
                return index;
        }

        return -1;
    }

    public void removeNote(int index) {
        mNotes.remove(index);
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public Course getCourse(String id) {
        for (Course course : mCourses) {
            if (id.equals(course.getCourseId()))
                return course;
        }
        return null;
    }

    public List<Note> getNotes(Course course) {
        ArrayList<Note> notes = new ArrayList<>();
        for(Note note:mNotes) {
            if(course.equals(note.getCourse()))
                notes.add(note);
        }
        return notes;
    }

    public int getNoteCount(Course course) {
        int count = 0;
        for(Note note:mNotes) {
            if(course.equals(note.getCourse()))
                count++;
        }
        return count;
    }

    private DataManager() {
    }

    //region Initialization code

    private void initializeCourses() {
        mCourses.add(initializeCourse1());
        mCourses.add(initializeCourse2());
        mCourses.add(initializeCourse3());
        mCourses.add(initializeCourse4());
    }

    public void initializeExampleNotes() {
        final DataManager dm = getInstance();

        Course course = dm.getCourse("android_intents");
        course.getModule("android_intents_m01").setComplete(true);
        course.getModule("android_intents_m02").setComplete(true);
        course.getModule("android_intents_m03").setComplete(true);
        mNotes.add(new Note(course, "Dynamic intent resolution",
                "Wow, intents allow components to be resolved at runtime"));
        mNotes.add(new Note(course, "Delegating intents",
                "PendingIntents are powerful; they delegate much more than just a component invocation"));

        course = dm.getCourse("android_async");
        course.getModule("android_async_m01").setComplete(true);
        course.getModule("android_async_m02").setComplete(true);
        mNotes.add(new Note(course, "Service default threads",
                "Did you know that by default an Android Service will tie up the UI thread?"));
        mNotes.add(new Note(course, "Long running operations",
                "Foreground Services can be tied to a notification icon"));

        course = dm.getCourse("java_lang");
        course.getModule("java_lang_m01").setComplete(true);
        course.getModule("java_lang_m02").setComplete(true);
        course.getModule("java_lang_m03").setComplete(true);
        course.getModule("java_lang_m04").setComplete(true);
        course.getModule("java_lang_m05").setComplete(true);
        course.getModule("java_lang_m06").setComplete(true);
        course.getModule("java_lang_m07").setComplete(true);
        mNotes.add(new Note(course, "Parameters",
                "Leverage variable-length parameter lists"));
        mNotes.add(new Note(course, "Anonymous classes",
                "Anonymous classes simplify implementing one-use types"));

        course = dm.getCourse("java_core");
        course.getModule("java_core_m01").setComplete(true);
        course.getModule("java_core_m02").setComplete(true);
        course.getModule("java_core_m03").setComplete(true);
        mNotes.add(new Note(course, "Compiler options",
                "The -jar option isn't compatible with with the -cp option"));
        mNotes.add(new Note(course, "Serialization",
                "Remember to include SerialVersionUID to assure version compatibility"));
    }

    private Course initializeCourse1() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("android_intents_m01", "Android Late Binding and Intents"));
        modules.add(new Module("android_intents_m02", "Component activation with intents"));
        modules.add(new Module("android_intents_m03", "Delegation and Callbacks through PendingIntents"));
        modules.add(new Module("android_intents_m04", "IntentFilter data tests"));
        modules.add(new Module("android_intents_m05", "Working with Platform Features Through Intents"));

        return new Course("android_intents", "Android Programming with Intents", modules);
    }

    private Course initializeCourse2() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("android_async_m01", "Challenges to a responsive user experience"));
        modules.add(new Module("android_async_m02", "Implementing long-running operations as a service"));
        modules.add(new Module("android_async_m03", "Service lifecycle management"));
        modules.add(new Module("android_async_m04", "Interacting with services"));

        return new Course("android_async", "Android Async Programming and Services", modules);
    }

    private Course initializeCourse3() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("java_lang_m01", "Introduction and Setting up Your Environment"));
        modules.add(new Module("java_lang_m02", "Creating a Simple App"));
        modules.add(new Module("java_lang_m03", "Variables, Data Types, and Math Operators"));
        modules.add(new Module("java_lang_m04", "Conditional Logic, Looping, and Arrays"));
        modules.add(new Module("java_lang_m05", "Representing Complex Types with Classes"));
        modules.add(new Module("java_lang_m06", "Class Initializers and Constructors"));
        modules.add(new Module("java_lang_m07", "A Closer Look at Parameters"));
        modules.add(new Module("java_lang_m08", "Class Inheritance"));
        modules.add(new Module("java_lang_m09", "More About Data Types"));
        modules.add(new Module("java_lang_m10", "Exceptions and Error Handling"));
        modules.add(new Module("java_lang_m11", "Working with Packages"));
        modules.add(new Module("java_lang_m12", "Creating Abstract Relationships with Interfaces"));
        modules.add(new Module("java_lang_m13", "Static Members, Nested Types, and Anonymous Classes"));

        return new Course("java_lang", "Java Fundamentals: The Java Language", modules);
    }

    private Course initializeCourse4() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("java_core_m01", "Introduction"));
        modules.add(new Module("java_core_m02", "Input and Output with Streams and Files"));
        modules.add(new Module("java_core_m03", "String Formatting and Regular Expressions"));
        modules.add(new Module("java_core_m04", "Working with Collections"));
        modules.add(new Module("java_core_m05", "Controlling App Execution and Environment"));
        modules.add(new Module("java_core_m06", "Capturing Application Activity with the Java Log System"));
        modules.add(new Module("java_core_m07", "Multithreading and Concurrency"));
        modules.add(new Module("java_core_m08", "Runtime Type Information and Reflection"));
        modules.add(new Module("java_core_m09", "Adding Type Metadata with Annotations"));
        modules.add(new Module("java_core_m10", "Persisting Objects with Serialization"));

        return new Course("java_core", "Java Fundamentals: The Core Platform", modules);
    }

    public int createNewNote(Course course, String noteTitle, String noteText) {
        int index = createNewNote();
        Note note = getNotes().get(index);
        note.setCourse(course);
        note.setTitle(noteTitle);
        note.setText(noteText);

        return index;
    }
    //endregion

}
