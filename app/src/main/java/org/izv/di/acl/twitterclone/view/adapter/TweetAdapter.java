package org.izv.di.acl.twitterclone.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;
import org.izv.di.acl.twitterclone.view.adapter.viewholder.TweetViewHolder;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {
    private List<UserTweet> tweetList;
    private Context context;

    public TweetAdapter(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        UserTweet userType = tweetList.get(position);
        Tweet tweet = userType.tweet;
        holder.tweet = tweet;
        User user = userType.user;
        holder.tvUsername.setText(user.username);
        holder.tvDate.setText(tweet.date);
        holder.tvDesc.setText(tweet.content);
        try {
            Glide.with(this.context).load(tweet.urlPic).into(holder.ivImageDesc);
            Glide.with(this.context).load(user.urlUserPic).into(holder.ivUserPic);
        }catch (Exception e){
            Log.v("xyzyx", "Amsiedad");
        }

    }

    @Override
    public int getItemCount() {
        if (tweetList == null)
            return 0;
        return tweetList.size();
    }

    public void setTweetList(List<UserTweet> tweetList) {
        this.tweetList = tweetList;
        notifyDataSetChanged();
    }
}
