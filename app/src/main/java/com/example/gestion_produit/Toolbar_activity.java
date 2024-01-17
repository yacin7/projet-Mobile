package com.example.gestion_produit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_produit.front.CartActivity;
import com.example.gestion_produit.user.UserProfile;

public class Toolbar_activity extends AppCompatActivity {
    ImageView profil,cart,home;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        profil=findViewById(R.id.profilhome);
        cart=findViewById(R.id.cartbtnhome);
        home=findViewById(R.id.home);

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toolbar_activity.this, UserProfile.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toolbar_activity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toolbar_activity.this, page_acceuil.class);
                startActivity(intent);
            }
        });


    }
}
