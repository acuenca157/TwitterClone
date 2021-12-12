package org.izv.di.acl.twitterclone.view.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.databinding.FragmentEditTweetBinding;
import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.view.activity.MainActivity;
import org.izv.di.acl.twitterclone.viewmodel.TweetViewModel;

public class EditTweet extends Fragment {

    private @NonNull
    FragmentEditTweetBinding binding;
    private Tweet tweet;
    Button btnCreate, btnCancel;
    TweetViewModel tvm;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentEditTweetBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tweet = getArguments().getParcelable("tweet");
        initialize();
    }

    private void initialize() {
        getViewModel();
        binding.etContent.setText(tweet.content);
        if(!tweet.urlPic.isEmpty()) {
            binding.etUrl.setText(tweet.urlPic);
            Glide.with(this).load(tweet.urlPic).into(binding.imageView);
        }

        binding.btnCreate.setOnClickListener((View v)->{
            editTweet();
        });

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).toggleUserOption(false);
    }

    private void editTweet() {
        Tweet tweetNew = new Tweet();
        tweetNew.id = tweet.id;
        tweetNew.idUser = tweet.idUser;
        tweetNew.urlPic = binding.etUrl.getText().toString();
        tweetNew.date = tweet.date;
        tweetNew.content = binding.etContent.getText().toString();

        tvm.updateTweet(tweetNew);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void getViewModel() {
        tvm = new ViewModelProvider(this).get(TweetViewModel.class);
        tvm.getLiveUpdatedTweetResult().observe(this.getViewLifecycleOwner(), res -> {
            if (res > 0){
                Log.v("xyzyx", "Se ha editado el tweet " + res);
                NavHostFragment.findNavController(this).navigate(R.id.action_editTweet_to_tweetsFragment);
            }
            else {
                alert(getResources().getString(R.string.error), getResources().getString(R.string.not_tweet_updated));
                Log.v("xyzyx", "Algo ha salido mal creando el tweet");
            }
        });
    }

    private void alert(String title, String description) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this.getContext());
        builder.setTitle(title)
                .setMessage(description);
        builder.create().show();
    }
}