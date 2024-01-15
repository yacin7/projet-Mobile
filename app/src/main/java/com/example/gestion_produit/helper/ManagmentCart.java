package com.example.gestion_produit.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.gestion_produit.model.Products;
import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;
    public ManagmentCart(Context context)
    {
        this.context=context;
        this.tinyDB=new TinyDB(context);
    }
    public void insertProduit(Products item)
    {
        ArrayList<Products> ListPro=getListCart();
        boolean existAlready=false;
        int n=0;
        for (int i=0;i<ListPro.size();i++)
        {
            if(ListPro.get(i).getProductName().equals(item.getProductName())){
                existAlready= true;
                n=i; break;
            }
        }
        if(existAlready)
        {
            ListPro.get(n).setNumberinCart(item.getNumberinCart());
        }else {
            ListPro.add(item);
        }
        tinyDB.putListObject("CartList",ListPro);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Products> getListCart(){
return tinyDB.getListObject("CartList");
    }

    public void minuesNumberItem(ArrayList<Products> listitem,int position,ChangeNumberitemsListener changeNumberitemsListener)
    {
        if(listitem.get(position).getNumberinCart() == 1)
        {
            listitem.remove(position);
        }
        else {
            listitem.get(position).setNumberinCart(listitem.get(position).getNumberinCart() -1);
        }
        tinyDB.putListObject("CartList",listitem);
        changeNumberitemsListener.change();
    }
    public void pulsnumberitem(ArrayList<Products> listitem,int position,ChangeNumberitemsListener changeNumberitemsListener)
            {
              listitem.get(position).setNumberinCart(listitem.get(position).getNumberinCart()+1);
              tinyDB.putListObject("CartList",listitem);
              changeNumberitemsListener.change();
            }
     public Double getTotalFee()
     {
         ArrayList<Products> listitem= getListCart();
         double fee = 0;
         for(int i=0;i<listitem.size();i++)
         {
             fee=fee + (listitem.get(i).getProductPrice() * listitem.get(i).getNumberinCart());
         }
         return fee;
     }


}
