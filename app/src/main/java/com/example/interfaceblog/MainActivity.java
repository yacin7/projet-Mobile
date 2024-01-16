package com.example.interfaceblog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class MainActivity extends AppCompatActivity implements RecycleViewOnItemClick, SensorEventListener {

    private List<Blog> blogList = new ArrayList<>();
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private String url = Constants.BASE_URL;
    private user currentUser;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    // Variables pour la gestion du défilement
    private float currentY = 0;
    private static final int SCROLL_THRESHOLD = 10; // Ajustez selon votre sensibilité

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

        // Configurer le capteur d'accéléromètre
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e("MainActivity", "Accelerometer not supported on this device");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float y = event.values[1];

            // Vérifier le mouvement vertical de la main
            if (Math.abs(y - currentY) > SCROLL_THRESHOLD) {
                // Si le mouvement est vers le haut, défilez vers le haut
                if (y < currentY) {
                    recyclerView.smoothScrollBy(0, -1150); // Ajustez la valeur selon vos besoins
                }
                // Si le mouvement est vers le bas, défilez vers le bas
                else {
                    recyclerView.smoothScrollBy(0, 1150); // Ajustez la valeur selon vos besoins
                }

                // Mettez à jour la position actuelle
                currentY = y;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Vous pouvez ignorer cela pour l'exemple
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
        intent.putExtra("like", blog.getLike());
        intent.putExtra("dislike", blog.getDislike());

        startActivity(intent);
    }

}

