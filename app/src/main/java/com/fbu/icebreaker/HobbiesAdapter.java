package com.fbu.icebreaker;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.fbu.icebreaker.subclasses.Hobby;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ViewHolder> {

    private static final String TAG = "HobbiesAdapter";

    private Context context;
    private List<Hobby> hobbies;
    private OnClickListener clickListener;

    public interface OnClickListener {
        void onRemoveClicked(int position);
    }

    public HobbiesAdapter(Context context, List<Hobby> hobbies, OnClickListener clickListener) {
        this.context = context;
        this.hobbies = hobbies;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hobby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hobby hobby = hobbies.get(position);
        holder.bind(hobby);
    }

    @Override
    public int getItemCount() { return hobbies.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHobbyName;
        private TagView tvTags;
        private ImageView ivRemove;
        private ImageView ivHobbyImage;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHobbyName = itemView.findViewById(R.id.tvHobbyName);
            tvTags = itemView.findViewById(R.id.tvTag);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            ivHobbyImage = itemView.findViewById(R.id.ivHobbyImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Hobby hobby) {
            tvHobbyName.setText(hobby.getName());
            tvTags.removeAllTags();
            String[] tags = hobby.getTags().toArray(new String[0]);
            for (String s : tags) {
                Random rnd = new Random();
                Tag tag = new Tag(s);
                tag.tagTextSize = 10f;
                tag.layoutColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                tvTags.addTag(tag);
            }

            tvDescription.setText(hobby.getDescription());
            tvDescription.setMaxLines(4);
            tvDescription.setEllipsize(TextUtils.TruncateAt.END);

            final int radius = 30;
            final int margin = 10;
            Glide.with(context)
                    .load(hobby.getImage())
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivHobbyImage);

            ivRemove.setOnClickListener(v -> clickListener.onRemoveClicked(getAdapterPosition()));
        }
    }
}
