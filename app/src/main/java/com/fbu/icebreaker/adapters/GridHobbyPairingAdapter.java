package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.PairingsByTag;

import java.util.List;

public class GridHobbyPairingAdapter extends RecyclerView.Adapter<GridHobbyPairingAdapter.ViewHolder> {

    Context context;
    List<PairingsByTag> pairingsByTags;

    public GridHobbyPairingAdapter(Context context, List<PairingsByTag> pairingsByTags) {
        this.context = context;
        this.pairingsByTags = pairingsByTags;
    }

    @NonNull
    @Override
    public GridHobbyPairingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_hobby, parent, false);
        return new ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GridHobbyPairingAdapter.ViewHolder holder, int position) {
        PairingsByTag pairingByTag = pairingsByTags.get(position);
        holder.bind(pairingByTag);
    }

    @Override
    public int getItemCount() {
        return pairingsByTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView, Context context) {
            super(itemView);
        }

        public void bind(PairingsByTag pairingByTag) {
        }
    }
}
