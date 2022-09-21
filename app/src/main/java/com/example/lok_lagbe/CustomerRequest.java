package com.example.lok_lagbe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRequest extends AppCompatActivity {

    TextView a, b, c, d, e, f, g;
    LinearLayout h, i;
    Button state;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder AlertDialogBuilder;
    int j, k, w = 0, x = 0;
    String userId, ProviderUserId, Addresss, Status, completedJob, cb, CustomerName, CustomerPhone, CustomerAddress;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        ProviderUserId = getIntent().getStringExtra("ProviderUserId").toString();
        Addresss = getIntent().getStringExtra("ADDRESS").toString();

        a = findViewById(R.id.providerNameAshbeId);
        b = findViewById(R.id.providerPhoneAshbeId);
        c = findViewById(R.id.providerThikanaAshbeId);
        d = findViewById(R.id.providerChargeAshbeId);
        e = findViewById(R.id.providerRequestAshbeId);
        f = findViewById(R.id.providerStatusAshbeId);
        g = findViewById(R.id.providerJobAshbeId);

        state = findViewById(R.id.statusdiboId);

        h = findViewById(R.id.stId);
        i = findViewById(R.id.st1Id);

        DocumentReference doc20 = firebaseFirestore.collection("users").document(userId);
        doc20.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    CustomerName = value.getString("FullName");
                    CustomerPhone = value.getString("Phone_Number");
                    CustomerAddress = value.getString("ADDRESS");
                }
            }
        });

        DocumentReference doc1 = firebaseFirestore.collection("usersrequest").document(userId).collection("request").document(ProviderUserId);
        doc1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    a.setText(value.getString("ProviderName"));
                    b.setText(value.getString("ProviderPhone"));
                    c.setText(value.getString("ProviderAddress"));
                    g.setText(value.getString("ProviderJob"));
                    d.setText(value.getString("ProviderCharge"));
                    e.setText(value.getString("Request"));
                    f.setText(value.getString("Status"));
                    Status = value.getString("Status");
                    if (Status.equals("None")) {
                        h.setVisibility(View.GONE);
                        i.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        DocumentReference doc = firebaseFirestore.collection("providers").document(ProviderUserId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    cb = value.getString("Completed_Job");
                }
            }
        });


        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialogBuilder = new AlertDialog.Builder(CustomerRequest.this);
                View mview = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                AlertDialogBuilder.setTitle(R.string.Title4);
                Spinner spinner = (Spinner) mview.findViewById(R.id.spinner20Id);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomerRequest.this, android.R.layout.simple_spinner_item
                        , getResources().getStringArray(R.array.Work_Status));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(adapter);
                AlertDialogBuilder.setMessage(R.string.message4);
                AlertDialogBuilder.setIcon(R.drawable.ic_baseline_person_24);
                j = Integer.parseInt(cb);
                k = j + 1;
                completedJob = Integer.toString(k);
                AlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();

                        if (!spinner.getSelectedItem().toString().equalsIgnoreCase("Choose Work Status...")) {
                            String Work = spinner.getSelectedItem().toString();

                            firebaseFirestore.collection("usershistory").document(userId).collection("history").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot documentSnapshot : list) {
                                                w = w + 1;
                                            }
                                            String wow = String.valueOf(w + 1);
                                            DocumentReference documentReference = firebaseFirestore.collection("usershistory").document(userId).collection("history").document(wow);
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("ProviderName", a.getText().toString());
                                            user.put("ProviderAddress", c.getText().toString());
                                            user.put("ProviderPhone", b.getText().toString());
                                            user.put("ProviderCharge", d.getText().toString());
                                            user.put("Status", Work);
                                            user.put("ProviderJob", g.getText().toString());
                                            user.put("Request", e.getText().toString());


                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("TAG", "onSuccess" + userId);

                                                    firebaseFirestore.collection("providershistory").document(ProviderUserId).collection("history").get()
                                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                    for (DocumentSnapshot documentSnapshot : list) {
                                                                        x = x + 1;
                                                                    }
                                                                    String wow1 = String.valueOf(x + 1);
                                                                    DocumentReference documentReference1 = firebaseFirestore.collection("providershistory").document(ProviderUserId).collection("history").document(wow1);
                                                                    Map<String, Object> user1 = new HashMap<>();
                                                                    user1.put("CustomerName", CustomerName);
                                                                    user1.put("CustomerAddress", CustomerAddress);
                                                                    user1.put("CustomerPhone", CustomerPhone);
                                                                    user1.put("Request", e.getText().toString());
                                                                    user1.put("Status", Work);
                                                                    user1.put("Charge", d.getText().toString());
                                                                    documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            Log.d("TAG", "onSuccess" + ProviderUserId);
                                                                            DocumentReference documentReference3 = firebaseFirestore.collection("providers").document(ProviderUserId);
                                                                            Map<String, Object> user3 = new HashMap<>();
                                                                            user3.put("Completed_Job", completedJob);
                                                                            documentReference3.update(user3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {
                                                                                    DocumentReference documentReference2 = firebaseFirestore.collection("users").document(userId);
                                                                                    Map<String, Object> user2 = new HashMap<>();
                                                                                    user2.put("ProviderUserId", null);
                                                                                    documentReference2.update(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {
                                                                                            if (progressDialog.isShowing()) {
                                                                                                progressDialog.dismiss();
                                                                                            }
                                                                                            finish();
                                                                                            Intent intent = new Intent(CustomerRequest.this, CustomerRating.class);
                                                                                            intent.putExtra("ADDRESS", Addresss);
                                                                                            intent.putExtra("ProviderUserId", ProviderUserId);
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
                    }
                });
                AlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        Toast.makeText(CustomerRequest.this, "Okay", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
                AlertDialogBuilder.setView(mview);
                AlertDialog alertDialog = AlertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}