package holmes.studentscheduler.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import holmes.studentscheduler.Adapter.CourseListLayoutAdapter;
import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.CourseSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.Receiver.Receiver;
import holmes.studentscheduler.model.Course;

public class CourseListController extends AppCompatActivity implements CourseSelectListener {

    private static Course selectedCourse;
    private static String startDate;
    private static String endDate;
    TextView startDateTextView, endDateTextView;
    EditText courseNameEditText;
    CalendarView courseCalendarView;
    RecyclerView courseRecyclerView;
    TermListController selectedTerm = new TermListController();
    DataBaseHelper dataBaseHelper;
    Button deleteButton;
    List<Course> allCourses;
    private final String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        startDateTextView = findViewById(R.id.courseStartDateTextView);
        endDateTextView = findViewById(R.id.courseEndDateTextView);
        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseCalendarView = findViewById(R.id.courseCalendarView);
        courseRecyclerView = findViewById(R.id.courseListRecyclerView);

        courseCalendarView.setVisibility(View.INVISIBLE);

        dataBaseHelper = new DataBaseHelper(CourseListController.this);
        deleteButton = findViewById(R.id.courseListDeleteButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showAllCourses();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(CourseListController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAllCourses() {
        allCourses = dataBaseHelper.getAllCourses(selectedTerm.passTerm());
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setAdapter(new CourseListLayoutAdapter(getApplicationContext(), allCourses, this, deleteButton));
    }

    public void onStartDate(View view) {
        courseRecyclerView.setVisibility(View.INVISIBLE);
        courseCalendarView.setVisibility(View.VISIBLE);

        courseCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String startDateText = "Start Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                startDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                startDateTextView.setText(startDateText);
                courseCalendarView.setVisibility(View.INVISIBLE);
                courseRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onEndDate(View view) {
        courseRecyclerView.setVisibility(View.INVISIBLE);
        courseCalendarView.setVisibility(View.VISIBLE);

        courseCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String endDateText = "End Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                endDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                endDateTextView.setText(endDateText);
                courseCalendarView.setVisibility(View.INVISIBLE);
                courseRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onAddCourse(View view) {

        if (courseNameEditText.getText().toString().equals("")) {
            Toast.makeText(CourseListController.this, "Please enter a Course Name.", Toast.LENGTH_SHORT).show();
        } else if (startDateTextView.getText().toString().equals("Start Date:")) {
            Toast.makeText(CourseListController.this, "Please enter a Start Date.", Toast.LENGTH_SHORT).show();
        } else if (endDateTextView.getText().toString().equals("End Date:")) {
            Toast.makeText(CourseListController.this, "Please enter a End Date.", Toast.LENGTH_SHORT).show();
        } else {
            Course newCourse = new Course(-1, selectedTerm.passTerm().getTermId(), courseNameEditText.getText().toString(), startDateTextView.getText().toString(), endDateTextView.getText().toString(), 1, 1);
            dataBaseHelper = new DataBaseHelper(CourseListController.this);
            dataBaseHelper.addCourse(newCourse);
            showAllCourses();
            HomeScreenController.alertTitle = courseNameEditText.getText().toString();

            Date startDateFormat = null;
            try {
                startDateFormat = sdf.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long startDateTrigger = startDateFormat.getTime();
            Intent startDateIntent = new Intent(CourseListController.this, Receiver.class);
            startDateIntent.putExtra("key", "Your course " + courseNameEditText.getText().toString() + " starts on " + startDateFormat);
            PendingIntent startDateSender = PendingIntent.getBroadcast(CourseListController.this, HomeScreenController.numAlert++, startDateIntent, 0);
            AlarmManager startDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startDateAlarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, startDateSender);

            Date endDateFormat = null;
            try {
                endDateFormat = sdf.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long endDateTrigger = endDateFormat.getTime();
            Intent endDateIntent = new Intent(CourseListController.this, Receiver.class);
            endDateIntent.putExtra("key", "Your course " + courseNameEditText.getText().toString() + " ends on " + endDateFormat);
            PendingIntent endDateSender = PendingIntent.getBroadcast(CourseListController.this, HomeScreenController.numAlert++, endDateIntent, 0);
            AlarmManager endDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            endDateAlarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endDateSender);

            Toast.makeText(CourseListController.this, courseNameEditText.getText().toString() + " added.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClicked(Course course) {
        Intent intent = new Intent(CourseListController.this, CourseDetailViewController.class);
        startActivity(intent);
        selectedCourse = course;
    }

    @Override
    public void onCourseDelete(Button deleteButton, Course course) {
        dataBaseHelper = new DataBaseHelper(CourseListController.this);
        dataBaseHelper.deleteCourse(course);
        showAllCourses();
    }

    public Course passCourse() {
        return selectedCourse;
    }
}