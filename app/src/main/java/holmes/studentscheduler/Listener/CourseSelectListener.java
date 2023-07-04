package holmes.studentscheduler.Listener;

import android.widget.Button;

import holmes.studentscheduler.model.Course;

public interface CourseSelectListener {

    void onItemClicked(Course course);

    void onCourseDelete(Button deleteButton, Course course);
}
