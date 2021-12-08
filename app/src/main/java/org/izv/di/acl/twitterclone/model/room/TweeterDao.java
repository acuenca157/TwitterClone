package org.izv.di.acl.twitterclone.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;

import java.util.List;

@Dao
public interface TweeterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertTweet(Tweet tweet);

    @Update
    int updateUser(User user);

    @Update
    int updateTweet(Tweet tweet);

    @Delete
    int deleteUser(User user);

    @Delete
    int deleteTweet(Tweet tweet);

    @Query("SELECT t.*, u.id user_id, u.username user_username, u.urlUserPic user_urlUserPic FROM tweets t JOIN users u ON t.idUser = u.id ORDER BY t.id DESC")
    LiveData<List<UserTweet>> getAllTweets();

    /*@Query("SELECT COUNT(id) FROM users WHERE username = :username AND password = :pass")
    int checkUser(String username, String pass);*/

    @Query("SELECT * FROM users WHERE username = :username AND password = :pass")
    User getUser(String username, String pass);
}
