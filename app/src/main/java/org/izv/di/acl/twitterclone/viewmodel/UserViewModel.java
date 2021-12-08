package org.izv.di.acl.twitterclone.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.repository.TwitterRepository;

public class UserViewModel extends AndroidViewModel {
    private TwitterRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new TwitterRepository(application);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void getUserById(String username, String pass) {
        repository.getUserById(username, pass);
    }

    public MutableLiveData<Long> getLiveInsertUserResult() {
        return repository.getLiveInsertUserResult();
    }

    public MutableLiveData<Integer> getLiveUserCountResult() {
        return repository.getLiveUserCountResult();
    }

    public MutableLiveData<User> getLiveUserSearchResult() {
        return repository.getLiveUserSearchResult();
    }
}
