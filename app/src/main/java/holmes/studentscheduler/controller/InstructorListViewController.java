package holmes.studentscheduler.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import holmes.studentscheduler.Adapter.InstructorListLayoutAdapter;
import holmes.studentscheduler.DataBase.DataBaseHelper;
import holmes.studentscheduler.Listener.InstructorSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Instructor;

public class InstructorListViewController extends AppCompatActivity implements InstructorSelectListener {

    private static Instructor selectedInstructor;
    EditText instructorNameEdit, instructorPhoneEdit, instructorEmailEdit;
    RecyclerView instructorRecyclerView;
    DataBaseHelper dataBaseHelper;
    List<Instructor> allInstructors;
    Button instructorDeleteButton, instructorEditButton;
    CourseListController selectedCourse = new CourseListController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list_view);

        dataBaseHelper = new DataBaseHelper(InstructorListViewController.this);

        instructorNameEdit = findViewById(R.id.instructorNameEditText);
        instructorPhoneEdit = findViewById(R.id.phoneEditText);
        instructorEmailEdit = findViewById(R.id.emailEditText);
        instructorRecyclerView = findViewById(R.id.instructorRecyclerView);
        instructorDeleteButton = findViewById(R.id.instructorDeleteButton);
        instructorEditButton = findViewById(R.id.instructorEditButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showAllInstructors();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(InstructorListViewController.this, HomeScreenController.class);
                startActivity(intentHome);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAllInstructors() {
        allInstructors = dataBaseHelper.getAllInstructors();
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructorRecyclerView.setAdapter(new InstructorListLayoutAdapter(getApplicationContext(), allInstructors, this, instructorDeleteButton, instructorEditButton));
    }

    public void onAddInstructor(View view) {
        Instructor newInstructor = new Instructor(-1, instructorNameEdit.getText().toString(), instructorPhoneEdit.getText().toString(), instructorEmailEdit.getText().toString());
        dataBaseHelper = new DataBaseHelper(InstructorListViewController.this);
        dataBaseHelper.addInstructor(newInstructor);

        showAllInstructors();

    }

    @Override
    public void onItemClicked(Instructor instructor) {
        dataBaseHelper = new DataBaseHelper(InstructorListViewController.this);
        selectedCourse.passCourse().setCourseInstructorId(instructor.getInstructorId());
        dataBaseHelper.updateCourse(selectedCourse.passCourse());
        Intent intent = new Intent(InstructorListViewController.this, CourseDetailViewController.class);
        startActivity(intent);
    }

    @Override
    public void onInstructorDelete(Button deleteButton, Instructor instructor) {
        dataBaseHelper.deleteInstructor(instructor);
        showAllInstructors();
    }

    @Override
    public void onInstructorEdit(Button editButton, Instructor instructor) {
        selectedInstructor = instructor;
        Intent intent = new Intent(InstructorListViewController.this, InstructorDetailViewController.class);
        startActivity(intent);
    }

    public Instructor passInstructor(){
        return selectedInstructor;
    }
}