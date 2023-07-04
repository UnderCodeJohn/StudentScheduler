package holmes.studentscheduler.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Instructor;

public class InstructorDetailViewController extends AppCompatActivity {

    EditText instructorName, instructorPhone, instructorEmail;
    Instructor selectedInstructor = new InstructorListViewController().passInstructor();
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_detail_view);

        instructorName = findViewById(R.id.editTextDetailInstructorName);
        instructorPhone = findViewById(R.id.editTextDetailInstructorPhone);
        instructorEmail = findViewById(R.id.editTextDetailInstructorEmail);

        instructorName.setText(selectedInstructor.getInstructorName());
        instructorPhone.setText(selectedInstructor.getInstructorPhone());
        instructorEmail.setText(selectedInstructor.getInstructorEmail());
    }

    public void onSaveInstructor(View view) {
        selectedInstructor.setInstructorName(instructorName.getText().toString());
        selectedInstructor.setInstructorPhone(instructorPhone.getText().toString());
        selectedInstructor.setInstructorEmail(instructorEmail.getText().toString());

        dataBaseHelper = new DataBaseHelper(InstructorDetailViewController.this);
        dataBaseHelper.updateInstructor(selectedInstructor);

        Intent intent = new Intent(InstructorDetailViewController.this, InstructorListViewController.class);
        startActivity(intent);
    }
}