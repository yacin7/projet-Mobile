package com.example.gestion_produit.front;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gestion_produit.R;
import com.example.gestion_produit.front.Constants;
import com.example.gestion_produit.front.GitHubService;

import com.example.gestion_produit.model.Blog;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBlog extends AppCompatActivity {

    private EditText editTextParagraph;
    private GitHubService apiService;
    private String url = Constants.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        //1 - Configuring Toolbar
        this.configureToolbar();

        editTextParagraph = findViewById(R.id.editTextText);
        editTextParagraph.requestFocus();

        // Créez une instance de l'interface ApiService en utilisant Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(GitHubService.class);

        // Ajoutez un écouteur sur un bouton ou effectuez une autre action pour ajouter la description
        ImageButton addButton = findViewById(R.id.addDescription);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editTextParagraph.getText().toString();
                addDescription(description);

                // Lancer l'activité MainActivity
                Intent intent = new Intent(AddBlog.this, MainActivity.class);
                startActivity(intent);
                finish(); // Terminer l'activité actuelle pour revenir en arrière
            }
        });
    }
    private void configureToolbar(){
        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
    private void addDescription(String description) {
        String username = "Yassin Ferchichi"; // Remplacez par le nom d'utilisateur réel
        // Obtenez la date et l'heure actuelles
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        Blog request = new Blog();
        request.setName(username);
        request.setDescription(description);
        request.setDate(date);
        request.setLike(0);
        request.setDislike(0);

        Call<AddDescriptionResponse> call = apiService.addDescription(request);
        call.enqueue(new Callback<AddDescriptionResponse>() {
            @Override
            public void onResponse(Call<AddDescriptionResponse> call, Response<AddDescriptionResponse> response) {
                if (response.isSuccessful()) {
                    AddDescriptionResponse addDescriptionResponse = response.body();
                    String message = addDescriptionResponse.getMessage();
                    // Traitez la réponse de l'API ici
                    Toast.makeText(AddBlog.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    // Gérez les erreurs de l'API ici
                    Toast.makeText(AddBlog.this, "Erreur lors de l'ajout de la description", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddDescriptionResponse> call, Throwable t) {
                // Gérez les erreurs de réseau ici
                Toast.makeText(AddBlog.this, "Erreur de réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}