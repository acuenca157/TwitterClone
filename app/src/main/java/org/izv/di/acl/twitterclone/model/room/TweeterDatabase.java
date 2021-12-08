package org.izv.di.acl.twitterclone.model.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;

@Database(entities = {User.class, Tweet.class}, version = 1, exportSchema = false)
public abstract class TweeterDatabase extends RoomDatabase{
    public abstract TweeterDao getDao();

    private static volatile TweeterDatabase INSTANCE;

    /* versión simplificada */
    public static TweeterDatabase getDatabase(Context ctx){
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), TweeterDatabase.class, "tweeter").build();

        return INSTANCE;
    }
}
