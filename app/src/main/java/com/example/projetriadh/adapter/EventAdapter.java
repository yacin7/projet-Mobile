package com.example.projetriadh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetriadh.Constants;
import com.example.projetriadh.DetailEvent;
import com.example.projetriadh.GitHubService;
import com.example.projetriadh.R;
import com.example.projetriadh.model.Event;

import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.RecentViewHolder>{

    Context context;
    List<Event> EventsList ;
    private boolean incrementState = true;
    private String url = Constants.BASE_URL;



    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        EventsList = eventList;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_row_item,parent, false);
        return new RecentViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Event_name.setText(EventsList.get(position).getTitle());
        String placeName = EventsList.get(position).getPlaceName();
        String date = String.valueOf(holder.date);

        Event event = EventsList.get(position);

         String imageURL = EventsList.get(position).getImageURL();
        Glide.with(holder.itemView.getContext()).load(imageURL).into(holder.place_image);
        //Glide.with(context).load(EventsList.get(position).getImageURL()).placeholder(R.drawable.test4).error(R.drawable.test4).into(holder.prodImage);
        //Glide.with(context).load(productsList.get(position).getImageurl()).into(holder.prodImage);
        final Event Event = EventsList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailEvent.class);
                intent.putExtra("object",  EventsList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }
    /*
    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        holder.Event_name.setText(EventsList.get(position).getTitle());
        holder.place.setText(EventsList.get(position).getPlaceName());
        holder.date.setText((CharSequence) EventsList.get(position).getDateEvent());
        holder.place_image.setImageResource(EventsList.get(position).getImageURL());
    }
    */

    @Override
    public int getItemCount() {
        return EventsList.size();
    }

    public static final class RecentViewHolder extends RecyclerView.ViewHolder{
        ImageView place_image;
        TextView Event_name,place,date;

        public RecentViewHolder(@NonNull View itemView){
            super(itemView);

            place_image = itemView.findViewById(R.id.place_image);
            Event_name = itemView.findViewById(R.id.event_name);
            place = itemView.findViewById(R.id.place);
            date = itemView.findViewById(R.id.date);

        }


    }

}
