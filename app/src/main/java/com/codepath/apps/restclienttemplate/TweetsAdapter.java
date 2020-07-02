package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Tweet tweet = tweets.get(position);

        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {

        tweets.clear();

        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // Pass in the context and list of tweets

    // For each row, inflate the layout

    // Bind values based on the position of the element

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvName;
        TextView createdAt;
        ImageView ivMedia;
        Button btnReply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvName = itemView.findViewById(R.id.tvName);
            createdAt = itemView.findViewById(R.id.tvCreatedAt);
            btnReply = itemView.findViewById(R.id.btnReply);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            itemView.setOnClickListener(this);

        }

        public void bind(final Tweet tweet) {

            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvScreenName.setText(tweet.user.screenName);
            createdAt.setText(tweet.date);
            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ComposeActivity.class);
                    //Intent intent = new Intent(this, ComposeActivity.class);
                    i.putExtra("Author", "@" + tweet.user.screenName);
                    i.putExtra("Reply", true);
                    startActivity(context, i,null);
                }
            });
            if (!tweet.mediaIdUrl.equals("")){
                Glide.with(context).load(tweet.mediaIdUrl).into(ivMedia);
                ivMedia.setVisibility(View.VISIBLE);
            }
            else {
                ivMedia.setVisibility(View.GONE);
            }
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

        }


        @Override
        public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    // get the tweet at the position
                    Tweet tweet = tweets.get(position);

                    // Intent to display MovieDetailsActivity
                    Intent intent = new Intent(context, DetailActivity.class);

                    intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));

                    context.startActivity(intent);

                }

        }
    }
}
