package org.izv.di.acl.twitterclone.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;
import org.izv.di.acl.twitterclone.model.room.TweeterDao;
import org.izv.di.acl.twitterclone.model.room.TweeterDatabase;

import java.util.List;

public class TwitterRepository {
    private TweeterDao dao;
    private LiveData<List<User>> liveUsers;
    private LiveData<List<Tweet>> liveTweet;
    private UserTweet userTweet;

    private MutableLiveData<Long> liveInsertUserResult;
    private MutableLiveData<Integer> liveUserCountResult;
    private MutableLiveData<User> liveUserSearchResult;
    private MutableLiveData<Long> liveInsertTweetResult;
    private MutableLiveData<Integer> liveUpdatedTweetResult;

    private SharedPreferences preferences;

    public TwitterRepository(Context ctx){
        TweeterDatabase db = TweeterDatabase.getDatabase(ctx);
        userTweet = new UserTweet();
        dao = db.getDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        liveInsertUserResult = new MutableLiveData<>();
        liveUserCountResult = new MutableLiveData<>();
        liveUserSearchResult = new MutableLiveData<>();
        liveInsertTweetResult = new MutableLiveData<>();
        liveUpdatedTweetResult = new MutableLiveData<>();

    }
    public void insertUser(User user) {
        Runnable r = () ->{
            liveInsertUserResult.postValue(dao.insertUser(user));
        };
        new Thread(r).start();
    }

    public void insertTweet(Tweet tweet) {
        Runnable r = () -> {
            liveInsertTweetResult.postValue(dao.insertTweet(tweet));
        };
        new Thread(r).start();
    }

    public int updateUser(User user) {
        return dao.updateUser(user);
    }

    public void updateTweet(Tweet tweet) {
        Runnable r = () -> {
            liveUpdatedTweetResult.postValue(dao.updateTweet(tweet));
        };
        new Thread(r).start();
    }

    public int deleteUser(User user) {
        return dao.deleteUser(user);
    }

    public int deleteTweet(Tweet tweet) {
        return dao.deleteTweet(tweet);
    }

    public LiveData<List<UserTweet>> getAllTweets() {
        return dao.getAllTweets();
    }

    /*public void checkUser(String username, String pass) {
        Runnable r = () -> {
            liveUserCountResult.postValue(dao.checkUser(username, pass));
        };
        new Thread(r).start();
    }*/

    public void getUserById(String username, String pass) {
        Runnable r = () -> {
            liveUserSearchResult.postValue(dao.getUser(username, pass));
        };
        new Thread(r).start();
    }

    public MutableLiveData<Long> getLiveInsertUserResult(){
        return liveInsertUserResult;
    }

    public MutableLiveData<Integer> getLiveUserCountResult() {
        return liveUserCountResult;
    }

    public MutableLiveData<User> getLiveUsers() {
        return liveUserSearchResult;
    }

    public MutableLiveData<User> getLiveUserSearchResult() {
        return liveUserSearchResult;
    }

    public MutableLiveData<Long> getLiveInsertTweetResult() {
        return liveInsertTweetResult;
    }

    public MutableLiveData<Integer> getLiveUpdatedTweetResult() {
        return liveUpdatedTweetResult;
    }
}
