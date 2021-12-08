package org.izv.di.acl.twitterclone.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tweets",
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "idUser", onDelete = ForeignKey.CASCADE)})
public class Tweet implements Parcelable {
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

    public Tweet(){}

    protected Tweet(Parcel in) {
        id = in.readLong();
        idUser = in.readLong();
        content = in.readString();
        urlPic = in.readString();
        date = in.readString();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(idUser);
        dest.writeString(content);
        dest.writeString(urlPic);
        dest.writeString(date);
    }
}
