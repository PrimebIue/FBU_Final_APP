package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.PairingsByTag;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HobbyPairingAdapter extends RecyclerView.Adapter<HobbyPairingAdapter.ViewHolder> {

    private static final String TAG = "HobbyPairingAdapter";

    private Context context;

    private List<PairingsByTag> pairingsByTags;

    public HobbyPairingAdapter(Context context, List<PairingsByTag> pairingsByTags) {
        this.context = context;
        this.pairingsByTags = pairingsByTags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_hobby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PairingsByTag pairingsByTag = pairingsByTags.get(position);
            holder.bind(pairingsByTag);
    }

    @Override
    public int getItemCount() { return pairingsByTags.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPairTag;
        private RecyclerView rvHobbiesPairing;
        private PairedIndividualHobbiesByTagAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPairTag = itemView.findViewById(R.id.tvPairTag);
            rvHobbiesPairing = itemView.findViewById(R.id.rvHobbiesPairing);
        }

        public void bind(PairingsByTag pairingsByTag) {

            tvPairTag.setText(pairingsByTag.getPairedTag());
            adapter = new PairedIndividualHobbiesByTagAdapter(context, pairingsByTag.getPairedHobbies());

            RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e) {
                    int action = e.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e) {}

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
            };
            rvHobbiesPairing.addOnItemTouchListener(mScrollTouchListener);
            rvHobbiesPairing.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rvHobbiesPairing.setLayoutManager(linearLayoutManager);
            adapter.notifyDataSetChanged();
        }
    }
}
