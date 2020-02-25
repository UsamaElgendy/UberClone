package com.elgindy.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button customer_btn, driver_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        customer_btn = findViewById(R.id.welcome_customer_btn);
        driver_btn = findViewById(R.id.welcome_driver_btn);

        customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CustomerLoginRegisterActivity.class);
                startActivity(intent);
            }
        });


        driver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, DriverLoginRegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
