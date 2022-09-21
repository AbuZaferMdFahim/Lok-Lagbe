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

public class CustomerProfile extends AppCompatActivity {

    TextView fnames,mobile,dob,lingo;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;
    LinearLayout a,b;
    private Button Updata,Updata2;
    ImageView imageView;
    String docId,Address;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerprofile);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView = findViewById(R.id.profileImageId);
        Address = getIntent().getStringExtra("ADDRESS").toString();

        StorageReference profileRef = storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

        userId = firebaseAuth.getCurrentUser().getUid();

        fnames = findViewById(R.id.fName1Id);
        mobile = findViewById(R.id.MobileNumberId);
        dob = findViewById(R.id.dobId);
        lingo = findViewById(R.id.lingoId);

        a = (LinearLayout) findViewById(R.id.linId);

        Updata = (Button) findViewById(R.id.upDataId);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        DocumentReference doc = firestore.collection("users").document(userId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    fnames.setText(value.getString("FullName"));
                    mobile.setText(value.getString("Phone_Number"));
                    dob.setText(value.getString("Date Of Birth"));
                    lingo.setText(value.getString("GENDER"));
                }
            }
        });

        Updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CustomerProfile.this,CustomerProfileUpdate.class);
                intent.putExtra("FullName", fnames.getText().toString());
                intent.putExtra("Phone_Number", mobile.getText().toString());
                intent.putExtra("Date Of Birth",dob.getText().toString());
                intent.putExtra("GENDER",lingo.getText().toString());
                intent.putExtra("ADDRESS", Address);
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
        final StorageReference storageReference1 = storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                    }
                });
                Toast.makeText(CustomerProfile.this,"Profile Image Uploaded Succesfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CustomerProfile.this,"Profile Image is Not Uploaded",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            Intent intent = new Intent(CustomerProfile.this,CustomerHome.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}