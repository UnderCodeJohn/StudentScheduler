package holmes.studentscheduler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Listener.AssessmentSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Assessment;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListHolder> {

    Context context;
    List<Assessment> assessments;
    AssessmentSelectListener assessmentListener;
    Button deleteButton;

    public AssessmentListAdapter(Context context, List<Assessment> assessments, AssessmentSelectListener assessmentListener, Button deleteButton) {
        this.context = context;
        this.assessments = assessments;
        this.assessmentListener = assessmentListener;
        this.deleteButton = deleteButton;
    }

    @NonNull
    @Override
    public AssessmentListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssessmentListHolder(LayoutInflater.from(context).inflate(R.layout.assessment_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListHolder holder, int position) {

        holder.assessmentListName.setText(assessments.get(position).getAssessmentName());
        holder.assessmentListStartDate.setText(assessments.get(position).getAssessmentStartDate());
        holder.assessmentListEndDate.setText(assessments.get(position).getAssessmentEndDate());

        holder.assessmentListDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentListener.onAssessmentDelete(deleteButton, assessments.get(position));
            }
        });

        holder.assessmentListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentListener.onItemClicked(assessments.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }
}
