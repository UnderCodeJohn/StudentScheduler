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

import holmes.studentscheduler.Adapter.AssessmentListAdapter;
import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.AssessmentSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.Receiver.Receiver;
import holmes.studentscheduler.model.Assessment;

public class AssessmentListController extends AppCompatActivity implements AssessmentSelectListener {

    private static Assessment selectedAssessment;
    private static String startDate;
    private static String endDate;
    EditText assessmentName;
    TextView assessmentStartDate, assessmentEndDate;
    CalendarView assessmentCalendar;
    RecyclerView assessmentRecycler;
    CourseListController selectedCourse = new CourseListController();
    DataBaseHelper dataBaseHelper;
    Button deleteButton;
    List<Assessment> allAssessments;
    private final String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        assessmentName = findViewById(R.id.editTextAssessmentName);
        assessmentStartDate = findViewById(R.id.textViewAssessmentStartDate);
        assessmentEndDate = findViewById(R.id.textViewAssessmentEndDate);
        assessmentCalendar = findViewById(R.id.calendarViewAssessment);
        assessmentRecycler = findViewById(R.id.assessmentRecyclerView);
        deleteButton = findViewById(R.id.noteListDeleteButton);
        dataBaseHelper = new DataBaseHelper(AssessmentListController.this);

        assessmentCalendar.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showAllAssessments();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(AssessmentListController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAllAssessments() {
        allAssessments = dataBaseHelper.getAllAssessments(selectedCourse.passCourse());
        assessmentRecycler.setLayoutManager(new LinearLayoutManager(this));
        assessmentRecycler.setAdapter(new AssessmentListAdapter(getApplicationContext(), allAssessments, this, deleteButton));
    }

    public void onAssessmentStartDate(View view) {
        assessmentRecycler.setVisibility(View.INVISIBLE);
        assessmentCalendar.setVisibility(View.VISIBLE);

        assessmentCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String startDateText = "Start Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                startDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                assessmentStartDate.setText(startDateText);
                assessmentCalendar.setVisibility(View.INVISIBLE);
                assessmentRecycler.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onAssessmentEndDate(View view) {
        assessmentRecycler.setVisibility(View.INVISIBLE);
        assessmentCalendar.setVisibility(View.VISIBLE);

        assessmentCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String endDateText = "End Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                endDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                assessmentEndDate.setText(endDateText);
                assessmentCalendar.setVisibility(View.INVISIBLE);
                assessmentRecycler.setVisibility(View.VISIBLE);

            }
        });
    }

    public void onAddAssessment(View view) {

        if (assessmentName.getText().toString().equals("")) {
            Toast.makeText(AssessmentListController.this, "Please Enter a Assessment Name.", Toast.LENGTH_SHORT).show();
        } else if (assessmentStartDate.getText().toString().equals("Start Date:")) {
            Toast.makeText(AssessmentListController.this, "Please Enter a Start Date.", Toast.LENGTH_SHORT).show();
        } else if (assessmentEndDate.getText().toString().equals("End Date:")) {
            Toast.makeText(AssessmentListController.this, "Please Enter a End Date.", Toast.LENGTH_SHORT).show();
        } else {
            Assessment newAssessment = new Assessment(-1, selectedCourse.passCourse().getCourseId(), 1, assessmentName.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString());
            dataBaseHelper = new DataBaseHelper(AssessmentListController.this);
            dataBaseHelper.addAssessment(newAssessment);
            HomeScreenController.alertTitle = assessmentName.getText().toString();

            Date startDateFormat = null;
            try {
                startDateFormat = sdf.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long startDateTrigger = startDateFormat.getTime();
            Intent startDateIntent = new Intent(AssessmentListController.this, Receiver.class);
            startDateIntent.putExtra("key", "Your assessment " + assessmentName.getText().toString() + " starts on " + startDateFormat);
            PendingIntent startDateSender = PendingIntent.getBroadcast(AssessmentListController.this, HomeScreenController.numAlert++, startDateIntent, 0);
            AlarmManager startDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startDateAlarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, startDateSender);

            Date endDateFormat = null;
            try {
                endDateFormat = sdf.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long endDateTrigger = endDateFormat.getTime();
            Intent endDateIntent = new Intent(AssessmentListController.this, Receiver.class);
            endDateIntent.putExtra("key", "Your assessment " + assessmentName.getText().toString() + " ends on " + endDateFormat);
            PendingIntent endDateSender = PendingIntent.getBroadcast(AssessmentListController.this, HomeScreenController.numAlert++, endDateIntent, 0);
            AlarmManager endDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            endDateAlarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endDateSender);

            Toast.makeText(AssessmentListController.this, assessmentName.getText().toString() + " assessment added.", Toast.LENGTH_SHORT).show();

            showAllAssessments();
        }
    }

    @Override
    public void onItemClicked(Assessment assessment) {
        Intent intent = new Intent(AssessmentListController.this, AssessmentDetailViewController.class);
        startActivity(intent);

        selectedAssessment = assessment;
    }

    @Override
    public void onAssessmentDelete(Button deleteButton, Assessment assessment) {
        dataBaseHelper = new DataBaseHelper(AssessmentListController.this);
        dataBaseHelper.deleteAssessment(assessment);
        showAllAssessments();
    }

    public Assessment passAssessment() {
        return selectedAssessment;
    }
}