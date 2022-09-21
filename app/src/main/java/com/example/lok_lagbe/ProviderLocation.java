package com.example.lok_lagbe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProviderLocation extends AppCompatActivity {

    Button askLocation;
    SupportMapFragment supportMapFragment;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String userId;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerlocation);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = firebaseAuth.getCurrentUser().getUid();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        askLocation = (Button) findViewById(R.id.askLocation12Id);



        DocumentReference doc = firestore.collection("providers").document(userId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    askLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == R.id.askLocation12Id) {
                                if (ActivityCompat.checkSelfPermission(ProviderLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    getLocation();
                                } else {
                                    ActivityCompat.requestPermissions(ProviderLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(ProviderLocation.this,"You have to Set up Your Profile First",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProviderLocation.this,ProviderSetupProfile.class);
                    startActivity(intent);
                }

            }
        });
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(ProviderLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProviderLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<android.location.Location> task) {
                android.location.Location location = task.getResult();
                if(location != null){
                    Geocoder geocoder = new Geocoder(ProviderLocation.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String adress = addresses.get(0).getAddressLine(0);
                        DocumentReference documentReference1 = firestore.collection("providers").document(userId);
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("Permanent_Address","Gulshan");
                        documentReference1.update(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                        Intent intent = new Intent(ProviderLocation.this, ProviderHome.class);
                        intent.putExtra("ADDRESS",adress);
                        finish();
                        startActivity(intent);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}