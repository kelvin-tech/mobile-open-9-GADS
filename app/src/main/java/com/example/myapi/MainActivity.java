package com.example.myapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapi.models.JsonResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit.MyAPI;
import retrofit.MyClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait...");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValidate();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });


    }

    String fname, lname, email, phone, password, password1;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void mValidate() {

        fname = tedtFirstName.getText().toString().trim();
        lname = tedtLastName.getText().toString().trim();
        email = tedtEmail.getText().toString().trim();
        phone = tedtPhone.getText().toString().trim();
        password = tedtPassword.getText().toString().trim();
        password1 = tedtPassword1.getText().toString().trim();

        if (fname.isEmpty()) {
            tedtFirstName.setError("Enter First Name");
            tedtFirstName.requestFocus();
            return;
        }
        if (lname.isEmpty()) {
            tedtLastName.setError("Enter Last Name");
            tedtLastName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            tedtEmail.setError("Enter Email");
            tedtEmail.requestFocus();
            return;
        }if (password1.isEmpty()) {
            tedtPassword1.setError("Enter password");
            tedtPassword1.requestFocus();
            return;
        }if (password.isEmpty()) {
            tedtPassword.setError("Repeat password");
            tedtPassword.requestFocus();
            return;
        }if (! password.equalsIgnoreCase(password1)) {
            tedtPassword.setError("Passwords Don't Match");
            tedtPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            tedtPhone.setError("Enter Phone");
            tedtPhone.requestFocus();
            return;
        } else {
            mRegister();
        }
    }

    private void mRegister() {
        dialog.show();
        Retrofit retrofit = MyClient.getRetrofitClient();
        MyAPI myAPI = retrofit.create(MyAPI.class);

        Call<JsonResponse> jsonResponseCall = myAPI.mRegister(fname, lname, email, phone, password);
        jsonResponseCall.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                dialog.dismiss();
                if (response.body() !=null){
                    Toast.makeText(MainActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private TextInputEditText tedtFirstName;
    private TextInputEditText tedtLastName;
    private TextInputEditText tedtEmail;
    private TextInputEditText tedtPhone;
    private Button btnRegister;
    private TextView txtLogin;
    private TextInputEditText tedtPassword;
    private TextInputEditText tedtPassword1;

    public void initViews() {
        tedtFirstName = (TextInputEditText) findViewById(R.id.tedt_first_name);
        tedtLastName = (TextInputEditText) findViewById(R.id.tedt_last_name);
        tedtEmail = (TextInputEditText) findViewById(R.id.tedt_email);
        tedtPhone = (TextInputEditText) findViewById(R.id.tedt_phone);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtLogin = (TextView) findViewById(R.id.txtlogin);
        tedtPassword = (TextInputEditText) findViewById(R.id.tedt_password);
        tedtPassword1 = (TextInputEditText) findViewById(R.id.tedt_password1);
    }

}
