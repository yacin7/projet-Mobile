package com.example.interfaceblog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private ImageView imageView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Activer la barre d'action par défaut
        //1 - Configuring Toolbar
        this.configureToolbar();
        // Récupérer les vues pour afficher les détails de l'élément
        nameTextView = findViewById(R.id.namedt);
        descriptionTextView = findViewById(R.id.descriptiondt);
        dateTextView = findViewById(R.id.datedt);
        imageView = findViewById(R.id.imageView3);

        // Récupérer les détails de l'élément depuis l'intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("name");
        String itemDescription = intent.getStringExtra("description");
        String itemDate = intent.getStringExtra("date");
        int itemImage = intent.getIntExtra("imageview", 0);

        // Afficher les détails de l'élément dans les vues correspondantes
        nameTextView.setText(itemName);
        descriptionTextView.setText(itemDescription);
        dateTextView.setText(itemDate);
        imageView.setImageResource(itemImage);
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
}