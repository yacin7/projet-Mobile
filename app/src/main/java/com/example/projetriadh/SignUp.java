package com.example.projetriadh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SignUp extends AppCompatActivity {
    ImageView imageViewReturn2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        imageViewReturn2 =  findViewById(R.id.imageViewReturn2);
        imageViewReturn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OpenMain();
            }
        });


        button =  findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OpenEvents();
            }
        });

    }
    public void OpenMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void OpenEvents(){
        Intent intent = new Intent(this, Events.class);
        startActivity(intent);
    }

}