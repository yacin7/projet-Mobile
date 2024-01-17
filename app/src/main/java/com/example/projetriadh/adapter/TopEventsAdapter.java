package com.example.projetriadh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.projetriadh.R;
import com.example.projetriadh.model.Event;
import com.example.projetriadh.model.TopEvents;
import java.util.List;

public class TopEventsAdapter extends RecyclerView.Adapter<TopEventsAdapter.TopEventsViewHolder> {

    private Context context;
    private List<TopEvents> topEventsList;

    public TopEventsAdapter(Context context, List<TopEvents> topEventsList) {
        this.context = context;
        this.topEventsList = topEventsList;
    }

    @NonNull
    @Override
    public TopEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_events_row_item, parent, false);
        return new TopEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopEventsViewHolder holder, int position) {
        TopEvents currentEvent = topEventsList.get(position);

        holder.eventName.setText(currentEvent.getTitle());
        holder.place.setText(currentEvent.getPlaceName());
        holder.date.setText((CharSequence) currentEvent.getDateEvent());

        String imageUrl = currentEvent.getImageURL();
        Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.placeImage);
    }


    @Override
    public int getItemCount() {
        return topEventsList.size();
    }

    public static class TopEventsViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView eventName, place, date;

        public TopEventsViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            eventName = itemView.findViewById(R.id.place_name);
            place = itemView.findViewById(R.id.country_name);
            date = itemView.findViewById(R.id.price);
        }
    }
}
