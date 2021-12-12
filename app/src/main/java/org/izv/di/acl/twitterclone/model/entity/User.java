package org.izv.di.acl.twitterclone.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users",
        indices = {@Index(value = "username", unique = true)})
public class User implements Parcelable {
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


    @ColumnInfo(name = "urlUserBanner")
    public String urlUserBanner;

    public User(){}

    protected User(Parcel in) {
        id = in.readLong();
        username = in.readString();
        password = in.readString();
        description = in.readString();
        urlUserPic = in.readString();
        urlUserBanner = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(description);
        dest.writeString(urlUserPic);
        dest.writeString(urlUserBanner);
    }
}
