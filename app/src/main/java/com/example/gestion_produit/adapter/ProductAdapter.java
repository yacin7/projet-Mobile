package com.example.gestion_produit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestion_produit.front.Constants;
import com.example.gestion_produit.front.GitHubService;
import com.example.gestion_produit.R;
import com.example.gestion_produit.front.ShowDetailActivity;
import com.example.gestion_produit.model.Products;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Products> productsList;
    private String url = Constants.BASE_URL;
    private boolean incrementState = true;

    public ProductAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View inflate = LayoutInflater.from(context).inflate(R.layout.products_row_item,parent,false);
context=parent.getContext();
        return new ProductViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder,  @SuppressLint("RecyclerView") int position) {

        Products product = productsList.get(position);

        // Charger et afficher l'image du produit avec Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImageurl())
                .placeholder(R.drawable.placeholder) // Image de remplacement en cas de chargement
                .error(R.drawable.error) // Image de remplacement en cas d'erreur de chargement
                .into(holder.prodImage);

        holder.prodName.setText(product.getProductName());
        holder.prodPrice.setText(String.valueOf(product.getProductPrice()) + "DT");
        holder.likeView.setText(String.valueOf(product.getLike()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object",  productsList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(position);
            }
        });

    }

    private void toggleLike(int position) {
        Products blog = productsList.get(position);

        if (incrementState) {
            // Increment the like count
            incrementLike(position);
        } else {
            // Decrement the like count
            decrementLike(position);
        }

        // Invert the increment state for the next toggle
        incrementState = !incrementState;
    }
    private void incrementLike(int position) {
        Products products = productsList.get(position);
        // Increment the like count locally
        products.incrementLike();
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

        // Perform additional actions, e.g., update the server
        updateServerLike(products.getProductId());
    }

    private void decrementLike(int position) {
        Products products = productsList.get(position);
        // Decrement the like count locally
        products.decrementLike();
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

        // Perform additional actions, e.g., update the server
        updateServerincLike(products.getProductId());
    }
    private void updateServerLike(int produitid) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService produitApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the like count on the server
        Call<Void> call = produitApi.incrementLike(produitid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.e("MyAdapter", "Error updating like on server");
                } else {
                    // Successfully updated like on the server
                    Log.d("MyAdapter", "Like updated successfully on server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("MyAdapter", "Failed to update like on server", t);
            }
        });
    }
    private void updateServerincLike(int produitId) {
        // Initialize Retrofit and GitHubService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService produitApi = retrofit.create(GitHubService.class);

        // Call Retrofit to update the like count on the server
        Call<Void> call = produitApi.decrementLike(produitId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.e("MyAdapter", "Error updating like on server");
                } else {
                    // Successfully updated like on the server
                    Log.d("MyAdapter", "Like updated successfully on server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("MyAdapter", "Failed to update like on server", t);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public static final class ProductViewHolder extends RecyclerView.ViewHolder{
ImageView prodImage;
TextView prodName,prodPrice,likeView;
TextView addBtn;
ImageView likeButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage=itemView.findViewById(R.id.picproduit);
            prodName=itemView.findViewById(R.id.titleTxtt);
            prodPrice=itemView.findViewById(R.id.prod_price);
            addBtn=itemView.findViewById(R.id.addToCartBtn);
            likeButton = itemView.findViewById(R.id.like);
            likeView = itemView.findViewById(R.id.nblike);

        }
    }
}
