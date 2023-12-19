package com.example.projetriadh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SignIn extends AppCompatActivity {
    ImageView imageViewReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        imageViewReturn =  findViewById(R.id.imageViewReturn);
        imageViewReturn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OpenMain();
            }
        });

    }
    public void OpenMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}