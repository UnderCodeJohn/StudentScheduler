package holmes.studentscheduler.Listener;

import android.widget.Button;

import holmes.studentscheduler.model.Term;

public interface TermSelectListener {

    void onItemClicked(Term term);

    void onTermDelete(Button deleteButton, Term term);
}
