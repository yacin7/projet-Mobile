package tn.wassim.gestionuser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

import model.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPassword extends AppCompatActivity {
    UserService userService;
    String userEmail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:// 192.168.1.3:3000")
                .build();
        userService = retrofit.create(UserService.class);

        Button submitButton = findViewById(R.id.submitBtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificationCode = generateVerificationCode();
                EditText userEmailField = (EditText) findViewById(R.id.emailEditText);
                userEmail = userEmailField.getText().toString();
                showVerificationCodePrompt(verificationCode);
                displayCodeInConsole(verificationCode);
            }
        });
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 10000 + random.nextInt(90000);
        return String.valueOf(code);
    }

    private void displayCodeInConsole(String code) {
        System.out.println("Received code in ForgetPassword activity: " + code);

        Call<ResponseBody> call = userService.sendCode(userEmail,code);

        // Enqueue the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    Toast.makeText(ForgetPassword.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failure
                    System.out.println(response.toString());
                    Toast.makeText(ForgetPassword.this, "Email Failed to send", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Toast.makeText(ForgetPassword.this, "Email Failed to send: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showVerificationCodePrompt(String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verification Code");
        builder.setMessage("Please enter the verification code sent to your email:");

        final EditText verificationCodeInput = new EditText(this);
        builder.setView(verificationCodeInput);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String enteredCode = verificationCodeInput.getText().toString().trim();
            if (enteredCode.equals(code)) {
                showNewPasswordPrompt();
            } else {
                Toast.makeText(ForgetPassword.this, "Incorrect verification code", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showNewPasswordPrompt() {
        AlertDialog.Builder newPasswordBuilder = new AlertDialog.Builder(this);
        newPasswordBuilder.setTitle("New Password");
        newPasswordBuilder.setMessage("Please enter the verification code and your new password:");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText newPasswordInput = new EditText(this);
        newPasswordInput.setHint("New Password");
        layout.addView(newPasswordInput);

        newPasswordBuilder.setView(layout);

        newPasswordBuilder.setPositiveButton("Submit", (dialog, which) -> {
            String newPassword = newPasswordInput.getText().toString().trim();
            updatePassword(userEmail, newPassword);
        });

        newPasswordBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        newPasswordBuilder.show();
    }

    private void updatePassword(String email, String newPassword) {

        // Call the API to update the password
        Call<ResponseBody> call = userService.updatePassword(email, newPassword);

        // Enqueue the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    Toast.makeText(ForgetPassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println(response.body());
                    // Handle failure
                    Toast.makeText(ForgetPassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Toast.makeText(ForgetPassword.this, "Failed to update password: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}