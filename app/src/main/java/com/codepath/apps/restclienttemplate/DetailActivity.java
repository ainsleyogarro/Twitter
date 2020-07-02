package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import static androidx.core.content.ContextCompat.startActivity;

public class DetailActivity extends AppCompatActivity {
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Populate view with all the details from tweet
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));
        TextView body = findViewById(R.id.tvDetailBody);
        TextView name = findViewById(R.id.tvDetailName);
        TextView screenName = findViewById(R.id.tvScreenName);
        ImageView profilePic = findViewById(R.id.ivDetailProfileImage);
        ImageView ivMedia = findViewById(R.id.ivDetailMedia);
        body.setText(tweet.body);
        name.setText(tweet.user.name);
        screenName.setText(tweet.user.screenName);
        Glide.with(getApplicationContext()).load(tweet.user.profileImageUrl).into(profilePic);
        if (!tweet.mediaIdUrl.equals("")){
            Glide.with(getApplicationContext()).load(tweet.mediaIdUrl).into(ivMedia);
            ivMedia.setVisibility(View.VISIBLE);
        }
        else {
            ivMedia.setVisibility(View.GONE);
        }
        TextView createdAt = findViewById(R.id.tvDetailCreatedAt);
        createdAt.setText(tweet.createdAt);
        ImageButton reply = findViewById(R.id.detailBtnReply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
                //Intent intent = new Intent(this, ComposeActivity.class);
                i.putExtra("Author", "@" + tweet.user.screenName);
                i.putExtra("Reply", true);
                startActivity( i);
            }
        });

        ImageButton retweet = findViewById(R.id.detailBtnRetweet);
        retweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
                i.putExtra("Author", "RT@" + tweet.user.screenName + tweet.body);
                i.putExtra("Reply", true);
                startActivity(i);
            }
        });
    }
}