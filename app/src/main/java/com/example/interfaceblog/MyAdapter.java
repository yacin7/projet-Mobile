package com.example.interfaceblog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaceblog.entities.Blog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Blog> blogs;
    private RecycleViewOnItemClick recyclerViewOnItemClick;
    private String url = Constants.BASE_URL;
    private boolean incrementState = true;

    public MyAdapter(Context context, List<Blog> blogs, RecycleViewOnItemClick recyclerViewOnItemClick) {
        this.context = context;
        this.blogs = blogs;
        this.recyclerViewOnItemClick = recyclerViewOnItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Blog item = blogs.get(position);
        holder.nameView.setText(item.getName());
        holder.descriptionView.setText(item.getDescription());
        holder.dateView.setText(item.getDate());
        holder.imageView.setImageResource(item.getImage());
        holder.likeView.setText(String.valueOf(item.getLike()));
        holder.dislikeView.setText(String.valueOf(item.getDislike()));
        // Set click listeners for like and dislike buttons
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(position);
            }
        });

        holder.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDislike(position);
            }
        });

    }
    private void toggleLike(int position) {
        Blog blog = blogs.get(position);

        if (incrementState) {
            // Increment the like count
            incrementLike(position);
        } else {
            // Decrement the like count
            decrementLike(position);
        }

        // Invert the increment state for the next toggle
        incrementState = !incrementState;
    }

    private void toggleDislike(int position) {
        Blog blog = blogs.get(position);

        if (incrementState) {
            // Increment the dislike count
            incrementDislike(position);
        } else {
            // Decrement the dislike count
            decrementDislike(position);
        }

        // Invert the increment state for the next toggle
        incrementState = !incrementState;
    }

    private void incrementLike(int position) {
        Blog blog = blogs.get(position);
        // Increment the like count locally
        blog.incrementLike();
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

        // Perform additional actions, e.g., update the server
        updateServerLike(blog.getId());
    }

    private void decrementLike(int position) {
        Blog blog = blogs.get(position);
        // Decrement the like count locally
        blog.decrementLike();
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

        // Perform additional actions, e.g., update the server
        updateServerincLike(blog.getId());
    }

    private void incrementDislike(int position) {
        Blog blog = blogs.get(position);
        // Increment the dislike count locally
        blog.incrementDislike();
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

        // Perform additional actions, e.g., update the server
        updateServerDislike(blog.getId());
    }

    private void decrementDislike(int position) {
        Blog blog = blogs.get(position);
        // Decrement the dislike count locally
        blog.decrementDislike();
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

        // Perform additional actions, e.g., update the server
        updateServerincDislike(blog.getId());
    }

    private void updateServerLike(int blogId) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService blogApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the like count on the server
        Call<Void> call = blogApi.incrementLike(blogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.e("MyAdapter", "Error updating like on server");
                } else {
                    // Successfully updated like on the server
                    Log.d("MyAdapter", "Like updated successfully on server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("MyAdapter", "Failed to update like on server", t);
            }
        });
    }
    private void updateServerincLike(int blogId) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService blogApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the like count on the server
        Call<Void> call = blogApi.decrementLike(blogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.e("MyAdapter", "Error updating like on server");
                } else {
                    // Successfully updated like on the server
                    Log.d("MyAdapter", "Like updated successfully on server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("MyAdapter", "Failed to update like on server", t);
            }
        });
    }
    private void updateServerDislike(int blogId) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService blogApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the dislike count on the server
        Call<Void> call = blogApi.incrementDislike(blogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.e("MyAdapter", "Error updating dislike on server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("MyAdapter", "Failed to update dislike on server", t);
            }
        });
    }
    private void updateServerincDislike(int blogId) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService blogApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the dislike count on the server
        Call<Void> call = blogApi.decrementDislike(blogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.e("MyAdapter", "Error updating dislike on server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("MyAdapter", "Failed to update dislike on server", t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView, descriptionView, dateView, likeView, dislikeView;
        ImageButton likeButton, dislikeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            nameView = itemView.findViewById(R.id.name);
            dateView = itemView.findViewById(R.id.date);
            descriptionView = itemView.findViewById(R.id.description);
            likeView = itemView.findViewById(R.id.nblike);
            dislikeView = itemView.findViewById(R.id.nbdislike);
            likeButton = itemView.findViewById(R.id.like);
            dislikeButton = itemView.findViewById(R.id.dislike);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewOnItemClick.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}