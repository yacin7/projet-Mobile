package com.example.gestion_produit.front;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import com.example.gestion_produit.R;
import com.example.gestion_produit.adapter.CartListAdapter;
import com.example.gestion_produit.helper.ChangeNumberitemsListener;
import com.example.gestion_produit.helper.ManagmentCart;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class CartActivity extends AppCompatActivity {
    ImageView backbtn;
    ImageView ActivateButton;
    public static final String Error_Detected="No NFC Tag Detected";
    public static final String Write_Success="Text Written Successfully";
    public static final String Write_Error="Error Writing";

NfcAdapter nfcAdapter;
PendingIntent pendingIntent;
IntentFilter writingTagFillters[];
boolean writeMode;
Tag myTag;
   TextView numCard;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private TextView totalFeeTxt, taxTxt, deliverTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    TextView adress;
    ImageView getlocation;
    private final static int REQUEST_CODE=100;
    int MY_SCAN_REQUEST_CODE=111;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        managmentCart = new ManagmentCart(this);
        initView();
        initList();
        getBundle();
        calcualteCart();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastlocation();
            }
        });
        ActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScanPress();
            }
        });
    }
    public void onScanPress() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            numCard.setText(resultDisplayStr);
        }
        // else handle other activity results
    }
    private void buildTagViews(NdefMessage[] msgs)
    {
        if(msgs==null||msgs.length==0) return;
        String text="";
        byte[] payload=msgs[0].getRecords()[0].getPayload();
        String textEncoding= ((payload[0] & 128)==0) ? "UTF-8" : "UTF-16";
        int languageCodeLength =payload[0] & 0063;
        try {
            text =new String(payload,languageCodeLength+1,payload.length-languageCodeLength-1,textEncoding);

        }catch (UnsupportedEncodingException e)
        {
            Log.e("UnspportEncoding",e.toString());
        }
        numCard.setText("NFC Content"+text);
    }
 private void   getLastlocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location !=null){
                            Geocoder geocoder=new Geocoder(CartActivity.this , Locale.getDefault());

                            List<Address> addresses=null;
                            try {
                                addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                adress.setText(addresses.get(0).getAddressLine(0));

                            }catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        }
        else {
            askPermisson();
        }
 }
 private void askPermisson()
 {
     ActivityCompat.requestPermissions(CartActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

 }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if (requestCode== REQUEST_CODE){
           if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)

           {
               getLastlocation();
           }
           else {
               Toast.makeText(CartActivity.this, "Please provide the requierd permisson", Toast.LENGTH_SHORT).show();
           }
       }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initList()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new CartListAdapter(managmentCart.getListCart(),this, new ChangeNumberitemsListener() {
            @Override
            public void change() {
                calcualteCart();
            }
        });
        recyclerView.setAdapter(adapter);
        if(managmentCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
        else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    private void calcualteCart(){
        double percentTax=0.19;
        double delivery=8;
        tax=Math.round((managmentCart.getTotalFee()*percentTax)*100)/100;
        double total=Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal= Math.round(managmentCart.getTotalFee()*100)/100;
        totalFeeTxt.setText(itemTotal+"DT");
        taxTxt.setText(tax+"DT");
        deliverTxt.setText(delivery+"DT");
        totalTxt.setText(total+"DT");
    }
    public void initView()
    {
        recyclerView=findViewById(R.id.view3);
        backbtn=findViewById(R.id.backbtn2);
        totalFeeTxt= findViewById(R.id.totalFeetxt);
        deliverTxt= findViewById(R.id.deliveryTxt);
        totalTxt= findViewById(R.id.totalTxt);
        scrollView= findViewById(R.id.scrollView2);
        emptyTxt=findViewById(R.id.emptyTxt);
        taxTxt=findViewById(R.id.taxTxt);
        scrollView=findViewById(R.id.scrollView2);
        getlocation=findViewById(R.id.get_location_btn);
        adress=findViewById(R.id.adress);
        ActivateButton=findViewById(R.id.ScanCard);
        numCard=findViewById(R.id.numcard);
    }
    private void getBundle(){
        backbtn.setOnClickListener(v -> finish());}
}
