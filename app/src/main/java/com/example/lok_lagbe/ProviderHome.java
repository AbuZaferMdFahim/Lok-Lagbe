package com.example.lok_lagbe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle Toggle;
    ProgressBar progressBar;
    String userId,Address,customerUserId;
    StorageReference storageReference;
    TextView adr,Name,phone,job,rate;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    Button goOnline,Online,notification;
    FirebaseFirestore firebaseFirestore;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerhome);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");


        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        goOnline = findViewById(R.id.goOnlineId);
        Online = findViewById(R.id.OnlineId);
        notification = findViewById(R.id.notify12Id);
//        providerhistory = findViewById(R.id.providerHistoryShowId);
//        rating = findViewById(R.id.Ratingid);

        progressBar = findViewById(R.id.progressId);
        
        adr =  findViewById(R.id.adress1Id);
        Name =  findViewById(R.id.namId);
        phone =  findViewById(R.id.PhoneId);
        job =  findViewById(R.id.chakriId);
        rate = findViewById(R.id.AmarrateId);

//        class ProviderHeader{
//
//            TextView Name,Rate;
//            public ProviderHeader(@NonNull @NotNull View itemView) {
//                super(itemView);
//                Name = itemView.findViewById(R.id.raterNameId);
//                Rate = itemView.findViewById(R.id.raterRateId);
//            }
//
//        }

//        //intializa and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation20Id);
//
//        //set Home selected
//        bottomNavigationView.setSelectedItemId(R.id.homeProviderId);
//
//        //Perform ItemSelectedListener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                Intent intent;
//                switch (item.getItemId()){
//                    case R.id.homeProviderId:
//                        intent = new Intent(ProviderHome.this,ProviderHome.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.profileproviderId:
//                        intent = new Intent(ProviderHome.this,ProviderProfile.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.notificatioproviderId:
//                        if(customerUserId!=null) {
//                            intent = new Intent(ProviderHome.this, ProviderNotification.class);
//                            intent.putExtra("ADDRESS", Address);
//                            startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(ProviderHome.this,"You Have No Notification Now",Toast.LENGTH_LONG).show();
//                        }
//                        overridePendingTransition(0,0);
//                        return  true;
//
//                }
//                return false;
//            }
//        });



        imageView = findViewById(R.id.chobiId);

        userId = mAuth.getCurrentUser().getUid();

        drawerLayout = findViewById(R.id.drawer1Id);

        Address = getIntent().getStringExtra("ADDRESS").toString();
        adr.setText(Address);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);

        StorageReference profileRef = storageReference.child("providers/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navId);
        navigationView.setNavigationItemSelectedListener(this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        ProviderHeader p = new ProviderHeader(userId);



        progressDialog.show();
//        firebaseFirestore.collection("providershistory").document(userId).collection("history").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        int i = 0;
//                        for (DocumentSnapshot documentSnapshot : list) {
//                            model = documentSnapshot.toObject(ProviderModel.class);
//                            if (model.getStatus().equals("Completed")) {
//                                i = i+1;
//                                int j = i-1;
//                                String A = Integer.toString(j);
//                                DocumentReference documentReference = firebaseFirestore.collection("providers").document(userId);
//                                Map<String, Object> user = new HashMap<>();
//                                user.put("Completed_Job", A);
//                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                    }
//                                });
//                            }
//                        }
//                    }
//                });

//                firebaseFirestore.collection("providersrating").document(userId).collection("rating").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(@NonNull @NotNull QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                        for (DocumentSnapshot documentSnapshot : list) {
//                            model1 = documentSnapshot.toObject(ProviderRatingModel.class);
//                            rate = model1.getRate();
//                            j = Double.parseDouble(rate);
//                            i = i + j;
//                            k = k + 1.0;
//                        }
//                        l = i / k;
//                        Rating = String.valueOf(l);
//                        DocumentReference documentReference = firebaseFirestore.collection("providers").document(userId);
//                        Map<String, Object> user = new HashMap<>();
//                        user.put("Rating", Rating);
//                        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//
//                            }
//                        });
//                    }
//                });
//
//


        DocumentReference doc = firebaseFirestore.collection("providers").document(userId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    if(value.getString("CustomerUserId") != null) {
                        customerUserId = value.getString("CustomerUserId");
                    }
                    Name.setText(value.getString("Full_Name"));
                    job.setText(value.getString("Job"));
                    phone.setText(value.getString("Mobile_No"));
                    rate.setText(value.getString("Rating"));
                }
                else {
                    Toast.makeText(ProviderHome.this,"Profile is Not Completed...Contact With the Authorities",Toast.LENGTH_LONG).show();
                }

            }
        });
        if(progressDialog.isShowing())
            progressDialog.dismiss();

        goOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOnline.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Online.setVisibility(View.VISIBLE);
                notification.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        });
        Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOnline.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Online.setVisibility(View.GONE);
                notification.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customerUserId!=null) {
                    Intent intent = new Intent(ProviderHome.this, ProviderNotification.class);
                    intent.putExtra("CustomerUserId",customerUserId);
                    intent.putExtra("ADDRESS", Address);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ProviderHome.this,"You Have No Notification Now",Toast.LENGTH_LONG).show();
                }
            }
        });
//        class ProviderHeader extends AppCompatActivity {
//            FirebaseAuth firebaseAuth;
//            String UserId;
//            TextView t;
//            ImageView i;
//            FirebaseFirestore firebaseFirestore;
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.providerheader);
//
//                firebaseAuth = FirebaseAuth.getInstance();
//                firebaseFirestore = FirebaseFirestore.getInstance();
//                UserId = firebaseAuth.getCurrentUser().getUid();
//
//                t = findViewById(R.id.txt20Id);
//                i = findViewById(R.id.im20Id);
//                StorageReference profileRef = storageReference.child("providers/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
//                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into(i);
//                    }
//                });
//
//                DocumentReference doc = firebaseFirestore.collection("providers").document(UserId);
//                doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (value.exists()) {
//                            t.setText(value.getString("Full_Name"));
//                        }
//                    }
//                });
//
//
//            }
//        }


//        rating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProviderHome.this,Providerrating.class);
//                intent.putExtra("ADDRESS", Address);
//                startActivity(intent);
//
//            }
//        });

//        providerhistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProviderHome.this,ProvidersHistory.class);
//                intent.putExtra("ADDRESS", Address);
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if(item.getItemId()==R.id.k10id){
                intent = new Intent(this,ProviderHome.class);
                intent.putExtra("ADDRESS",adr.getText().toString());
                startActivity(intent);
        }

        else if(item.getItemId()==R.id.providersProfileId){
            intent = new Intent(ProviderHome.this,ProviderProfile.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.providersMenuHistoryId){
            intent = new Intent(ProviderHome.this,ProvidersHistory.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.providersMenuRatingId){
            intent = new Intent(ProviderHome.this,Providerrating.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.providersAboutUsId){
            intent = new Intent(ProviderHome.this,ProviderAboutUs.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.providersContactId){
            intent = new Intent(ProviderHome.this,ProviderContactUs.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.providersSignoutId){
            mAuth.getInstance().signOut();
            intent = new Intent(ProviderHome.this,MainActivity.class);
            Toast.makeText(ProviderHome.this,"Successfully Logout",Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }

        return false;
    }
}