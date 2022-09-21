package com.example.lok_lagbe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProviderProfile extends AppCompatActivity {

    TextView fullname,email,dob,lingo,telephone,address,job,fathername,nidNo,charge;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;
    LinearLayout a,b;
    Button Updata,Updata1;
    ImageView imageView;
    String docId,Address,customerUserId;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerprofile);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        Address = getIntent().getStringExtra("ADDRESS").toString();

        imageView = findViewById(R.id.profileImageId);

        StorageReference profileRef = storageReference.child("providers/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

        userId = firebaseAuth.getCurrentUser().getUid();

        fullname = findViewById(R.id.fName12Id);
        email = findViewById(R.id.lName12Id);
        dob = findViewById(R.id.dob12Id);
        lingo = findViewById(R.id.lingo12Id);
        telephone = findViewById(R.id.TelephoneId);
        address = findViewById(R.id.pAddressId);
        fathername = findViewById(R.id.FathersId);
        charge = findViewById(R.id.chargeId);
        job = findViewById(R.id.JobsId);

        nidNo = findViewById(R.id.nationalId);


        Updata = (Button) findViewById(R.id.upDataId);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        DocumentReference doc12 = firestore.collection("providers").document(userId);
//        doc12.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (value.exists()) {
//                    if (value.getString("CustomerUserId") != null) {
//                        customerUserId = value.getString("CustomerUserId");
//                    }
//                }
//            }
//        });

//        //intializa and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation20Id);
//
//        //set Home selected
//        bottomNavigationView.setSelectedItemId(R.id.profileproviderId);
//
//        //Perform ItemSelectedListener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                Intent intent;
//                switch (item.getItemId()){
//                    case R.id.profileproviderId:
//                        intent = new Intent(ProviderProfile.this,ProviderProfile.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.homeProviderId:
//                        intent = new Intent(ProviderProfile.this,ProviderHome.class);
//                        intent.putExtra("ADDRESS",Address);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.notificatioproviderId:
//                        if(customerUserId!=null) {
//                            intent = new Intent(ProviderProfile.this, ProviderNotification.class);
//                            intent.putExtra("ADDRESS", Address);
//                            startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(ProviderProfile.this,"You Have No Notification Now",Toast.LENGTH_LONG).show();
//                        }
//                        overridePendingTransition(0,0);
//                        return  true;
//
//                }
//                return false;
//            }
//        });


        DocumentReference doc = firestore.collection("providers").document(userId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    fullname.setText(value.getString("Full_Name"));
                    email.setText(value.getString("Email"));
                    job.setText(value.getString("Job"));
                    nidNo.setText(value.getString("Nid_Number"));
                    telephone.setText(value.getString("Mobile_No"));
                    fathername.setText(value.getString("Fathers_Name"));
                    address.setText(value.getString("Permanent_Address"));
                    dob.setText(value.getString("Date_Of_Birth"));
                    charge.setText(value.getString("Charge"));
                    lingo.setText(value.getString("GENDER"));
                }
                else {
                   Toast.makeText(ProviderProfile.this,"Profile is Not Completed.Contact With the Authorities",Toast.LENGTH_LONG).show();
                }

            }
        });

        Updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ProviderProfile.this,ProviderUpdate.class);
                intent.putExtra("Full_Name", fullname.getText().toString());
                intent.putExtra("Email", email.getText().toString());
                intent.putExtra("Date_Of_Birth",dob.getText().toString());
                intent.putExtra("GENDER",lingo.getText().toString());
                intent.putExtra("Permanent_Address", address.getText().toString());
                intent.putExtra("Fathers_Name", fathername.getText().toString());
                intent.putExtra("Nid_Number",nidNo.getText().toString());
                intent.putExtra("Mobile_No",telephone.getText().toString());
                intent.putExtra("Charge",charge.getText().toString());
                intent.putExtra("ADDRESS",Address);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                //imageView.setImageURI(uri);
                uploadImageForFirbase(uri);
            }
        }
    }

    private void uploadImageForFirbase(Uri uri) {
        //upload image to firebase storage
        final StorageReference storageReference1 = storageReference.child("providers/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                    }
                });
                Toast.makeText(ProviderProfile.this,"Profile Image Uploaded Succesfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProviderProfile.this,"Profile Image is Not Uploaded",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            Intent intent = new Intent(ProviderProfile.this,ProviderHome.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}