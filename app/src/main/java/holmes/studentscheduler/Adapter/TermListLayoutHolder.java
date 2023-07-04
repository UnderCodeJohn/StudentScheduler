package holmes.studentscheduler.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import holmes.studentscheduler.R;

public class TermListLayoutHolder extends RecyclerView.ViewHolder {

    TextView termListLayoutTermName, termListLayoutStartDate, termListLayoutEndDate;
    Button termListLayoutDeleteButton;
    ConstraintLayout termListLayoutContainer;

    public TermListLayoutHolder(@NonNull View itemView) {
        super(itemView);
        termListLayoutTermName = itemView.findViewById(R.id.termListLayoutTextView);
        termListLayoutStartDate = itemView.findViewById(R.id.termListStartDateTextView);
        termListLayoutEndDate = itemView.findViewById(R.id.termListEndDateTextView);
        termListLayoutDeleteButton = itemView.findViewById(R.id.noteListDeleteButton);
        termListLayoutContainer = itemView.findViewById(R.id.termListLayoutContainer);
    }
}
