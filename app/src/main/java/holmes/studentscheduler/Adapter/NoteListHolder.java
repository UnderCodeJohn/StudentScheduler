package holmes.studentscheduler.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import holmes.studentscheduler.R;

public class NoteListHolder extends RecyclerView.ViewHolder {

    TextView noteListName;
    Button noteListButton;
    ConstraintLayout noteListContainer;

    public NoteListHolder(@NonNull View itemView) {
        super(itemView);

        noteListName = itemView.findViewById(R.id.noteListNameTextView);
        noteListButton = itemView.findViewById(R.id.noteListDeleteButton);
        noteListContainer = itemView.findViewById(R.id.noteListContainer);
    }
}
