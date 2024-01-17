package com.example.projetriadh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projetriadh.model.Event;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailEvent extends AppCompatActivity {
    private TextView Description;
    private Button participe;
    private ImageView Image,arrowback, arrow;
    private Event object;
    private Integer nbrparticipe;
    List<Event> EventsList ;
    private boolean incrementState = true;
    private String url = Constants.BASE_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        initView();
        getBundle();
    }

    private void initView() {
        Description=findViewById(R.id.Description);
        participe=findViewById(R.id.participe);
        Image=findViewById(R.id.Image);
        arrowback=findViewById(R.id.arrowback);

    }







    private void toggleParticipe(int position) {
        Event blog = EventsList.get(position);

        if (incrementState) {
            // Increment the participation count
            incrementParticipe(position);
        }

        // Invert the increment state for the next toggle
        incrementState = !incrementState;
    }
    private void incrementParticipe(int position) {
        Event event = EventsList.get(position);
        // Increment the participe count locally
        event.incrementParticipe();
        // Notify the adapter that the data has changed


        // Perform additional actions, e.g., update the server
        updateServerParticipe(event.getEventID());
    }



    private void updateServerParticipe(int eventID) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService eventApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the like count on the server
        Call call = eventApi.incrementParticipe(eventID);
    }







    private void getBundle() {
        object=(Event) getIntent().getSerializableExtra("object");
      /*  int drawableResourceId=this.getResources().getIdentifier(object.getImageurl(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picproduit);*/
        Glide.with(this)
                .load(object.getImageURL())
                .placeholder(R.drawable.homme1) // Image de remplacement en cas de chargement
               // .error(R.drawable.error) // Image de remplacement en cas d'erreur de chargement
                .into(Image);

        Description.setText(object.getDescription());


        participe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nbrparticipe=nbrparticipe+1;

            }
        });


    }


}



