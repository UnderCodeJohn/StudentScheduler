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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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

import holmes.studentscheduler.Adapter.InstructorListLayoutAdapter;
import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.InstructorSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.Receiver.Receiver;
import holmes.studentscheduler.model.Course;
import holmes.studentscheduler.model.Instructor;

public class CourseDetailViewController extends AppCompatActivity {

    private static RadioGroup courseStatusGroup;
    private static String startDate;
    private static String endDate;
    private final String myFormat = "MM/dd/yy";
    TextView courseName, courseStartDate, courseEndDate, instructorName, instructorPhone, instructorEmail, courseStatus, editHintTextView, editCourseStartDate, editCourseEndDate, editCourseStatus;
    ScrollView mainLayout;
    EditText editCourseTextEdit;
    CheckBox editCourseStartDateAlert, editCourseEndDateAlert;
    CourseListController selectedCourse = new CourseListController();
    DataBaseHelper dataBaseHelper;
    CalendarView editCourseCalendar;

    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_view);

        dataBaseHelper = new DataBaseHelper(CourseDetailViewController.this);

        mainLayout = findViewById(R.id.courseDetailScrollView);

        courseName = findViewById(R.id.courseDetailNameTextView);
        courseStartDate = findViewById(R.id.courseDetailStartDateTextView);
        courseEndDate = findViewById(R.id.courseDetailEndDateTextView);
        courseStatus = findViewById(R.id.courseDetailStatusTextView);

        instructorName = findViewById(R.id.courseDetailInstructorNameTextView);
        instructorPhone = findViewById(R.id.courseDetailInstructorPhoneTextView);
        instructorEmail = findViewById(R.id.courseDetailInstructorEmailTextView);

        Course updateCourse = dataBaseHelper.getCourse(selectedCourse.passCourse());
        courseName.setText(updateCourse.getCourseName());
        courseStartDate.setText(updateCourse.getCourseStartDate());
        courseEndDate.setText(updateCourse.getCourseEndDate());
        courseStatus.setText(String.format("Course Status: %s", dataBaseHelper.getCourseStatus(selectedCourse.passCourse())));

        startDate = courseStartDate.getText().toString().substring(11);
        endDate = courseEndDate.getText().toString().substring(9);


        Instructor courseInstructor = dataBaseHelper.getInstructor(updateCourse);
        instructorName.setText(String.format("Instructor Name: %s", courseInstructor.getInstructorName()));
        instructorPhone.setText(String.format("Instructor Phone: %s", courseInstructor.getInstructorPhone()));
        instructorEmail.setText(String.format("Instructor Email: %s", courseInstructor.getInstructorEmail()));

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
                Intent intentHome = new Intent(CourseDetailViewController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateCourseInfo() {
        Intent intent = new Intent(CourseDetailViewController.this, CourseDetailViewController.class);
        startActivity(intent);
    }

    public void onViewNotes(View view) {
        Intent intent = new Intent(CourseDetailViewController.this, NoteListController.class);
        startActivity(intent);
        System.out.println("Note");
    }

    public void onViewAssessments(View view) {
        Intent intent = new Intent(CourseDetailViewController.this, AssessmentListController.class);
        startActivity(intent);
    }

    public void onEditCourse(View view) {
        setContentView(R.layout.edit_course_layout_view);

        TextView editCourseStatus = findViewById(R.id.editCourseStatus);
        editCourseStatus.setText(courseStatus.getText());

        editCourseTextEdit = findViewById(R.id.editCourseTextEdit);
        editCourseTextEdit.setText(courseName.getText());

        courseStatusGroup = findViewById(R.id.radioGroupCourseStatus);

        editCourseStartDate = findViewById(R.id.editCourseStartDate);
        editCourseStartDate.setText(courseStartDate.getText());

        editCourseEndDate = findViewById(R.id.editCourseEndDate);
        editCourseEndDate.setText(courseEndDate.getText());

        editHintTextView = findViewById(R.id.editHintTextView);

        editCourseCalendar = findViewById(R.id.calendarViewEditCourse);
        editCourseCalendar.setVisibility(View.INVISIBLE);

        editCourseStartDateAlert = findViewById(R.id.checkBoxEditCourseStartDateAlert);
        editCourseEndDateAlert = findViewById(R.id.checkBoxEditCourseEndDateAlert);


        dataBaseHelper = new DataBaseHelper(CourseDetailViewController.this);
        courseStatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton selectedStatusRadioButton = findViewById(checkedId);
                if (selectedStatusRadioButton.getText().equals("In Progress")) {
                    selectedCourse.passCourse().setCourseStatusId(2);
                    editCourseStatus.setText(String.format("Course Status: %s", selectedStatusRadioButton.getText()));
                    Toast.makeText(CourseDetailViewController.this, "Selected Status: " + selectedStatusRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                if (selectedStatusRadioButton.getText().equals("Completed")) {
                    selectedCourse.passCourse().setCourseStatusId(3);
                    editCourseStatus.setText(String.format("Course Status: %s", selectedStatusRadioButton.getText()));
                    Toast.makeText(CourseDetailViewController.this, "Selected Status: " + selectedStatusRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                if (selectedStatusRadioButton.getText().equals("Dropped")) {
                    selectedCourse.passCourse().setCourseStatusId(4);
                    editCourseStatus.setText(String.format("Course Status: %s", selectedStatusRadioButton.getText()));
                    Toast.makeText(CourseDetailViewController.this, "Selected Status: " + selectedStatusRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                if (selectedStatusRadioButton.getText().equals("Plan to Take")) {
                    selectedCourse.passCourse().setCourseStatusId(5);
                    editCourseStatus.setText(String.format("Course Status: %s", selectedStatusRadioButton.getText()));
                    Toast.makeText(CourseDetailViewController.this, "Selected Status: " + selectedStatusRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onEditAddInstructor(View view) {
        Intent intent = new Intent(CourseDetailViewController.this, InstructorListViewController.class);
        startActivity(intent);
    }
    public void onEditStartDate(View view) {
        courseStatusGroup.setVisibility(View.INVISIBLE);
        editCourseCalendar.setVisibility(View.VISIBLE);
        editHintTextView.setVisibility(View.INVISIBLE);

        editCourseCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String startDateText = "Start Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                startDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                editCourseStartDate.setText(startDateText);
                selectedCourse.passCourse().setCourseStartDate(startDateText);
                editCourseCalendar.setVisibility(View.INVISIBLE);
                editHintTextView.setVisibility(View.VISIBLE);
                courseStatusGroup.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onEditEndDate(View view) {
        courseStatusGroup.setVisibility(View.INVISIBLE);
        editCourseCalendar.setVisibility(View.VISIBLE);
        editHintTextView.setVisibility(View.INVISIBLE);

        editCourseCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String EndDateText = "End Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                endDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                editCourseEndDate.setText(EndDateText);
                selectedCourse.passCourse().setCourseEndDate(EndDateText);
                editCourseCalendar.setVisibility(View.INVISIBLE);
                editHintTextView.setVisibility(View.INVISIBLE);
                courseStatusGroup.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onSave(View view) {
        editCourseTextEdit = findViewById(R.id.editCourseTextEdit);

        if (editCourseTextEdit.getText().toString().equals("")) {
            Toast.makeText(CourseDetailViewController.this, "Please enter a Term Name.", Toast.LENGTH_SHORT).show();
        }
        else {
            selectedCourse.passCourse().setCourseName(editCourseTextEdit.getText().toString());
            dataBaseHelper.updateCourse(selectedCourse.passCourse());
            HomeScreenController.alertTitle = editCourseTextEdit.getText().toString();

            if ((dataBaseHelper.getCourseStatus(selectedCourse.passCourse()).equals("In Progress") || dataBaseHelper.getCourseStatus(selectedCourse.passCourse()).equals("Plan to Take")) && editCourseStartDateAlert.isChecked()) {
                Date startDateFormat = null;
                try {
                    startDateFormat = sdf.parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startDateTrigger = startDateFormat.getTime();
                Intent startDateIntent = new Intent(CourseDetailViewController.this, Receiver.class);
                startDateIntent.putExtra("key", "Your " + editCourseTextEdit.getText().toString() + " course starts on " + startDateFormat);
                PendingIntent startDateSender = PendingIntent.getBroadcast(CourseDetailViewController.this, HomeScreenController.numAlert++, startDateIntent, 0);
                AlarmManager startDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startDateAlarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, startDateSender);
            }
            if ((dataBaseHelper.getCourseStatus(selectedCourse.passCourse()).equals("In Progress") || dataBaseHelper.getCourseStatus(selectedCourse.passCourse()).equals("Plan to Take")) && editCourseEndDateAlert.isChecked()) {
                Date endDateFormat = null;
                try {
                    endDateFormat = sdf.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endDateTrigger = endDateFormat.getTime();
                Intent endDateIntent = new Intent(CourseDetailViewController.this, Receiver.class);
                endDateIntent.putExtra("key", "Your " + editCourseTextEdit.getText().toString() + " course ends on " + endDateFormat);
                PendingIntent endDateSender = PendingIntent.getBroadcast(CourseDetailViewController.this, HomeScreenController.numAlert++, endDateIntent, 0);
                AlarmManager endDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endDateAlarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endDateSender);
            }
            Toast.makeText(CourseDetailViewController.this, editCourseTextEdit.getText().toString() + " Course Saved.", Toast.LENGTH_SHORT).show();
            updateCourseInfo();
        }
    }
}