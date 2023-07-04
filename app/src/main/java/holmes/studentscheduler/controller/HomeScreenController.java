package holmes.studentscheduler.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Term;

public class HomeScreenController extends AppCompatActivity {

    public static int numAlert;
    public static String alertTitle;
    TextView startDateTextView, endDateTextView;
    EditText termNameEditText;
    CalendarView termCalendarView;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        termNameEditText = findViewById(R.id.termNameEditText);
        termCalendarView = findViewById(R.id.termCalendarView);
        termCalendarView.setVisibility(View.INVISIBLE);

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
                Intent intentHome = new Intent(HomeScreenController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSelectStartDate(View view) {
        termCalendarView.setVisibility(View.VISIBLE);

        termCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String startDateText = "Start Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                startDateTextView.setText(startDateText);
                termCalendarView.setVisibility(View.INVISIBLE);
            }
        });
        System.out.println("onSelectStartDate clicked");
    }

    public void onSelectEndDate(View view) {
        termCalendarView.setVisibility(View.VISIBLE);

        termCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String endDateText = "End Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                endDateTextView.setText(endDateText);
                termCalendarView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onViewAllTerms(View view) {

        Intent intent = new Intent(HomeScreenController.this, TermListController.class);
        startActivity(intent);
    }

    public void onAddTerm(View view) {

        if (termNameEditText.getText().toString().equals("")) {
            Toast.makeText(HomeScreenController.this, "Please enter a Term Name.", Toast.LENGTH_SHORT).show();
        } else if (startDateTextView.getText().toString().equals("Start Date:")) {
            Toast.makeText(HomeScreenController.this, "Please enter a Start Date.", Toast.LENGTH_SHORT).show();
        } else if (endDateTextView.getText().toString().equals("End Date:")) {
            Toast.makeText(HomeScreenController.this, "Please enter a End Date.", Toast.LENGTH_SHORT).show();
        } else {
            Term newTerm = new Term(-1, termNameEditText.getText().toString(), startDateTextView.getText().toString(), endDateTextView.getText().toString());
            dataBaseHelper = new DataBaseHelper(HomeScreenController.this);
            dataBaseHelper.addTerm(newTerm);
            Toast.makeText(HomeScreenController.this, "Term " + termNameEditText.getText().toString() + " added.", Toast.LENGTH_SHORT).show();
        }

    }

}