
package com.example.myapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapi.models.JsonResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait...");

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent votingIntent = new Intent(LoginActivity.this, VotingActivity.class);
                startActivity(votingIntent);
            }
        });
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValidate();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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


    private Toolbar toolbar;
    private TextInputEditText tedtEmail;
    private TextInputEditText tedtPassword;
    private Button btnLogin;
    private TextView txtRegister;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tedtEmail = (TextInputEditText) findViewById(R.id.tedt_email);
        tedtPassword = (TextInputEditText) findViewById(R.id.tedt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}