package com.example.lok_lagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Provider;

public class ProviderLogin extends AppCompatActivity {

    private Button login,loginmail,Continue;
    private EditText Mobile,signinemail,sininpass;
    private TextView textView;
    private LinearLayout a,b;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerlogin);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        login = (Button) findViewById(R.id.Login1id);
        loginmail = (Button) findViewById(R.id.loginWithEmail1ID);
        Continue = (Button) findViewById(R.id.signInLogin1Id);

        Mobile = (EditText) findViewById(R.id.P1Id);
        signinemail = (EditText) findViewById(R.id.signInEmail1Id);
        sininpass = (EditText) findViewById(R.id.signInPassword1Id);

        textView = (TextView) findViewById(R.id.notRegistered1Id);

        a= (LinearLayout) findViewById(R.id.li1d);
        b= (LinearLayout) findViewById(R.id.l1i2d);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderLogin.this, ProviderEmailSignin.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(v.VISIBLE);
//                String code = CountryData.countryCode[spinner.getSelectedItemPosition()];

                String number = Mobile.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    Mobile.setError("Number is Required");
                    Mobile.requestFocus();
                    return;
                }
                String realPhoneNumber = "+880" + number;
                Intent intent = new Intent(ProviderLogin.this, ProviderOtp.class);
                intent.putExtra("MobileNumber", realPhoneNumber);
                startActivity(intent);
            }
        });

        loginmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setVisibility(View.GONE);
                b.setVisibility(View.VISIBLE);

                Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = signinemail.getText().toString().trim();
                        String pass = sininpass.getText().toString().trim();

                        if(email.isEmpty()){
                            signinemail.setError("Enter an Email Adress");
                            signinemail.requestFocus();
                            return;
                        }
                        if(pass.isEmpty()){
                            sininpass.setError("Enter Password");
                            sininpass.requestFocus();
                            return;
                        }
                        if(pass.length()<8){
                            sininpass.setError("Password is Too Short");
                            sininpass.requestFocus();
                            return;
                        }
                        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(ProviderLogin.this, ProviderLocation.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });

            }
        });
    }
}