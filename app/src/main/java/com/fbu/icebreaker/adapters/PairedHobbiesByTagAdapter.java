package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.fragments.PairedProfileFragment;
import com.fbu.icebreaker.subclasses.Hobby;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class PairedHobbiesByTagAdapter extends RecyclerView.Adapter<PairedHobbiesByTagAdapter.ViewHolder> {

    List<Hobby> pairedHobbies;
    Context context;

    public PairedHobbiesByTagAdapter(Context context, List<Hobby> pairedHobbies) {
        this.context = context;
        this.pairedHobbies = pairedHobbies;
    }

    @NonNull
    @NotNull
    @Override
    public PairedHobbiesByTagAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paired_hobby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PairedHobbiesByTagAdapter.ViewHolder holder, int position) {
        Hobby hobby = pairedHobbies.get(position);
        holder.bind(hobby);
    }

    @Override
    public int getItemCount() {
        return pairedHobbies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHobbyName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvHobbyName = itemView.findViewById(R.id.tvHobbyName);
        }

        public void bind(Hobby hobby) {
            tvHobbyName.setText(hobby.getName());
        }
    }
}
