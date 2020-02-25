package com.elgindy.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegisterActivity extends AppCompatActivity {
    private Button loginDriverBtn, registrationDriverBtn;
    private EditText emailDriver, passwordDriver;
    private TextView registerLink;

    // firebase
    private FirebaseAuth auth;

    // progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        auth = FirebaseAuth.getInstance();

        loginDriverBtn = findViewById(R.id.driver_login_btn);


        progressDialog = new ProgressDialog(this);

        registrationDriverBtn = findViewById(R.id.driver_register_btn);
        emailDriver = findViewById(R.id.email_driver);
        passwordDriver = findViewById(R.id.password_driver);
        registerLink = findViewById(R.id.do_not_have_account_driver);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDriverBtn.setVisibility(View.INVISIBLE);
                registrationDriverBtn.setVisibility(View.VISIBLE);
                registerLink.setVisibility(View.INVISIBLE);
                registrationDriverBtn.setEnabled(true);
            }
        });


        registrationDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailDriver.getText().toString();
                String password = passwordDriver.getText().toString();

                registerDriver(email, password);
            }
        });

        loginDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailDriver.getText().toString();
                String password = passwordDriver.getText().toString();

                loginDriver(email, password);
            }
        });
    }

    private void loginDriver(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {  // TODo : MAKE PASSWORD HAVE A 6 NUMBER
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Driver Login");
            progressDialog.setMessage("Please wait, while we are checking your data ...");
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DriverLoginRegisterActivity.this, DriversMapActivity.class);

                                startActivity(intent);
                            } else {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Login unSuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


    private void registerDriver(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {  // TODo : MAKE PASSWORD HAVE A 6 NUMBER
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Driver Registration");
            progressDialog.setMessage("Please wait, it don't take a lot of time ...");
            progressDialog.show();


            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", task.isSuccessful() + "");
                            if (task.isSuccessful()) {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Customer Register Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(DriverLoginRegisterActivity.this, DriversMapActivity.class);

                                startActivity(intent);
                            } else {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Registration UnSuccessful please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}
