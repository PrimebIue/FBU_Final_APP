package com.fbu.icebreaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.subclasses.Hobby;

import java.util.ArrayList;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.MultiViewHolder> {

    private Context context;
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

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(final Hobby hobby) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hobby.setChecked(!hobby.getChecked());
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
