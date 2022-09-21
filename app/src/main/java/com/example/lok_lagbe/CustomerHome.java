package com.example.lok_lagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class CustomerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle Toggle;
    Button locations,customerhistory,customerrequest;
    LinearLayout l1,l2,l3,l4,l5,l6,l7,l8,l9;
    ImageView imageView;
    String userId,Address,providerUserId,ok;
    StorageReference storageReference;
    TextView adr;
    ImageView image;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private NavigationView n;
    private TextView imid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhome);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseInstanceId.getInstance().getToken();
        imageView = (ImageView) findViewById(R.id.imageView1ID);
        userId = mAuth.getCurrentUser().getUid();
        adr =  findViewById(R.id.adressId);

//        imid = findViewById(R.id.txtId);

//        customerhistory = findViewById(R.id.customerHistoryId);
        customerrequest = findViewById(R.id.customerRequestId);

        l1 = findViewById(R.id.liLa1Id);
        l2 = findViewById(R.id.liLa2Id);
        l3 = findViewById(R.id.liLa3Id);
        l4 = findViewById(R.id.liLa4Id);
        l5 = findViewById(R.id.liLa5Id);
        l6 = findViewById(R.id.liLa6Id);
        l7 = findViewById(R.id.liLa7Id);
        l8 = findViewById(R.id.liLa8Id);
        l9 = findViewById(R.id.liLa9Id);


        drawerLayout = findViewById(R.id.drawerId);
        NavigationView navigationView = findViewById(R.id.navId);



        navigationView.setNavigationItemSelectedListener(this);

        Toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Address = getIntent().getStringExtra("ADDRESS").toString();
        adr.setText(Address);

        DocumentReference doc = firebaseFirestore.collection("users").document(userId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    if (value.getString("ProviderUserId") != null) {
                        providerUserId = value.getString("ProviderUserId");
//                        imid.setText(value.getString("FullName"));
                    }
                }
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,Routerproblem.class);
                startActivity(intent);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,FanService.class);
                startActivity(intent);

            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,DrillingService.class);
                startActivity(intent);
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,AcRepair.class);
                startActivity(intent);
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,ElectricGeyser.class);
                startActivity(intent);
            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this, WashingMachine.class);
                startActivity(intent);
            }
        });
        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,ElectricService.class);
                startActivity(intent);
            }
        });
        l8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,PlumbingService.class);
                startActivity(intent);
            }
        });
        l9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,WashBasinService.class);
                startActivity(intent);

            }
        });
//        customerhistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CustomerHome.this,CustomersHistory.class);
//                startActivity(intent);
//            }
//        });

        customerrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(providerUserId !=null) {
                    Intent intent = new Intent(CustomerHome.this, CustomerRequest.class);
                    intent.putExtra("ProviderUserId", providerUserId);
                    intent.putExtra("ADDRESS", Address);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CustomerHome.this,"Your Request is Empty Now",Toast.LENGTH_LONG).show();
                }
            }
        });

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
        if(item.getItemId()==R.id.homeId){
            intent = new Intent(this,CustomerHome.class);
            intent.putExtra("ADDRESS",adr.getText().toString());
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.profileId){
            intent = new Intent(this,CustomerProfile.class);
            intent.putExtra("ADDRESS", Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.customerAboutUsId){
            intent = new Intent(this,CustomerAboutUs.class);
            intent.putExtra("ADDRESS", Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.customerMenuHistoryId){
            intent = new Intent(this,CustomersHistory.class);
//            intent.putExtra("ADDRESS", Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.customerContactId){
            intent = new Intent(this,CustomerContactus.class);
            intent.putExtra("ADDRESS", Address);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.customerSignoutId){
            mAuth.signOut();
            intent = new Intent(CustomerHome.this,MainActivity.class);
            Toast.makeText(CustomerHome.this,"Successfully Logout",Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
        return false;
    }
}