package com.hst.gads.electronicvotingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hst.gads.electronicvotingapp.models.JsonResponse;

import retrofit.MyAPI;
import retrofit.MyClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait...");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValidate();
            }
        });

    }

    public void onSignUp(View view){

        startActivity(new Intent(LoginActivity.this,MainActivity.class));

    }

    String email, pass;

    private void mValidate() {
        email  = tedtEmail.getText().toString().trim();
        pass  = tedtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            tedtEmail.setError("Enter Email");
            tedtEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            tedtPassword.setError("Enter Password");
            tedtPassword.requestFocus();
            return;
        } else {
            mLogin();
        }
    }
    private void mLogin() {

        dialog.show();
        Retrofit retrofit = MyClient.getRetrofitClient();
        MyAPI myAPI = retrofit.create(MyAPI.class);

        Call<JsonResponse> jsonResponseCall = myAPI.mLogin(email, pass);
        jsonResponseCall.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                dialog.dismiss();
                if (response.body() !=null){
                    JsonResponse r = response.body();

                    if (r.isSuccess()){
                        Intent votingIntent = new Intent(LoginActivity.this, VotingActivity.class);
                        votingIntent.putExtra("email", email);

                        startActivity(votingIntent);
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private TextInputEditText tedtEmail;
    private TextInputEditText tedtPassword;
    private Button btnLogin;


    public void initViews(){
        tedtEmail = findViewById(R.id.email_edit_text);
        tedtPassword = findViewById(R.id.password_edit_text);
        btnLogin = (Button) findViewById(R.id.signin_button);

    }
}