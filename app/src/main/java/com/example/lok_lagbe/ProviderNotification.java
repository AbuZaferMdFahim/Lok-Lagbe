package com.example.lok_lagbe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderNotification extends AppCompatActivity {

    TextView a, b, c;
    int w = 0, x = 0;
    Button accept, reject;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    AlertDialog.Builder AlertDialogBuilder;
    public String userId, customerUserId, customerName, customerAddress, customerPhone, Addresss, myText, ProviderName, ProviderAddress, ProviderPhone, ProviderJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_notification);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        customerUserId = getIntent().getStringExtra("CustomerUserId").toString();
        Addresss = getIntent().getStringExtra("ADDRESS").toString();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        a = findViewById(R.id.customerNameId);
        b = findViewById(R.id.customerAdressId);
        c = findViewById(R.id.customerPhone13Id);

        accept = findViewById(R.id.customerAcceptId);
        reject = findViewById(R.id.customerRejectId);

//        a.setText(customerUserId);
//        b.setText(customerUserId);
        DocumentReference doc1 = firebaseFirestore.collection("providers").document(userId);
        doc1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    ProviderName = value.getString("Full_Name");
                    ProviderPhone = value.getString("Mobile_No");
                    ProviderAddress = value.getString("Permanent_Address");
                    ProviderJob = value.getString("Job");
                }
            }
        });

//        //intializa and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation20Id);
//
//        //set Home selected
//        bottomNavigationView.setSelectedItemId(R.id.notificatioproviderId);
//
//        //Perform ItemSelectedListener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                Intent intent;
//                switch (item.getItemId()){
//                    case R.id.notificatioproviderId:
//                        intent = new Intent(ProviderNotification.this,ProviderNotification.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.homeProviderId:
//                        intent = new Intent(ProviderNotification.this,ProviderHome.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.profileproviderId:
//                        intent = new Intent(ProviderNotification.this,ProviderProfile.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//
//                }
//                return false;
//            }
//        });


        DocumentReference doc2 = firebaseFirestore.collection("users").document(customerUserId);
        doc2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    a.setText(value.getString("FullName"));
                    customerName = value.getString("FullName");

                    b.setText(value.getString("ADDRESS"));
                    customerAddress = value.getString("ADDRESS");

                    c.setText(value.getString("Phone_Number"));
                    customerPhone = value.getString("Phone_Number");
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogBuilder = new AlertDialog.Builder(v.getContext());
                AlertDialogBuilder.setTitle(R.string.Title1);
                AlertDialogBuilder.setMessage(R.string.message1);
                AlertDialogBuilder.setIcon(R.drawable.ic_baseline_person_24);

                final EditText editText = new EditText(ProviderNotification.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialogBuilder.setView(editText);
                AlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        myText = editText.getText().toString();
//                        DocumentReference documentReference = firebaseFirestore.collection("providersrequest").document(userId).collection("request").document(customerUserId);
//                        Map<String, Object> user = new HashMap<>();
//                        user.put("CustomerName", customerName);
//                        user.put("CustomerAddress", customerAddress);
//                        user.put("CustomerPhone", customerPhone);
//                        user.put("Request", "Accept");
//                        user.put("Status", "None");
//                        user.put("Charge", myText);
//
//                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("TAG", "onSuccess" + userId);
//                                DocumentReference documentReference3 = firebaseFirestore.collection("providersrating").document(userId).collection("rating").document(customerUserId);
//                                Map<String,Object> user3 = new HashMap<>();
//                                user3.put("Rate","0");
//                                user3.put("Name", customerName);
//                                documentReference3.set(user3).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Log.d("TAG", "onSuccess" + userId);
                        DocumentReference documentReference1 = firebaseFirestore.collection("usersrequest").document(customerUserId).collection("request").document(userId);
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("Request", "Accept");
                        user1.put("Status", "None");
                        user1.put("ProviderCharge", myText);
                        documentReference1.update(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DocumentReference documentReference2 = firebaseFirestore.collection("providers").document(userId);
                                Map<String, Object> user2 = new HashMap<>();
                                user2.put("CustomerUserId", null);
                                documentReference2.update(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                        Intent intent = new Intent(ProviderNotification.this, ProviderHome.class);
                                        intent.putExtra("ADDRESS", Addresss);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
//                                    }
//                                });
//                            }
//                        });
                    }
                });
                AlertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ProviderNotification.this, "Okay", Toast.LENGTH_LONG).show();
                    }
                });


                AlertDialog alertDialog = AlertDialogBuilder.create();
                alertDialog.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogBuilder = new AlertDialog.Builder(v.getContext());
                AlertDialogBuilder.setTitle(R.string.Title3);
                AlertDialogBuilder.setMessage(R.string.message3);
                AlertDialogBuilder.setIcon(R.drawable.ic_baseline_person_24);
                AlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        firebaseFirestore.collection("providershistory").document(userId).collection("history").get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot documentSnapshot : list) {
                                            x = x + 1;
                                        }
                                        String wow1 = String.valueOf(x + 1);
                                        DocumentReference documentReference = firebaseFirestore.collection("providershistory").document(userId).collection("history").document(wow1);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("CustomerName", customerName);
                                        user.put("CustomerPhone", customerPhone);
                                        user.put("CustomerAddress", customerAddress);
                                        user.put("Request", "Reject");
                                        user.put("Status", "None");
                                        user.put("Charge", "None");
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("TAG", "onSuccess" + userId);
                                                firebaseFirestore.collection("usershistory").document(customerUserId).collection("history").get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                for (DocumentSnapshot documentSnapshot : list) {
                                                                    w = w + 1;
                                                                }
                                                                String wow = String.valueOf(w + 1);
                                                                DocumentReference documentReference1 = firebaseFirestore.collection("usershistory").document(customerUserId).collection("history").document(wow);
                                                                Map<String, Object> user1 = new HashMap<>();
                                                                user1.put("Request", "Reject");
                                                                user1.put("ProviderName", ProviderName);
                                                                user1.put("ProviderJob", ProviderJob);
                                                                user1.put("ProviderCharge", "None");
                                                                user1.put("Status", "None");
                                                                user1.put("ProviderPhone", ProviderPhone);
                                                                user1.put("ProviderAddress", ProviderAddress);
                                                                documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Log.d("TAG", "onSuccess" + customerUserId);
                                                                        DocumentReference documentReference2 = firebaseFirestore.collection("providers").document(userId);
                                                                        Map<String, Object> user2 = new HashMap<>();
                                                                        user2.put("CustomerUserId", null);
                                                                        documentReference2.update(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                DocumentReference documentReference3 = firebaseFirestore.collection("users").document(customerUserId);
                                                                                Map<String, Object> user3 = new HashMap<>();
                                                                                user3.put("ProviderUserId", null);
                                                                                documentReference3.update(user3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void unused) {
                                                                                        if (progressDialog.isShowing()) {
                                                                                            progressDialog.dismiss();
                                                                                        }
                                                                                        finish();
                                                                                        Intent intent = new Intent(ProviderNotification.this, ProviderHome.class);
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
                                    }
                                });
                    }
                });
                AlertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ProviderNotification.this, "Okay", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = AlertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}