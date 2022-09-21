package com.example.lok_lagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    Button Customer,Provider;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Customer = findViewById(R.id.customerId);
        Provider = findViewById(R.id.providerId);

      Customer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
              final String login1 = sharedPreferences.getString("LOGIN", null);

              if (login1 != null) {
                  Intent intent = new Intent(MainActivity.this,CustomerLocation.class);
                  startActivity(intent);
                  finish();
                  //put your code if user is logged. For example, go to another activity
              }
              else {
                  Intent intent = new Intent(MainActivity.this,CustomerLogin.class);
                  startActivity(intent);

              }
          }
      });

        Provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProviderLogin.class);
                startActivity(intent);
            }
        });



    }


}