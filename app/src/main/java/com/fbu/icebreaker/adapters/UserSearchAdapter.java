package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.R;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {

    private static final String TAG = "UserSearchAdapter";

    private final Context context;
    private final List<ParseUser> users;
    private final OnClickListener clickListener;

    public interface OnClickListener {
        void onUserClicked(int position);
    }

    public UserSearchAdapter(Context context, List<ParseUser> users, OnClickListener clickListener) {
        this.context = context;
        this.users = users;
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ParseUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() { return users.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvUsername;
        private final ImageView ivUserProfilePicture;
        private final RelativeLayout rlSearchUser;

        public ViewHolder(View view) {
            super(view);
            tvUsername = view.findViewById(R.id.tvUsername);
            ivUserProfilePicture = view.findViewById(R.id.ivUserProfilePicture);
            rlSearchUser = view.findViewById(R.id.rlSearchUser);
        }

        public void bind(ParseUser user) {

            Log.i(TAG, user.getUsername());

            tvUsername.setText(user.getUsername());
            Glide.with(context)
                    .load(Objects.requireNonNull(user.getParseFile("profilePicture")).getUrl())
                    .into(ivUserProfilePicture);

            rlSearchUser.setOnClickListener(v -> clickListener.onUserClicked(getAdapterPosition()));
        }
    }
}
