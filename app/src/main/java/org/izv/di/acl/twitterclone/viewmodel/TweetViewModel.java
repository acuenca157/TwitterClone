package org.izv.di.acl.twitterclone.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;
import org.izv.di.acl.twitterclone.model.repository.TwitterRepository;

import java.util.List;

public class TweetViewModel extends AndroidViewModel {
    private TwitterRepository repository;
    public TweetViewModel(@NonNull Application application) {
        super(application);
        repository = new TwitterRepository(application);
    }

    public void insertTweet(Tweet tweet) {
        repository.insertTweet(tweet);
    }

    public int updateTweet(Tweet tweet) {
        return repository.updateTweet(tweet);
    }

    public LiveData<List<UserTweet>> getAllTweets() {
        return repository.getAllTweets();
    }

    public MutableLiveData<Long> getLiveInsertTweetResult() {
        return repository.getLiveInsertTweetResult();
    }
}
