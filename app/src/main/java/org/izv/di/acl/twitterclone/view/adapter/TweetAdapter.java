package org.izv.di.acl.twitterclone.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;
import org.izv.di.acl.twitterclone.view.adapter.viewholder.TweetViewHolder;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {
    private List<UserTweet> tweetList;
    private Context context;
    private long idUser;
    private boolean fromMainMenu;

    public TweetAdapter(Context ctx, long user, boolean fromMainMenu){
        this.context = ctx;
        this.idUser = user;
        this.fromMainMenu = fromMainMenu;
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

        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                holder.ivImageDesc.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                // everything worked out, so probably nothing to do
                return false;
            }
        };

        try {
            Glide.with(this.context).asBitmap().load(tweet.urlPic).listener(requestListener).into(holder.ivImageDesc);
            Glide.with(this.context).load(user.urlUserPic).into(holder.ivUserPic);
        }catch (Exception e){
            holder.ivImageDesc.setVisibility(View.GONE);
            Log.v("xyzyx", "NO he podidio cargar la imagen");
        }

        if (holder.tweet.idUser == idUser){
            holder.ivEdit.setVisibility(View.VISIBLE);
            holder.ivEdit.setOnClickListener((View v) -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("tweet", tweet);
                if (fromMainMenu)
                    Navigation.findNavController(holder.itemView).navigate(R.id.action_tweetsFragment_to_editTweet, bundle);
                else
                    Navigation.findNavController(holder.itemView).navigate(R.id.action_profileFragment_to_editTweet, bundle);
            });
        }else{
            holder.ivEdit.setVisibility(View.GONE);
        }

        if (fromMainMenu) {
            holder.ivUserPic.setOnClickListener((View v) -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("searchUser", user);
                Navigation.findNavController(v).navigate(R.id.action_tweetsFragment_to_profileFragment, bundle);
            });
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
