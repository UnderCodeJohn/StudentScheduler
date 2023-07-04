package holmes.studentscheduler.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import holmes.studentscheduler.R;

public class InstructorListLayoutHolder extends RecyclerView.ViewHolder {

    TextView instructorListName, instructorListPhone, instructorListEmail;
    Button instructorDeleteButton, instructorEditButton;
    ConstraintLayout instructorListContainer;

    public InstructorListLayoutHolder(@NonNull View itemView) {
        super(itemView);
        instructorListName = itemView.findViewById(R.id.instructorListNameTextView);
        instructorListPhone = itemView.findViewById(R.id.instructorListPhoneTextView);
        instructorListEmail = itemView.findViewById(R.id.assessmentListEndDateTextView);
        instructorDeleteButton = itemView.findViewById(R.id.instructorDeleteButton);
        instructorEditButton = itemView.findViewById(R.id.instructorEditButton);
        instructorListContainer = itemView.findViewById(R.id.instructorListContainer);
    }
}
