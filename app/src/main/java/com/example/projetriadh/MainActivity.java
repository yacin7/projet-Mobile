package com.example.projetriadh;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetriadh.adapter.EventAdapter;
import com.example.projetriadh.adapter.TopEventsAdapter;
import com.example.projetriadh.model.Event;
import com.example.projetriadh.model.TopEvents;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView EventRecycler;
    RecyclerView TopEventsRecycler;

    EventAdapter eventAdapter;
    TopEventsAdapter topEventsAdapter;

    private String url = Constants.BASE_URL;
    private List<Event> eventsList = new ArrayList<>();
    private List<TopEvents> topEventsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService blogApi = retrofit.create(GitHubService.class);

        // RecyclerView setup
        EventRecycler = findViewById(R.id.recent_recycler);
        TopEventsRecycler = findViewById(R.id.TopEventsRecycler);

        // API call
        Call<List<Event>> call = blogApi.getAll();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    eventsList = response.body();
                    loadImages(eventsList);
                    eventAdapter = new EventAdapter(getApplicationContext(), eventsList);
                    EventRecycler.setAdapter(eventAdapter);
                    setRecyclerView(eventsList);
                } else {
                    // Handle API error
                    Log.e("MainActivity", "Error retrieving events from API");
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Handle network failure
                Log.e("MainActivity", "Failed to retrieve events from API", t);
                Toast.makeText(MainActivity.this, "Network failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImages(List<Event> eventsList) {
        for (Event event : eventsList) {
            String imageUrl = event.getImageURL();
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    event.setBitmap(bitmap);
                    eventAdapter.notifyDataSetChanged();
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.e("MainActivity", "Failed to load image: " + e.getMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    // Optional: You can display a placeholder while loading the image
                }
            };
            Picasso.get().load(imageUrl).into(target);
        }
    }

    private void setRecyclerView(List<Event> eventsList) {
        // Horizontal RecyclerView setup
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        EventRecycler.setLayoutManager(eventLayoutManager);
        eventAdapter = new EventAdapter(this, eventsList);
        EventRecycler.setAdapter(eventAdapter);

        // Vertical RecyclerView setup
        RecyclerView.LayoutManager topEventsLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        TopEventsRecycler.setLayoutManager(topEventsLayoutManager);
        topEventsAdapter = new TopEventsAdapter(this, topEventsList);
        TopEventsRecycler.setAdapter(topEventsAdapter);
    }
}
