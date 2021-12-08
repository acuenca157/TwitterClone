package org.izv.di.acl.twitterclone.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.model.entity.Tweet;

public class TweetViewHolder extends RecyclerView.ViewHolder {
    public TextView tvUsername, tvDate, tvDesc;
    public ImageView ivUserPic, ivImageDesc, ivEdit;
    public Tweet tweet;

    public TweetViewHolder(@NonNull View itemView){
        super(itemView);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvDesc = itemView.findViewById(R.id.tvDesc);

        ivUserPic = itemView.findViewById(R.id.ivUserPic);
        ivImageDesc = itemView.findViewById(R.id.ivImageDesc);
        ivEdit = itemView.findViewById(R.id.ivOptions);

    }
}
