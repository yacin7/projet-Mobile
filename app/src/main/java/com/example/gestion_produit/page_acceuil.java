package com.example.gestion_produit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.gestion_produit.front.AddBlog;
import com.example.gestion_produit.user.UserProfile;

public class page_acceuil extends AppCompatActivity {
ImageView profil,Blog,Cours,Produit,Event;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_acceuil);
        profil=findViewById(R.id.profill);
        Cours=findViewById(R.id.cours);
        Produit=findViewById(R.id.produit);
        Event=findViewById(R.id.Event);
        Blog=findViewById(R.id.blog);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page_acceuil.this, UserProfile.class);
                startActivity(intent);
            }
        });
        Produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page_acceuil.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page_acceuil.this, com.example.gestion_produit.front.MainActivity.class);
                startActivity(intent);
            }
        });
        Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page_acceuil.this, com.example.gestion_produit.event.MainActivity.class);
                startActivity(intent);
            }
        });

    }
}