package com.hst.gads.electronicvotingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hst.gads.electronicvotingapp.models.JsonResponse;

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



    }


    public void onSignIn(View view) {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }


    String name, email, phone, password, password1;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void mValidate() {

        name = tedtName.getText().toString().trim();
        email = tedtEmail.getText().toString().trim();
        phone = tedtPhone.getText().toString().trim();
        password = tedtPassword.getText().toString().trim();
        password1 = tedtPassword1.getText().toString().trim();



        if (phone.isEmpty()) {
            tedtPhone.setError("Enter Phone");
            tedtPhone.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            tedtPhone.setError("Enter Phone");
            tedtPhone.requestFocus();
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
        else {

            mRegister();
        }
    }





    private void mRegister() {
        dialog.show();
        Retrofit retrofit = MyClient.getRetrofitClient();
        MyAPI myAPI = retrofit.create(MyAPI.class);

        Call<JsonResponse> jsonResponseCall = myAPI.mRegister(name, email, password, phone);
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



    private TextInputEditText tedtEmail;
    private TextInputEditText tedtPhone;
    private Button btnRegister;
//    private TextView txtLogin;
    private TextInputEditText tedtPassword;
    private TextInputEditText tedtPassword1;
    private TextInputEditText tedtName;

    public void initViews() {

        tedtEmail = (TextInputEditText) findViewById(R.id.email_edit_text);
        tedtPhone = (TextInputEditText) findViewById(R.id.phone_edit_text);
        btnRegister = (Button) findViewById(R.id.signup_button);
//        txtLogin = (TextView) findViewById(R.id.signin_button);
        tedtPassword = (TextInputEditText) findViewById(R.id.password_edit_text);
        tedtPassword1 = (TextInputEditText) findViewById(R.id.cnf_password_edit_text);
        tedtName = findViewById(R.id.fullname_input_edit_text);
    }

}
