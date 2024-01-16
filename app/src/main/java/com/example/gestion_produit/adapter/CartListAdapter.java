package com.example.gestion_produit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestion_produit.R;
import com.example.gestion_produit.helper.ChangeNumberitemsListener;
import com.example.gestion_produit.helper.ManagmentCart;
import com.example.gestion_produit.model.Products;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<Products> listItemselected;
    private ManagmentCart managmentCart;
    ChangeNumberitemsListener changeNumberitemsListener;

    public CartListAdapter(ArrayList<Products> listItemselected,Context  context, ChangeNumberitemsListener changeNumberitemsListener) {
        this.listItemselected = listItemselected;
        managmentCart = new ManagmentCart(context);
        this.changeNumberitemsListener = changeNumberitemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_activity,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.title.setText(listItemselected.get(position).getProductName());
    holder.feeEachItem.setText(String.valueOf(listItemselected.get(position).getProductPrice()));
    holder.totalEachItem.setText(String.valueOf(Math.round((listItemselected.get(position).getNumberinCart()* listItemselected.get(position).getProductPrice())*100)/100));
    holder.num.setText(String.valueOf(listItemselected.get(position).getNumberinCart()));

   /* int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(listItemselected.get(position).getImageurl(),"drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
              .load(drawableResourceId)
             .into(holder.pic);*/
        Glide.with(holder.itemView.getContext())
                .load(listItemselected.get(position).getImageurl())
                .placeholder(R.drawable.placeholder) // Image de remplacement en cas de chargement
                .error(R.drawable.error) // Image de remplacement en cas d'erreur de chargement
                .into(holder.pic);

        holder.plusItem.setOnClickListener(v->{
            managmentCart.pulsnumberitem(listItemselected, position, new ChangeNumberitemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberitemsListener.change();
                }
            });

        });
        holder.minusItem.setOnClickListener(v->{
            managmentCart.minuesNumberItem(listItemselected, position, new ChangeNumberitemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberitemsListener.change();
                }
            });

        });

    }

    @Override
    public int getItemCount() {
        return listItemselected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, feeEachItem;
        ImageView pic,plusItem,minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleTxtt);
            pic=itemView.findViewById(R.id.picCart);
            feeEachItem=itemView.findViewById(R.id.feeEachitem);
            totalEachItem=itemView.findViewById(R.id.totaleachitem);
            plusItem=itemView.findViewById(R.id.pluscartitem);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
            num=itemView.findViewById(R.id.numberitemTxt);

        }
    }
}
