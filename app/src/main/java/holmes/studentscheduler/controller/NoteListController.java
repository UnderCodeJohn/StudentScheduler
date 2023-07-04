package holmes.studentscheduler.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Adapter.NoteListAdapter;
import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.NoteSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Note;

public class NoteListController extends AppCompatActivity implements NoteSelectListener {

    private static Note selectedNotes;
    EditText noteName;
    RecyclerView noteRecyclerView;
    CourseListController selectedCourse = new CourseListController();
    DataBaseHelper dataBaseHelper;
    Button deleteButton;
    List<Note> allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        noteName = findViewById(R.id.editTextNoteName);
        noteRecyclerView = findViewById(R.id.noteRecyclerView);
        deleteButton = findViewById(R.id.noteListDeleteButton);
        dataBaseHelper = new DataBaseHelper(NoteListController.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showAllNotes();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(NoteListController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAllNotes() {
        allNotes = dataBaseHelper.getAllNotes(selectedCourse.passCourse());
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setAdapter(new NoteListAdapter(getApplicationContext(), allNotes, this, deleteButton));
    }

    public void onAddNote(View view) {
        if (noteName.getText().toString().equals("")){
            Toast.makeText(NoteListController.this, "Please Enter a Note Name.", Toast.LENGTH_SHORT).show();
        } else {
            Note newNote = new Note(-1, selectedCourse.passCourse().getCourseId(), "", noteName.getText().toString());
            dataBaseHelper = new DataBaseHelper(NoteListController.this);
            dataBaseHelper.addNote(newNote);
            Toast.makeText(NoteListController.this, noteName.getText().toString() + " note added.", Toast.LENGTH_SHORT).show();
            showAllNotes();
        }
    }

    @Override
    public void onItemClicked(Note note) {
        Intent intent = new Intent(NoteListController.this, NoteDetailViewController.class);
        startActivity(intent);

        selectedNotes = note;
    }

    @Override
    public void onNoteDelete(Button deleteButton, Note note) {
        dataBaseHelper = new DataBaseHelper(NoteListController.this);
        dataBaseHelper.deleteNote(note);
        showAllNotes();
    }

    public Note passNote() {
        return selectedNotes;
    }
}