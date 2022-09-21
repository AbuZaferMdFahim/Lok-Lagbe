package com.example.lok_lagbe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GetProviderInformation extends AppCompatActivity {

    TextView a,b,c;
    String provideruserId,providerAddress,providerJob,userId;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_information);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        a = findViewById(R.id.a1Id);
        b = findViewById(R.id.a2Id);
        c = findViewById(R.id.a3Id);

        provideruserId = getIntent().getStringExtra("Uid").toString();
        providerAddress = getIntent().getStringExtra("ProviderAddress").toString();
        providerJob = getIntent().getStringExtra("ProviderJob").toString();

        DocumentReference doc = firebaseFirestore.collection("providers").document(provideruserId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value.exists()) {

                    a.setText(value.getString("Full_Name"));
                    b.setText(value.getString("Charge"));
                }
            }
        });
        DocumentReference doc1 = firebaseFirestore.collection("Providers").document(provideruserId);
        doc1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    c.setText(value.getString("Completed_Job"));
                }
            }
        });




    }
}