package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.PairingsByTag;

import org.w3c.dom.Text;

import java.util.List;

public class GridHobbyPairingAdapter extends BaseAdapter {

    Context context;
    List<PairingsByTag> pairingsByTags;

    public GridHobbyPairingAdapter(Context context, List<PairingsByTag> pairingsByTags) {
        this.context = context;
        this.pairingsByTags = pairingsByTags;
    }

    @Override
    public int getCount() {
        return pairingsByTags.size();
    }

    @Override
    public Object getItem(int position) {
        return pairingsByTags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RecyclerView rvHobbiesPairing;
        TextView tvPairTag;
        PairedHobbiesByTagAdapter adapter;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grid_hobby, null);
        }

        rvHobbiesPairing = convertView.findViewById(R.id.rvHobbiesPairing);
        tvPairTag = convertView.findViewById(R.id.tvPairTag);

        adapter = new PairedHobbiesByTagAdapter(context, pairingsByTags.get(position).getPairedHobbies());
        rvHobbiesPairing.setAdapter(adapter);

        tvPairTag.setText(pairingsByTags.get(position).getPairedTag());

        return convertView;
    }
}
