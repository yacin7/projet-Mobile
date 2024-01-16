package com.example.interfaceblog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView nblike;
    private TextView nbdislike;
    private TextView dateTextView;
    private ImageView imageView,like,dislike;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Récupérer les vues pour afficher les détails de l'élément
        nameTextView = findViewById(R.id.namedt);
        descriptionTextView = findViewById(R.id.descriptiondt);
        dateTextView = findViewById(R.id.datedt);
        imageView = findViewById(R.id.imageView3);
        nblike = findViewById(R.id.nblike2);
        nbdislike = findViewById(R.id.nbdislike2);

        // Récupérer les détails de l'élément depuis l'intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("name");
        String itemDescription = intent.getStringExtra("description");
        String itemDate = intent.getStringExtra("date");
        int itemImage = intent.getIntExtra("imageview", 0);
        int itemLike = intent.getIntExtra("like", 0);
        int itemDislike = intent.getIntExtra("dislike",0);

        // Afficher les détails de l'élément dans les vues correspondantes
        nameTextView.setText(itemName);
        descriptionTextView.setText(itemDescription);
        dateTextView.setText(itemDate);
        imageView.setImageResource(itemImage);
        //nblike.setText(String.valueOf(itemLike));
        //nbdislike.setText(String.valueOf(itemDislike));
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