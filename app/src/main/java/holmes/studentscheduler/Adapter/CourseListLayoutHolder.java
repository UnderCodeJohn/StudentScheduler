package holmes.studentscheduler.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import holmes.studentscheduler.R;

public class CourseListLayoutHolder extends RecyclerView.ViewHolder {

    TextView courseListLayoutTermName, courseListLayoutStartDate, courseListLayoutEndDate;
    Button courseListLayoutDeleteButton;
    ConstraintLayout courseListLayoutContainer;

    public CourseListLayoutHolder(@NonNull View itemView) {
        super(itemView);

        courseListLayoutTermName = itemView.findViewById(R.id.courseListLayoutTextView);
        courseListLayoutStartDate = itemView.findViewById(R.id.courseListStartDateTextView);
        courseListLayoutEndDate = itemView.findViewById(R.id.courseListEndDateTextView);
        courseListLayoutDeleteButton = itemView.findViewById(R.id.courseListDeleteButton);
        courseListLayoutContainer = itemView.findViewById(R.id.courseListLayoutContainer);
    }
}
