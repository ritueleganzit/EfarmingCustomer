package com.eleganzit.e_farmingcustomer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView txt_signup,txt_forgot;
    Button btn_login;
    EditText ed_email,ed_password;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSessionManager=new UserSessionManager(this);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        txt_signup=findViewById(R.id.txt_signup);
        txt_forgot=findViewById(R.id.txt_forgot);
        ed_email=findViewById(R.id.ed_email);
        ed_password=findViewById(R.id.ed_password);
        btn_login=findViewById(R.id.btn_login);

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {
                    startActivity(new Intent(LoginActivity.this,NavHomeActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        });
    }

/*

    private void loginUser() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginRespose> call = myInterface.loginUser(ed_email.getText().toString(), ed_pw.getText().toString());
        call.enqueue(new Callback<LoginRespose>() {
            @Override
            public void onResponse(Call<LoginRespose> call, Response<LoginRespose> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String email, id, username, photo;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                email = response.body().getData().get(i).getVendorEmail();
                                id = response.body().getData().get(i).getVendorId();
                                username = response.body().getData().get(i).getVendorName();
                                photo = response.body().getData().get(i).getVendorStartTime();
                                userSessionManager.createLoginSession(id, email, ed_password.getText().toString(), username, photo);

                            }

                        }
                    } else {

                        Toast.makeText(LoginActivity.this, "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<LoginRespose> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

*/

    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (!matcher.matches()) {
            ed_email.setError("Please enter valid email");

            ed_email.requestFocus();
            return false;
        }
        else  if (ed_password.getText().toString().equals("") || ed_password.getText().toString().length()<6) {

            ed_password.setError("Password must contain atleast 6 characters");

            ed_password.requestFocus();

            return false;
        }


        return true;
    }
}
