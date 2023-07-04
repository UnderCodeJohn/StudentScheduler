package holmes.studentscheduler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Listener.InstructorSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Instructor;

public class InstructorListLayoutAdapter extends RecyclerView.Adapter<InstructorListLayoutHolder> {

    Context context;
    List<Instructor> instructors;
    InstructorSelectListener instructorListener;
    Button deleteButton, editButton;

    public InstructorListLayoutAdapter(Context context, List<Instructor> instructors, InstructorSelectListener instructorListener, Button deleteButton, Button editButton) {
        this.context = context;
        this.instructors = instructors;
        this.instructorListener = instructorListener;
        this.deleteButton = deleteButton;
        this.editButton = editButton;
    }

    @NonNull
    @Override
    public InstructorListLayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructorListLayoutHolder(LayoutInflater.from(context).inflate(R.layout.instructor_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorListLayoutHolder holder, int position) {

        holder.instructorListName.setText(instructors.get(position).getInstructorName());
        holder.instructorListPhone.setText(instructors.get(position).getInstructorPhone());
        holder.instructorListEmail.setText(instructors.get(position).getInstructorEmail());

        holder.instructorDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorListener.onInstructorDelete(deleteButton, instructors.get(position));
            }
        });

        holder.instructorEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorListener.onInstructorEdit(editButton, instructors.get(position));
            }
        });

        holder.instructorListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorListener.onItemClicked(instructors.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }
}
