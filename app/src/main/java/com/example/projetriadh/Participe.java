package com.example.projetriadh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;

import com.example.projetriadh.model.Event;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Participe extends AppCompatActivity {
    private boolean incrementState = true;
    private Button participe;
    private String url = Constants.BASE_URL;
    Context context;
    List<Event> EventsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participe);
    }




}