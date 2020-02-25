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

public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private Button customerLoginBtn, customerRegisterBtn;
    private EditText emailCustomer, passwordCustomer;
    private TextView customerRegisterLink;

    // firebase
    private FirebaseAuth auth;

    // progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        // view
        customerLoginBtn = findViewById(R.id.customer_login_btn);
        customerRegisterBtn = findViewById(R.id.customer_register_btn);
        emailCustomer = findViewById(R.id.email_customer);
        passwordCustomer = findViewById(R.id.password_customer);
        customerRegisterLink = findViewById(R.id.do_not_have_account_customer);

        customerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerLoginBtn.setVisibility(View.INVISIBLE);
                customerRegisterLink.setVisibility(View.INVISIBLE);
                customerRegisterBtn.setVisibility(View.VISIBLE);
                customerRegisterBtn.setEnabled(true);
            }
        });

        customerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailCustomer.getText().toString();
                String password = passwordCustomer.getText().toString();

                registerCustomer(email, password);
            }
        });
        customerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailCustomer.getText().toString();
                String password = passwordCustomer.getText().toString();

                loginCustomer(email, password);

            }
        });

    }

    private void loginCustomer(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {  // TODo : MAKE PASSWORD HAVE A 6 NUMBER
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Customer Login");
            progressDialog.setMessage("Please wait, while we are checking your data ...");
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomerMapActivity.class);

                                startActivity(intent);
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Login unSuccessfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

        }
    }

    private void registerCustomer(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {  // TOOD : MAKE PASSWORD HAVE A 6 NUMBER
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
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Driver Register Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Registration UnSuccessful please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}