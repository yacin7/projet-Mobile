package com.example.projetriadh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OpenSignIn();
            }
        });


        Button buttonSignUp =  findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OpenSignUp();
            }
        });

    }
    public void OpenSignIn(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
    public void OpenSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}