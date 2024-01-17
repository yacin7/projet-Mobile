package com.example.gestion_produit.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_produit.R;
import com.example.gestion_produit.front.Constants;
import com.example.gestion_produit.front.GitHubService;
import com.example.gestion_produit.model.User;
import com.example.gestion_produit.page_acceuil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
public class SignInActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private Button buttonSignUp;
    private GitHubService userService;
    private String url = Constants.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button buttonForgetPassword = findViewById(R.id.buttonForgetPassword);

        buttonForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        userService = retrofit.create(GitHubService.class);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                signIn(username, password);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn(String username, String password) {
        Call<ResponseBody> call = userService.signIn(username, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject userObject = jsonObject.getJSONObject("user");
                        int id = userObject.getInt("id");
                        String username = userObject.getString("username");
                        String birthdate = userObject.getString("birthdate");
                        String email = userObject.getString("email");
                        double height = userObject.getDouble("height");
                        double weight = userObject.getDouble("weight");
                        JSONArray profilePictureDataArray = userObject.getJSONObject("profile_picture").getJSONArray("data");
                        byte[] profilePictureData = new byte[profilePictureDataArray.length()];
                        for (int i = 0; i < profilePictureDataArray.length(); i++) {
                            profilePictureData[i] = (byte) profilePictureDataArray.getInt(i);
                        }
                        User user = new User(id, username, birthdate, email ,height,weight,profilePictureData);
                        Intent intent = new Intent(SignInActivity.this, page_acceuil.class);
                        intent.putExtra("user", user);
                        Log.e("USER",user.toString());
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("TAG", "Error parsing response: ", e);
                        showToast("An error occurred");
                    }
                } else {
                    // Handle error
                    showToast("Invalid username or password");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                showToast("Sign-in failed: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
