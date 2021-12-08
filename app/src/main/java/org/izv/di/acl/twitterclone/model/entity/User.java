package org.izv.di.acl.twitterclone.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users",
        indices = {@Index(value = "username", unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "username")
    public String username;

    @NonNull
    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "urlUserPic")
    public String urlUserPic;

    public User(){}
}
