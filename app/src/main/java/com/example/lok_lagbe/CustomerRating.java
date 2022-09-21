package com.example.lok_lagbe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRating extends AppCompatActivity {

    RatingBar ratingBar;
    Button submit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String Addresss, ProviderUserId, UserId, Name;
    AlertDialog.Builder AlertDialogBuilder;
    ArrayList<ProviderRatingModel> dataList1;
    ProviderRatingModel model1;
    String Rating, rate, name;
    ProgressDialog progressDialog;
    int w = 0;
    double i = 0.0, k = 0.0, l = 0.0, j = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rating);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        ProviderUserId = getIntent().getStringExtra("ProviderUserId").toString();
        Addresss = getIntent().getStringExtra("ADDRESS").toString();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();

        ratingBar = findViewById(R.id.rating1Id);
        submit = findViewById(R.id.submit1Id);

        DocumentReference doc = firebaseFirestore.collection("users").document(UserId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    Name = value.getString("FullName");
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogBuilder = new AlertDialog.Builder(CustomerRating.this);
                AlertDialogBuilder.setTitle(R.string.Title2);
                AlertDialogBuilder.setMessage(R.string.message2);
                AlertDialogBuilder.setIcon(R.drawable.ic_baseline_person_24);

                AlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        String s = String.valueOf(ratingBar.getRating());
                        firebaseFirestore.collection("providersrating").document(ProviderUserId).collection("rating").get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot documentSnapshot : list) {
                                            w = w + 1;
                                        }
                                        String wow = String.valueOf(w + 1);
                                        DocumentReference documentReference = firebaseFirestore.collection("providersrating").document(ProviderUserId).collection("rating").document(wow);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("Rate", s);
                                        user.put("Name", Name);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("TAG", "onSuccess" + ProviderUserId);
                                                firebaseFirestore.collection("providersrating").document(ProviderUserId).collection("rating").get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                for (DocumentSnapshot documentSnapshot : list) {
                                                                    model1 = documentSnapshot.toObject(ProviderRatingModel.class);
                                                                    rate = model1.getRate();
                                                                    j = Double.parseDouble(rate);
                                                                    i = i + j;
                                                                    k = k + 1.0;
                                                                }
                                                                l = i / k;
                                                                Rating = String.valueOf(l);
                                                                DocumentReference documentReference = firebaseFirestore.collection("providers").document(ProviderUserId);
                                                                Map<String, Object> user = new HashMap<>();
                                                                user.put("Rating", Rating);
                                                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        if (progressDialog.isShowing()) {
                                                                            progressDialog.dismiss();
                                                                        }
                                                                        finish();
                                                                        Intent intent = new Intent(CustomerRating.this, CustomerHome.class);
                                                                        intent.putExtra("ADDRESS", Addresss);
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                            }
                                                        });
                                            }
                                        });
                                    }
                                });
                    }
                });
                AlertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        Toast.makeText(CustomerRating.this, "Okay", Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = AlertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}