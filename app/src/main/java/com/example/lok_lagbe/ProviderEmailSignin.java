package com.example.lok_lagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class ProviderEmailSignin extends AppCompatActivity {
    private EditText editText,editText1;
    private Button button;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provideremailsignin);

        firebaseAuth = FirebaseAuth.getInstance();

        editText = (EditText) findViewById(R.id.email2Id);
        editText1 = (EditText) findViewById(R.id.password2Id);
        button = (Button) findViewById(R.id.button2Id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString().trim();
                String pass = editText1.getText().toString().trim();

                if(email.isEmpty()){
                    editText.setError("Enter an Email Adress");
                    editText.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    editText1.setError("Enter Password");
                    editText1.requestFocus();
                    return;
                }
                if(pass.length()<8){
                    editText1.setError("Password is Too Short");
                    editText1.requestFocus();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(ProviderEmailSignin.this,ProviderLogin.class);
                            Toast.makeText(ProviderEmailSignin.this,"User is Successfully Registered",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(ProviderEmailSignin.this,"User is Already Existed",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(ProviderEmailSignin.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
    }

}