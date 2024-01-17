package com.example.gestion_produit.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_produit.R;
import com.example.gestion_produit.front.Constants;
import com.example.gestion_produit.front.GitHubService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    private GitHubService userService;
    private EditText editTextUsername, editTextPassword, editTextEmail, editTextHeight, editTextWeight;
    private DatePicker datePickerBirthdate;
    private ImageView imageViewProfilePicture;
    private static final int CAMERA_REQUEST_CODE = 1;
    private String url = Constants.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        datePickerBirthdate = findViewById(R.id.datePickerBirthdate);
        imageViewProfilePicture = findViewById(R.id.imageViewProfilePicture);
        Button signInButton = findViewById(R.id.btnSignIn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        userService = retrofit.create(GitHubService.class);
    }

    public void pickProfilePicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if there is a camera app available
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Start the camera activity
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap profileImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageViewProfilePicture.setImageBitmap(profileImage);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            profileImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
        }
    }

    byte[] byteArray;

    public void signUp(View view) throws IOException {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        float height = Float.parseFloat(editTextHeight.getText().toString().trim());
        float weight = Float.parseFloat(editTextWeight.getText().toString().trim());

        // Get selected date from DatePicker
        int year = datePickerBirthdate.getYear();
        int month = datePickerBirthdate.getMonth();
        int day = datePickerBirthdate.getDayOfMonth();

        // Format the date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String birthdate = sdf.format(calendar.getTime());

        if (byteArray != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), byteArray);
            MultipartBody.Part profilePicturePart = MultipartBody.Part.createFormData("profilePicture", "image.jpg", imageBody);

            Call<ResponseBody> call = userService.signUp(
                    RequestBody.create(MediaType.parse("text/plain"), username),
                    RequestBody.create(MediaType.parse("text/plain"), password),
                    RequestBody.create(MediaType.parse("text/plain"), birthdate),
                    RequestBody.create(MediaType.parse("text/plain"), email),
                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(height)),
                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(weight)),
                    profilePicturePart
            );
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Handle success
                        Toast.makeText(SignUpActivity.this, "Sign-up successful!", Toast.LENGTH_LONG).show();
                    } else {
                        // Handle error
                        Toast.makeText(SignUpActivity.this, "Sign-up failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Handle failure
                    Log.e("E", t.toString());
                    Toast.makeText(SignUpActivity.this, "Sign-up failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
