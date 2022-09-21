package com.example.lok_lagbe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Backend;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerLogin extends AppCompatActivity {

    private Button login,loginmail,Continue;
    private EditText Mobile,signinemail,sininpass;
    private TextView textView;
    private LinearLayout a,b;
    Backend backend;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerlogin);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        login = (Button) findViewById(R.id.LoginId);
        loginmail = (Button) findViewById(R.id.loginWithEmailID);
        Continue = (Button) findViewById(R.id.signInLoginId);

        Mobile = (EditText) findViewById(R.id.PId);
        signinemail = (EditText) findViewById(R.id.signInEmailId);
        sininpass = (EditText) findViewById(R.id.signInPasswordId);

        textView = (TextView) findViewById(R.id.notRegisteredId);

        a= (LinearLayout) findViewById(R.id.lid);
        b= (LinearLayout) findViewById(R.id.l1id);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerLogin.this, CustomerEmailSignin.class);
                startActivity(intent);

            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(v.VISIBLE);
////                String code = CountryData.countryCode[spinner.getSelectedItemPosition()];
//                SharedPreferences sharedPreferences = getSharedPreferences(CustomerLogin.PREFS_NAME,0);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putBoolean("hasLoggedIn",true);
//
//                if(editor.commit()){
//                    Intent intent = new Intent(CustomerLogin.this,CustomerLocation.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                sharedpreferences = getApplicationContext().getSharedPreferences(CustomerLogin.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("LOGIN",firebaseAuth.getCurrentUser().getUid() );
                editor.commit();

                String number = Mobile.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                        Mobile.setError("Number is Required");
                        Mobile.requestFocus();
                        return;
                    }
                    String realPhoneNumber = "+880" + number;
                    Intent intent = new Intent(CustomerLogin.this, CustomerOtp.class);
                    intent.putExtra("MobileNumber", realPhoneNumber);
                    startActivity(intent);

//                }
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
//                                    SharedPreferences.Editor editor = sharedpreferences.edit();
//                                    editor.putString("LOGIN",firebaseAuth.getCurrentUser().getUid() );
//                                    editor.commit();
                                    Intent intent = new Intent(CustomerLogin.this, CustomerLocation.class);
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