package com.example.lok_lagbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Providerrating extends AppCompatActivity {

    TextView a;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<ProviderRatingModel> dataList;
    ProviderRatingModel model;
    ProviderRatingAdapter adapter;
    String UserId,Address,Rating,rate;
    ProgressDialog progressDialog;
    double i = 0.0,k=0.0,l=0.0,j=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerrating);

        Address = getIntent().getStringExtra("ADDRESS").toString();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        recyclerView = (RecyclerView) findViewById(R.id.providerrrRatingId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();

        dataList = new ArrayList<>();
        adapter = new ProviderRatingAdapter(dataList);
        recyclerView.setAdapter(adapter);

//        firebaseFirestore.collection("providersrating").document(UserId).collection("rating").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                        for(DocumentSnapshot documentSnapshot: list) {
//                            model = documentSnapshot.toObject(ProviderRatingModel.class);
//                            rate = model.getRate();
//
//                            j = Double.parseDouble(rate);
//                            i = i + j;
//                            k++;
//                        }
//                        l = i/k;
//                        Rating = String.valueOf(l);
//                        a.setText(Rating);
//                    }
//                });

        firebaseFirestore.collection("providersrating").document(UserId).collection("rating").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot : list) {
                            model = documentSnapshot.toObject(ProviderRatingModel.class);
                            dataList.add(model);
                        }

                        adapter.notifyDataSetChanged();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }
                });



    }
}