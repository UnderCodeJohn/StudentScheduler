package holmes.studentscheduler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import holmes.studentscheduler.Listener.TermSelectListener;
import holmes.studentscheduler.R;
import holmes.studentscheduler.model.Term;

public class TermListLayoutAdapter extends RecyclerView.Adapter<TermListLayoutHolder> {

    Context context;
    List<Term> terms;
    TermSelectListener termListener;
    Button deleteButton;


    public TermListLayoutAdapter(Context context, List<Term> terms, TermSelectListener termListener, Button deleteButton) {
        this.context = context;
        this.terms = terms;
        this.termListener = termListener;
        this.deleteButton = deleteButton;
    }

    @NonNull
    @Override
    public TermListLayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TermListLayoutHolder(LayoutInflater.from(context).inflate(R.layout.term_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TermListLayoutHolder holder, int position) {
        holder.termListLayoutTermName.setText(terms.get(position).getTermName());
        holder.termListLayoutStartDate.setText(terms.get(position).getTermStartDate());
        holder.termListLayoutEndDate.setText(terms.get(position).getTermEndDate());

        holder.termListLayoutDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termListener.onTermDelete(deleteButton, terms.get(position));
            }
        });

        holder.termListLayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termListener.onItemClicked(terms.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return terms.size();
    }
}
