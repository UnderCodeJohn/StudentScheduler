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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.AssessmentSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.Receiver.Receiver;
import holmes.studentscheduler.model.Assessment;

public class AssessmentDetailViewController extends AppCompatActivity implements AssessmentSelectListener {

    private static Assessment editAssessment;
    private static String startDate;
    private static String endDate;
    TextView detailAssessmentName, detailAssessmentStartDate, detailAssessmentEndDate, detailAssessmentType, editAssessmentStartDate, editAssessmentEndDate, editAssessmentType;
    EditText editAssessmentName;
    CheckBox editAssessmentStartDateAlert, editAssessmentEndDateAlert;
    AssessmentListController selectedAssessment = new AssessmentListController();
    DataBaseHelper dataBaseHelper;
    RadioGroup assessmentTypeGroup;
    CalendarView editAssessmentCalendarView;
    private final String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail_view);

        editAssessment = selectedAssessment.passAssessment();
        dataBaseHelper = new DataBaseHelper(AssessmentDetailViewController.this);

        detailAssessmentName = findViewById(R.id.textViewAssessmentDetailName);
        detailAssessmentStartDate = findViewById(R.id.textViewAssessmentDetailStartDate);
        detailAssessmentEndDate = findViewById(R.id.textViewAssessmentDetailEndDate);
        detailAssessmentType = findViewById(R.id.textViewAssessmentDetailType);

        detailAssessmentName.setText(selectedAssessment.passAssessment().getAssessmentName());
        detailAssessmentStartDate.setText(selectedAssessment.passAssessment().getAssessmentStartDate());
        detailAssessmentEndDate.setText(selectedAssessment.passAssessment().getAssessmentEndDate());
        detailAssessmentType.setText(String.format("Assessment Type: %s", dataBaseHelper.getAssessmentType(editAssessment)));

        startDate = detailAssessmentStartDate.getText().toString().substring(11);
        endDate = detailAssessmentEndDate.getText().toString().substring(9);

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
                Intent intentHome = new Intent(AssessmentDetailViewController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateAssessmentInfo() {
        Intent intent = new Intent(AssessmentDetailViewController.this, AssessmentListController.class);
        startActivity(intent);
    }

    public void onEditAssessment(View view) {
        setContentView(R.layout.edit_assessment_layout);
        editAssessmentName = findViewById(R.id.editTextEditAssessmentName);
        editAssessmentStartDate = findViewById(R.id.textViewEditAssessmentStartDate);
        editAssessmentEndDate = findViewById(R.id.textViewEditAssessmentEndDate);
        editAssessmentType = findViewById(R.id.textViewEditAssessmentType);
        editAssessmentStartDateAlert = findViewById(R.id.checkBoxEditAssessmentStartDateAlert);
        editAssessmentEndDateAlert = findViewById(R.id.checkBoxEditAssessmentEndDateAlert);

        editAssessmentName.setText(detailAssessmentName.getText());
        editAssessmentStartDate.setText(editAssessment.getAssessmentStartDate());
        editAssessmentEndDate.setText(editAssessment.getAssessmentEndDate());
        editAssessmentType.setText(String.format("Assessment Type: %s", dataBaseHelper.getAssessmentType(editAssessment)));

        assessmentTypeGroup = findViewById(R.id.radioGroupAssessmentType);
        editAssessmentCalendarView = findViewById(R.id.calendarViewEditAssessment);

        editAssessmentCalendarView.setVisibility(View.INVISIBLE);

        assessmentTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton selectedTypeRadioButton = findViewById(checkedId);
                if (selectedTypeRadioButton.getText().equals("Performance")) {
                    editAssessment.setAssessmentTypeId(2);
                    editAssessmentType.setText(String.format("Assessment Type: %s", selectedTypeRadioButton.getText()));
                }
                if (selectedTypeRadioButton.getText().equals("Objective")) {
                    editAssessment.setAssessmentTypeId(3);
                    editAssessmentType.setText(String.format("Assessment Type: %s", selectedTypeRadioButton.getText()));
                }
            }
        });

    }

    @Override
    public void onItemClicked(Assessment assessment) {
        detailAssessmentName.setText(assessment.getAssessmentName());
        detailAssessmentStartDate.setText(assessment.getAssessmentStartDate());
        detailAssessmentEndDate.setText(assessment.getAssessmentEndDate());
        detailAssessmentType.setText(String.format("Assessment Type: %s", dataBaseHelper.getAssessmentType(editAssessment)));

        editAssessment = assessment;
    }

    @Override
    public void onAssessmentDelete(Button deleteButton, Assessment assessment) {
        dataBaseHelper = new DataBaseHelper(AssessmentDetailViewController.this);
        dataBaseHelper.deleteAssessment(assessment);
    }

    public void onEditAssessmentStartDate(View view) {
        assessmentTypeGroup.setVisibility(View.INVISIBLE);
        editAssessmentCalendarView.setVisibility(View.VISIBLE);

        editAssessmentCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String startDateText = "Start Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                startDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                editAssessmentStartDate.setText(startDateText);
                editAssessment.setAssessmentStartDate(startDateText);
                editAssessmentCalendarView.setVisibility(View.INVISIBLE);
                assessmentTypeGroup.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onEditAssessmentEndDate(View view) {
        assessmentTypeGroup.setVisibility(View.INVISIBLE);
        editAssessmentCalendarView.setVisibility(View.VISIBLE);

        editAssessmentCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String endDateText = "End Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
                endDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                editAssessmentEndDate.setText(endDateText);
                editAssessment.setAssessmentEndDate(endDateText);
                editAssessmentCalendarView.setVisibility(View.INVISIBLE);
                assessmentTypeGroup.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onSaveAssessment(View view) {

        if (editAssessmentName.getText().toString().equals("")){
            Toast.makeText(AssessmentDetailViewController.this, " Please Enter an Assessment Name.", Toast.LENGTH_SHORT).show();
        } else {
            editAssessment.setAssessmentName(editAssessmentName.getText().toString());
            dataBaseHelper.updateAssessment(editAssessment);
            HomeScreenController.alertTitle = editAssessmentName.getText().toString();

            if (editAssessmentStartDateAlert.isChecked()) {
                Date startDateFormat = null;
                try {
                    startDateFormat = sdf.parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startDateTrigger = startDateFormat.getTime();
                Intent startDateIntent = new Intent(AssessmentDetailViewController.this, Receiver.class);
                startDateIntent.putExtra("key", "Your " + editAssessmentName.getText().toString() + " assessment starts on " + startDateFormat);
                PendingIntent startDateSender = PendingIntent.getBroadcast(AssessmentDetailViewController.this, HomeScreenController.numAlert++, startDateIntent, 0);
                AlarmManager startDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startDateAlarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, startDateSender);
            }
            if (editAssessmentEndDateAlert.isChecked()) {
                Date endDateFormat = null;
                try {
                    endDateFormat = sdf.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endDateTrigger = endDateFormat.getTime();
                Intent endDateIntent = new Intent(AssessmentDetailViewController.this, Receiver.class);
                endDateIntent.putExtra("key", "Your " + editAssessmentName.getText().toString() + " assessment ends on " + endDateFormat);
                PendingIntent endDateSender = PendingIntent.getBroadcast(AssessmentDetailViewController.this, HomeScreenController.numAlert++, endDateIntent, 0);
                AlarmManager endDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endDateAlarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endDateSender);
            }
            updateAssessmentInfo();
        }
    }
}