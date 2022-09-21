package com.example.lok_lagbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerContactus extends AppCompatActivity {

    EditText subject,message;
    Button send;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String UserId,Address;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_contactus);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();

        Address = getIntent().getStringExtra("ADDRESS").toString();

        subject = findViewById(R.id.customerEmailSubjectId);
        message = findViewById(R.id.customerEmailMessageId);

        send = findViewById(R.id.customerEmailSendId);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = subject.getText().toString();
                String text = message.getText().toString();

                firebaseFirestore.collection("usersmessages").document(UserId).collection("messages").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot documentSnapshot : list) {
                                    i = i + 1;
                                }
                                String j = String.valueOf(i+1);
                                DocumentReference documentReference = firebaseFirestore.collection("usersmessages").document(UserId).collection("messages").document(j);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Subject", sub);
                                user.put("Message",text);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG", "onSuccess" + UserId);
                                        Intent intent = new Intent(CustomerContactus.this, CustomerHome.class);
                                        intent.putExtra("ADDRESS", Address);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
            }
        });



    }
}