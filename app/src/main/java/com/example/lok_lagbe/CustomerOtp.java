package com.example.lok_lagbe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class CustomerOtp extends AppCompatActivity {

    private EditText otp1;
    private Button verify1;
    String phoneNumber;
    Task task;
    public String otpCode;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private SharedPreferences sharedpreferences;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerotp);
        otpCode = "";
        phoneNumber = getIntent().getStringExtra("MobileNumber").toString().trim();
        otp1 = (EditText) findViewById(R.id.Otp1Id);
        verify1 = (Button) findViewById(R.id.Verify1Id);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        sendVerificationCode(phoneNumber);


        verify1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar1.setVisibility(v.VISIBLE);
                String code = otp1.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    otp1.setError("Enter valide code");
                    otp1.requestFocus();
                    return;
                }

                verifyCode(code);

            }
        });
    }



    //verifying code
    public void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpCode,code);
        signInWithAuthCredential(credential);

    }


    //Number Send for code
    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent( String s,PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otpCode = s;
                    }
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();
                        if(code!=null){
                            verifyCode(code);
                        }
                    }
                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
    }

    private void signInWithAuthCredential(PhoneAuthCredential Credential) {
        mAuth.signInWithCredential(Credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            DocumentReference documentReference = firebaseFirestore.collection("users").document(user.getUid());
//
//                            Map<String, Object> user = new HashMap<>();
//                            user.put("First Name","firstname");
//                            user.put("Last Name","lastname");
//                            user.put("Date Of Birth", "DateOfBirth");
//                            user.put("GENDER","Gender");
//
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Log.d("TAG", "onSuccess" + user);

                            Intent intent = new Intent(CustomerOtp.this, CustomerLocation.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
//                                }
//                            });
                        }
                        else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(CustomerOtp.this,"Incorrect verification Code",Toast.LENGTH_LONG).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
//            Intent intent =new Intent(GetOtp.this, Password.class);
//            intent.putExtra("Anim",phoneNumber);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }
}