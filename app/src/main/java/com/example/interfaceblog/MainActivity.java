package com.example.interfaceblog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.interfaceblog.database.AppDataBase;
import com.example.interfaceblog.entities.Blog;
import com.example.interfaceblog.entities.user;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecycleViewOnItemClick {
    private List<Item> items = new ArrayList<>();

    private ImageButton likeButton;
    private ImageButton dislikeButton;
    private TextView likeCountTextView;
    private TextView dislikeCountTextView;
    private int likeCount = 0;
    private int dislikeCount = 0;
    private AppDataBase dataBase;
    private MyAdapter adapter;
    private List<Blog> blogList = new ArrayList<Blog>();
    private GitHubService gitHubService;
    private RecyclerView recyclerView;
    private String url = Constants.BASE_URL;
    private user currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure Toolbar
        configureToolbar();
        int userId = 1; // ID de l'utilisateur connecté
        String username = "john_doe"; // Nom d'utilisateur de l'utilisateur connecté
        currentUser = new user(userId, username);

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService blogApi = retrofit.create(GitHubService.class);

        // Retrieve blogs from API and update RecyclerView
        Call<List<Blog>> call = blogApi.getAll();

        call.enqueue(new Callback<List<Blog>>() {
            @Override
            public void onResponse(Call<List<Blog>> call, Response<List<Blog>> response) {
                if (response.isSuccessful()) {
                    blogList = response.body();

                    // Create and set adapter for RecyclerView
                    adapter = new MyAdapter(getApplicationContext(), blogList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Handle API error
                    Log.e("MainActivity", "Error retrieving blogs from API");
                }
            }

            @Override
            public void onFailure(Call<List<Blog>> call, Throwable t) {
                // Handle network failure
                Log.e("MainActivity", "Failed to retrieve blogs from API", t);
            }
        });

        // Set up add blog button listener
        ImageButton addbtn = findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBlog.class);
                startActivity(intent);
            }
        });
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        // Set the Toolbar
        setSupportActionBar(toolbar);
    }

    @Override
    public void onItemClick(int position) {
        // Récupérer le blog sélectionné à partir de la position
        Blog blog = blogList.get(position);

        // Ouvrir l'activité de détail avec les informations du blog
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("name", blog.getName());
        intent.putExtra("description", blog.getDescription());
        intent.putExtra("date", blog.getDate());
        intent.putExtra("imageview", blog.getImage());
        startActivity(intent);
    }
}

