package holmes.studentscheduler.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import holmes.studentscheduler.R;

public class AssessmentListHolder extends RecyclerView.ViewHolder {

    TextView assessmentListName, assessmentListStartDate, assessmentListEndDate;
    Button assessmentListDeleteButton;
    ConstraintLayout assessmentListContainer;

    public AssessmentListHolder(@NonNull View itemView) {
        super(itemView);

        assessmentListName = itemView.findViewById(R.id.assessmentListNameTextView);
        assessmentListStartDate = itemView.findViewById(R.id.assessmentListStartDateTextView);
        assessmentListEndDate = itemView.findViewById(R.id.assessmentListEndDateTextView);
        assessmentListDeleteButton = itemView.findViewById(R.id.noteListDeleteButton);
        assessmentListContainer = itemView.findViewById(R.id.assessmentListContainer);
    }
}
