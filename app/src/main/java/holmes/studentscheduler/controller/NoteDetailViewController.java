package holmes.studentscheduler.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.R;

public class NoteDetailViewController extends AppCompatActivity {

    EditText noteDetailName, noteDetailNotes;
    NoteListController selectedNote = new NoteListController();
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail_view);

        noteDetailName = findViewById(R.id.editTextNoteDetailName);
        noteDetailNotes = findViewById(R.id.editTextNotesMultiLine);

        noteDetailName.setText(selectedNote.passNote().getNoteName());
        noteDetailNotes.setText(selectedNote.passNote().getNotes());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(NoteDetailViewController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveNotes(View view) {
        if (noteDetailName.getText().toString().equals("")){
            Toast.makeText(NoteDetailViewController.this, "Please Enter a Note Name.", Toast.LENGTH_SHORT).show();
        } else {
            selectedNote.passNote().setNoteName(noteDetailName.getText().toString());
            selectedNote.passNote().setNotes(noteDetailNotes.getText().toString());
            dataBaseHelper = new DataBaseHelper(NoteDetailViewController.this);
            dataBaseHelper.updateNote(selectedNote.passNote());
            Toast.makeText(NoteDetailViewController.this, noteDetailName.getText().toString() + " saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void onShareNote(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, noteDetailNotes.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_TITLE, noteDetailName.getText().toString());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}