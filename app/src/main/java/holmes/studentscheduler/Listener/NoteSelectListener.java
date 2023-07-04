package holmes.studentscheduler.Listener;

import android.widget.Button;

import holmes.studentscheduler.model.Note;

public interface NoteSelectListener {

    void onItemClicked(Note note);

    void onNoteDelete(Button deleteButton, Note note);
}
