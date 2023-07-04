package holmes.studentscheduler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Listener.NoteSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Note;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListHolder> {

    Context context;
    List<Note> notes;
    NoteSelectListener noteListener;
    Button deleteButton;

    public NoteListAdapter(Context context, List<Note> notes, NoteSelectListener noteListener, Button deleteButton) {
        this.context = context;
        this.notes = notes;
        this.noteListener = noteListener;
        this.deleteButton = deleteButton;
    }

    @NonNull
    @Override
    public NoteListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListHolder(LayoutInflater.from(context).inflate(R.layout.note_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListHolder holder, int position) {

        holder.noteListName.setText(notes.get(position).getNoteName());

        holder.noteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteListener.onNoteDelete(deleteButton, notes.get(position));
            }
        });

        holder.noteListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteListener.onItemClicked(notes.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
