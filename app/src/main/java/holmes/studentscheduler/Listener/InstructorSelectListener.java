package holmes.studentscheduler.Listener;

import android.widget.Button;

import holmes.studentscheduler.model.Instructor;

public interface InstructorSelectListener {

    void onItemClicked(Instructor instructor);
    void onInstructorDelete(Button deleteButton, Instructor instructor);
    void onInstructorEdit(Button editButton, Instructor instructor);
}
