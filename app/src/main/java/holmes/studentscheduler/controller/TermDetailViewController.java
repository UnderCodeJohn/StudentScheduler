package holmes.studentscheduler.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Course;
import holmes.studentscheduler.model.Term;

public class TermDetailViewController extends AppCompatActivity {

    TextView termDetailName;
    TextView termDetailStartDate;
    TextView termDetailEndDate;
    TextView termDetailNumberOfCourses;
    TermListController selectedTerm = new TermListController();
    DataBaseHelper dataBaseHelper;
    List<Course> allCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);

        dataBaseHelper = new DataBaseHelper(TermDetailViewController.this);

        termDetailName = findViewById(R.id.termDetailNameTextView);
        termDetailStartDate = findViewById(R.id.termDetailStartDateTextView);
        termDetailEndDate = findViewById(R.id.termDetailEndDateTextView);
        termDetailNumberOfCourses = findViewById(R.id.termDetailNumberOfCoursesTextView);

        termDetailName.setText(selectedTerm.passTerm().getTermName());
        termDetailStartDate.setText(selectedTerm.passTerm().getTermStartDate());
        termDetailEndDate.setText(selectedTerm.passTerm().getTermEndDate());

        allCourses = dataBaseHelper.getAllCourses(selectedTerm.passTerm());
        termDetailNumberOfCourses.setText("Number of Course: " + allCourses.size());

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
                Intent intentHome = new Intent(TermDetailViewController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onViewAllCourses(View view) {
        Intent intent = new Intent(TermDetailViewController.this, CourseListController.class);
        startActivity(intent);
    }

    public void onDeleteTerm(View view) {
        dataBaseHelper = new DataBaseHelper(TermDetailViewController.this);

        if (allCourses.size() != 0) {
            Toast.makeText(TermDetailViewController.this, "Cannot delete " + selectedTerm.passTerm().getTermName() + " term - " + allCourses.size() + " course remain. Please remove all course before deleting a term.", Toast.LENGTH_LONG).show();
        } else {
            dataBaseHelper.deleteTerm(selectedTerm.passTerm());
            Intent intent = new Intent(TermDetailViewController.this, TermListController.class);
            startActivity(intent);
        }
    }
}