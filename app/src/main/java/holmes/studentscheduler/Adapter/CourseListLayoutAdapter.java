package holmes.studentscheduler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Listener.CourseSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Course;

public class CourseListLayoutAdapter extends RecyclerView.Adapter<CourseListLayoutHolder> {

    Context context;
    List<Course> courses;
    CourseSelectListener courseListener;
    Button deleteButton;

    public CourseListLayoutAdapter(Context context, List<Course> courses, CourseSelectListener courseListener, Button deleteButton) {
        this.context = context;
        this.courses = courses;
        this.courseListener = courseListener;
        this.deleteButton = deleteButton;
    }

    @NonNull
    @Override
    public CourseListLayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseListLayoutHolder(LayoutInflater.from(context).inflate(R.layout.course_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListLayoutHolder holder, int position) {

        holder.courseListLayoutTermName.setText(courses.get(position).getCourseName());
        holder.courseListLayoutStartDate.setText(courses.get(position).getCourseStartDate());
        holder.courseListLayoutEndDate.setText(courses.get(position).getCourseEndDate());

        holder.courseListLayoutDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseListener.onCourseDelete(deleteButton, courses.get(position));
            }
        });

        holder.courseListLayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseListener.onItemClicked(courses.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
