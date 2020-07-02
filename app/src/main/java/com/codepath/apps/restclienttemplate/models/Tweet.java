package com.codepath.apps.restclienttemplate.models;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    @PrimaryKey
    public long id;

    @Ignore
    public User user;

    @ColumnInfo
    public long userId;

    @ColumnInfo
    public String date;
    //public Boolean hasMedia;

    @ColumnInfo
    public  String mediaIdUrl;

    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.id = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.date = getRelativeDate(jsonObject.getString("created_at"));
        tweet.userId = tweet.user.id;
        try {
            tweet.mediaIdUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            Log.i("Gohome", tweet.mediaIdUrl);
        }
        catch (JSONException e){
            tweet.mediaIdUrl = "";

        }
        //tweet.mediaIdUrl = jsonObject.getJSONArray("extended_entities").getJSONObject(0).getString
       //jsonObject.getJSONObject("retweeted_status").getJSONObject("quoted_status").getJSONObject("extended_entities").getJSONArray("media")
         //       .getJSONObject(0).getString("media_url");
        //tweet.hasMedia = jsonObject.getBoolean()
        return tweet;
    }

    public static String getRelativeDate(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
