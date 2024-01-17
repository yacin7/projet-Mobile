package tn.wassim.gestionuser;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import Classes.User;
import okhttp3.MultipartBody;
import retrofit2.Callback;
import retrofit2.Response;
import model.UserService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class UserProfile extends AppCompatActivity {
    Button disconnectButton, updateProfileButton;
    EditText usernameEditText, emailEditText, heightEditText, weightEditText;
    DatePicker dateOfBirthDatePicker;
    byte[] byteArray;
    private static final int CAMERA_REQUEST_CODE = 1;
    User user;
    ImageView userImageView;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        disconnectButton = findViewById(R.id.disconnectButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        dateOfBirthDatePicker = findViewById(R.id.dateOfBirthDatePicker);
        userImageView = findViewById(R.id.userImage);
        updateProfileButton = findViewById(R.id.updateProfileButton);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:// 192.168.1.3:3000")
                .build();
        userService = retrofit.create(UserService.class);

        Intent intent = getIntent();
        if (intent != null) {
            User user = getIntent().getParcelableExtra("user");
            Log.e("USER", user.toString());
            this.user = user;
            if (user != null) {
                byteArray= user.getProfilePicture();
                if (byteArray != null) {
                    Bitmap profilePictureBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    userImageView.setImageBitmap(profilePictureBitmap);
                }

                userImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            // Check if there is a camera app available
                            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                // Start the camera activity
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                            } else {
                                Toast.makeText(UserProfile.this, "No camera app found", Toast.LENGTH_SHORT).show();
                            }
                        }
                });
                // Set the data to EditText elements
                usernameEditText.setText(user.getUsername());
                emailEditText.setText(user.getEmail());
                heightEditText.setText(String.valueOf(user.getHeight()));
                weightEditText.setText(String.valueOf(user.getWeight()));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                try {
                    Date birthdateDate = sdf.parse(user.getBirthdate());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(birthdateDate);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    // Set the initial date for the DatePicker
                    dateOfBirthDatePicker.updateDate(year, month, day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the updated data from EditText elements
                String updatedUsername = usernameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();
                float updatedHeight = Float.parseFloat(heightEditText.getText().toString());
                float updatedWeight = Float.parseFloat(weightEditText.getText().toString());
                int year = dateOfBirthDatePicker.getYear();
                int month = dateOfBirthDatePicker.getMonth();
                int day = dateOfBirthDatePicker.getDayOfMonth();
                String updatedDateOfBirth = String.format(Locale.getDefault(), "%04d/%02d/%02d", year, month + 1, day);

                User updatedUser = new User();
                updatedUser.setUsername(updatedUsername);
                updatedUser.setEmail(updatedEmail);
                updatedUser.setHeight(updatedHeight);
                updatedUser.setWeight(updatedWeight);
                updatedUser.setBirthdate(updatedDateOfBirth);
                Log.e("UPDATED",updatedUser+toString());

                // Perform the logic to update the user profile on the server
                updateUserProfile(updatedUser);
            }
        });
    }

    private void updateUserProfile(User updatedUser) {
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), byteArray);
        MultipartBody.Part profilePicturePart = MultipartBody.Part.createFormData("profilePicture", "image.jpg", imageBody);

        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), updatedUser.getUsername());
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), updatedUser.getEmail());
        RequestBody heightBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(updatedUser.getHeight()));
        RequestBody weightBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(updatedUser.getWeight()));
        String birthdateWithoutTimestamp = updatedUser.getBirthdate().split(" ")[0];
        RequestBody birthdateBody = RequestBody.create(MediaType.parse("text/plain"), birthdateWithoutTimestamp);

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(this.user.getId()));

        Call<ResponseBody> call = userService.updateProfile(usernameBody, birthdateBody,emailBody, heightBody, weightBody, profilePicturePart,userId);

        // Enqueue the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    Toast.makeText(UserProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Error",response.body().toString());
                    Toast.makeText(UserProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Toast.makeText(UserProfile.this, "Failed to update profile: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap profileImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            userImageView.setImageBitmap(profileImage);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            profileImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
        }
    }

}
