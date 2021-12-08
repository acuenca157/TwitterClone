package org.izv.di.acl.twitterclone.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tweets",
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "idUser", onDelete = ForeignKey.CASCADE)})
public class Tweet {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "idUser")
    public long idUser;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "urlPic")
    public String urlPic;

    @ColumnInfo(name="date")
    public String date;
}
