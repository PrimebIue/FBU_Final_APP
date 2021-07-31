package com.fbu.icebreaker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.subclasses.Hobby;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.MultiViewHolder> {

    private static final String TAG = "MultiSelectionAdapter";

    private final Context context;
    private List<Hobby> hobbies;

    public MultiSelectionAdapter(Context context, List<Hobby> hobbies) {
        this.context = context;
        this.hobbies = hobbies;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hobby_selection, parent, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        Hobby hobby = hobbies.get(position);
        holder.bind(hobby);
    }

    @Override
    public int getItemCount() { return hobbies.size(); }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvHobbyName;

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHobbyName = itemView.findViewById(R.id.tvHobbyName);
        }

        void bind(final Hobby hobby) {
            tvHobbyName.getBackground().setTint(hobby.getChecked() ? ContextCompat.getColor(context, R.color.orange) : ContextCompat.getColor(context, R.color.light_blue));
            tvHobbyName.setText(hobby.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hobby.setChecked(!hobby.getChecked());
                    tvHobbyName.getBackground().setTint(hobby.getChecked() ? ContextCompat.getColor(context, R.color.orange) : ContextCompat.getColor(context, R.color.light_blue));
                    Log.i(TAG, String.valueOf(hobby.getChecked()));
                }
            });
        }
    }

    // Get all items selected
    public List<Hobby> getAll() { return hobbies; }

    // Get selected when button is clicked

    public List<Hobby> getSelected() {
        List<Hobby> selected = new ArrayList<>();
        for (int i = 0; i < hobbies.size(); i++){
            if (hobbies.get(i).getChecked()) {
                selected.add(hobbies.get(i));
            }
        }
        return selected;
    }
}
