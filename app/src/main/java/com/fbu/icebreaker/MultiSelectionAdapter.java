package com.fbu.icebreaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.subclasses.Hobby;

import java.util.ArrayList;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.MultiViewHolder> {

    private final Context context;
    private ArrayList<Hobby> hobbies;

    public MultiSelectionAdapter(Context context, ArrayList<Hobby> hobbies) {
        this.context = context;
        this.hobbies = hobbies;
    }

    public void setHobbies(ArrayList<Hobby> hobbies) {
        this.hobbies = new ArrayList<>();
        this.hobbies = hobbies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hobby_selection, parent, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(hobbies.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rlHobbySelection;

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            rlHobbySelection = itemView.findViewById(R.id.rlHobbySelection);
        }

        void bind(final Hobby hobby) {
            rlHobbySelection.setBackgroundColor(hobby.getChecked() ? ContextCompat.getColor(context, R.color.gray_500) : ContextCompat.getColor(context, R.color.light_blue));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hobby.setChecked(!hobby.getChecked());
                    rlHobbySelection.setBackgroundColor(hobby.getChecked() ? ContextCompat.getColor(context, R.color.gray_500) : ContextCompat.getColor(context, R.color.light_blue));
                }
            });
        }
    }

    // Get all items selected
    public ArrayList<Hobby> getAll() { return hobbies; }

    // Get selected when button is clicked

    public ArrayList<Hobby> getSelected() {
        ArrayList<Hobby> selected = new ArrayList<>();
        for (int i = 0; i < hobbies.size(); i++){
            if (hobbies.get(i).getChecked()) {
                selected.add(hobbies.get(i));
            }
        }
        return selected;
    }

}
