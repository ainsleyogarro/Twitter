package com.codepath.apps.restclienttemplate.models;

import android.provider.ContactsContract;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

//@Parcel
//@Entity(aKinds.Organization.class, parentColumns = "id", childColumns = "organization"))
@Parcel
@Entity
public class User {

    // empty user constructor
    public User(){}

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String screenName;

    @ColumnInfo
    public String profileImageUrl;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.id = jsonObject.getLong("id");
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        return user;

    }


    public static List<User> fromJsonTweetArray(List<Tweet> tweetsFromNetwork) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < tweetsFromNetwork.size(); i++) {
            users.add(tweetsFromNetwork.get(i).user);
        }
        return users;
    }
}
