package holmes.studentscheduler.Listener;

import android.widget.Button;

import holmes.studentscheduler.model.Assessment;

public interface AssessmentSelectListener {

    void onItemClicked(Assessment assessment);

    void onAssessmentDelete(Button deleteButton, Assessment assessment);
}
