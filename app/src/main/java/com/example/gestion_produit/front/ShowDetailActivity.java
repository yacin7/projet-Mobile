package com.example.gestion_produit.front;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gestion_produit.R;
import com.example.gestion_produit.helper.ManagmentCart;
import com.example.gestion_produit.model.Products;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
    private ImageView plusBtn,minusBtn,picproduit,arrowback;
    private Products object;
    int numberOrders=1;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managmentCart=new ManagmentCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        object=(Products) getIntent().getSerializableExtra("object");
      /*  int drawableResourceId=this.getResources().getIdentifier(object.getImageurl(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picproduit);*/
        Glide.with(this)
                .load(object.getImageurl())
                .placeholder(R.drawable.placeholder) // Image de remplacement en cas de chargement
                .error(R.drawable.error) // Image de remplacement en cas d'erreur de chargement
                .into(picproduit);

        titleTxt.setText(object.getProductName());
        feeTxt.setText(object.getProductPrice()+"DT");
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrders));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrders=numberOrders+1;
                numberOrderTxt.setText(String.valueOf(numberOrders));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (numberOrders > 1) {
                  numberOrders=numberOrders - 1;

              }
              numberOrderTxt.setText(String.valueOf(numberOrders));
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberinCart(numberOrders);
                managmentCart.insertProduit(object);
            }
        });
        arrowback.setOnClickListener(v -> finish());
    }

    private void initView() {
        addToCartBtn=findViewById(R.id.addToCartBtn);
        titleTxt=findViewById(R.id.titleTxtt);
        feeTxt=findViewById(R.id.priceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        numberOrderTxt=findViewById(R.id.numberOrderTxt);
        plusBtn=findViewById(R.id.plusBtn);
        minusBtn=findViewById(R.id.minusBtn);
        picproduit=findViewById(R.id.picproduit);
        arrowback=findViewById(R.id.arrowback);

    }
}
