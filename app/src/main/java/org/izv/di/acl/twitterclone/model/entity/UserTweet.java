package org.izv.di.acl.twitterclone.model.entity;

import androidx.room.Embedded;

public class UserTweet {

    @Embedded
    public Tweet tweet;

    @Embedded(prefix = "user_")
    public User user;

}
