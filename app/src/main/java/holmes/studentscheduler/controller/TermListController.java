package holmes.studentscheduler.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Adapter.TermListLayoutAdapter;
import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.TermSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Course;
import holmes.studentscheduler.model.Term;

public class TermListController extends AppCompatActivity implements TermSelectListener {

    private static Term selectedTerm;
    RecyclerView termListRecyclerView;
    List<Term> allTerms;
    DataBaseHelper dataBaseHelper;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        termListRecyclerView = findViewById(R.id.termListRecyclerView);
        dataBaseHelper = new DataBaseHelper(TermListController.this);
        deleteButton = findViewById(R.id.noteListDeleteButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showAllTerm();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(TermListController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAllTerm() {
        allTerms = dataBaseHelper.getAllTerms();
        termListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        termListRecyclerView.setAdapter(new TermListLayoutAdapter(getApplicationContext(), allTerms, this, deleteButton));
    }

    @Override
    public void onItemClicked(Term term) {

        Intent intent = new Intent(TermListController.this, TermDetailViewController.class);
        startActivity(intent);
        selectedTerm = term;
    }
    @Override
    public void onTermDelete(Button deleteButton, Term term) {

        dataBaseHelper = new DataBaseHelper(TermListController.this);
        List<Course> allCourses = dataBaseHelper.getAllCourses(term);

        if (allCourses.size() != 0) {
            Toast.makeText(TermListController.this, "Cannot delete " + term.getTermName() + " term - " + allCourses.size() + " course remain. Please remove all course before deleting a term.", Toast.LENGTH_LONG).show();
        } else {
            dataBaseHelper.deleteTerm(term);
            showAllTerm();
        }
    }

    public Term passTerm() {
        return selectedTerm;
    }

}