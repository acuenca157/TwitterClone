package org.izv.di.acl.twitterclone.view.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.databinding.FragmentTweetsBinding;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;
import org.izv.di.acl.twitterclone.view.activity.MainActivity;
import org.izv.di.acl.twitterclone.view.adapter.TweetAdapter;
import org.izv.di.acl.twitterclone.viewmodel.TweetViewModel;

import java.util.List;


public class TweetsFragment extends Fragment {
    private @NonNull FragmentTweetsBinding binding;

    private User user;
    TextView tvUsername;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTweetsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = ((MainActivity)getActivity()).getUser();
        initialize();
    }

    private void initialize() {
        binding.floatingActionButton.setOnClickListener((View v)->{
            Log.v("xyzyx", "Oliwi");
            NavHostFragment.findNavController(this).navigate(R.id.action_tweetsFragment_to_createTweet);
        });

        RecyclerView rvTweets = binding.rvTweets;
        rvTweets.setLayoutManager(new LinearLayoutManager(this.getContext()));

        TweetViewModel tvm = new ViewModelProvider(this).get(TweetViewModel.class);
        TweetAdapter tweetAdapter = new TweetAdapter(this.getContext());

        rvTweets.setAdapter(tweetAdapter);
        LiveData<List<UserTweet>> listaUserTweet = tvm.getAllTweets();
        listaUserTweet.observe(this, userTweets -> {
            tweetAdapter.setTweetList(userTweets);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}