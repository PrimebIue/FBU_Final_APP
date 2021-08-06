package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class PairedIndividualHobbiesByTagAdapter extends RecyclerView.Adapter<PairedIndividualHobbiesByTagAdapter.ViewHolder> {

    private final List<Hobby> pairedHobbies;

    private final Context context;

    private final OnClickListener clickListener;

    public interface OnClickListener {
        void onHobbyClicked(int position);
    }

    public PairedIndividualHobbiesByTagAdapter(Context context, List<Hobby> pairedHobbies, OnClickListener clickListener) {
        this.context = context;
        this.pairedHobbies = pairedHobbies;
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public PairedIndividualHobbiesByTagAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paired_hobby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PairedIndividualHobbiesByTagAdapter.ViewHolder holder, int position) {
        Hobby hobby = pairedHobbies.get(position);
        holder.bind(hobby);
    }

    @Override
    public int getItemCount() {
        return pairedHobbies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvHobbyName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvHobbyName = itemView.findViewById(R.id.tvHobbyName);
        }

        public void bind(Hobby hobby) {
            Random rnd = new Random();
            tvHobbyName.setText(hobby.getName());
            tvHobbyName.getBackground().setTint(Color.argb(255, rnd.nextInt(250), rnd.nextInt(250), rnd.nextInt(250)));

            tvHobbyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { clickListener.onHobbyClicked(getAdapterPosition()); }
            });
        }
    }
}
