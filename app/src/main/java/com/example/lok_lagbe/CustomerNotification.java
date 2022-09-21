package com.example.lok_lagbe;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lok_lagbe.SendNotificationPack.APIService;
import com.example.lok_lagbe.SendNotificationPack.Client;
import com.example.lok_lagbe.SendNotificationPack.Data;
import com.example.lok_lagbe.SendNotificationPack.MyResponse;
import com.example.lok_lagbe.SendNotificationPack.NotificationSender;
import com.example.lok_lagbe.SendNotificationPack.Token;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CustomerNotification extends AppCompatActivity {
    TextView a,b,c,d,e,f,g;
    Button b1;
    String provideruserId,userId,Name,Address,phone,providerName,providerPhone,providerJob,providerAddress;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    AlertDialog.Builder AlertDialogBuilder;
    ProgressDialog progressDialog;
    ImageView imageView;
    StorageReference storageReference;
//    String title = "Sure",message = "Hello";
//    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customernotification);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

//        Title=findViewById(R.id.Title);
//        UserTB = findViewById(R.id.UserID);
//        Message=findViewById(R.id.Message);
//        send=findViewById(R.id.button13);
//        String userId = "hHjJSm6F3Eewt8ivnhymHY31Qjp1";

        a = findViewById(R.id.fName20Id);
        b = findViewById(R.id.Phone20Id);
        c = findViewById(R.id.Thikna20Id);
        d = findViewById(R.id.lingo20Id);
        e = findViewById(R.id.job20Id);
        f = findViewById(R.id.charge20Id);
        g = findViewById(R.id.cJob20Id);


        b1 = findViewById(R.id.Request20Id);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.profileImage20Id);

        userId = firebaseAuth.getCurrentUser().getUid();

        provideruserId = getIntent().getStringExtra("Uid").toString();
        providerAddress = getIntent().getStringExtra("ProviderAddress").toString();
        providerJob = getIntent().getStringExtra("ProviderJob").toString();
        providerName = getIntent().getStringExtra("ProviderName").toString();
        providerPhone = getIntent().getStringExtra("ProviderPhone").toString();

        DocumentReference doc = firebaseFirestore.collection("users").document(userId);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    Address = (value.getString("ADDRESS"));
                }
            }
        });


        StorageReference profileRef = storageReference.child("providers/"+provideruserId
                +"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

        DocumentReference doc1 = firebaseFirestore.collection("providers").document(provideruserId);
        doc1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    a.setText(value.getString("Full_Name"));
                    b.setText(value.getString("Mobile_No"));
                    c.setText(value.getString("Permanent_Address"));
                    d.setText(value.getString("GENDER"));
                    e.setText(value.getString("Job"));
                    f.setText(value.getString("Charge"));
                    g.setText(value.getString("Completed_Job"));
                }
            }
        });
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuilder = new AlertDialog.Builder(v.getContext());
                AlertDialogBuilder.setTitle(R.string.Title);
                AlertDialogBuilder.setMessage(R.string.message);
                AlertDialogBuilder.setIcon(R.drawable.ic_baseline_person_24);

                progressDialog.show();
                AlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Notification notification = new NotificationCompat.Builder(CustomerNotification.this, CHANNEL_1_ID)
//                                .setSmallIcon(R.drawable.user)
//                                .setContentTitle(title)
//                                .setContentText(message)
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                                .build();
//                        notificationManager.notify(1, notification);

                        DocumentReference documentReference = firebaseFirestore.collection("providers").document(provideruserId);
                        Map<String, Object> user = new HashMap<>();
                        user.put("CustomerUserId", userId);
                        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DocumentReference documentReference2 = firebaseFirestore.collection("users").document(userId);
                                Map<String, Object> user2 = new HashMap<>();
                                user2.put("ProviderUserId", provideruserId);
                                documentReference2.update(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        DocumentReference documentReference1 = firebaseFirestore.collection("usersrequest").document(userId).collection("request").document(provideruserId);
                                        Map<String, Object> user1 = new HashMap<>();
                                        user1.put("ProviderName", providerName);
                                        user1.put("ProviderPhone", providerPhone);
                                        user1.put("ProviderAddress", providerAddress);
                                        user1.put("ProviderJob", providerJob);
                                        user1.put("ProviderCharge", "pending");
                                        user1.put("Request", "Processing");
                                        user1.put("Status", "pending");

                                        documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("TAG", "onSuccess" + userId);

                                                finish();
                                                if (progressDialog.isShowing()) {
                                                    progressDialog.dismiss();
                                                }

                                                Intent intent = new Intent(CustomerNotification.this, CustomerHome.class);
                                                Toast.makeText(CustomerNotification.this, "Request is Succesfully Sent", Toast.LENGTH_LONG).show();
                                                intent.putExtra("ADDRESS", Address);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CustomerNotification.this, "Okay", Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = AlertDialogBuilder.create();
                alertDialog.show();

            }
        });
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CustomerNotification.this,CustomerHome.class);
//                intent.putExtra("ADDRESS",Address);
//                startActivity(intent);
//            }
//        });




    }


//        UserTB.setText(userId);

//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase.getInstance().getReference().child("tokens").child(userId).child("tokens").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String tokens=dataSnapshot.getValue(String.class);
//                        sendNotifications(tokens, Title.getText().toString().trim(),Message.getText().toString().trim());
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//            }
//        });
//        UpdateToken();
//    }
//    private void UpdateToken(){
//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken= FirebaseInstanceId.getInstance().getToken();
//        Token token= new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
//    }
//
//    public void sendNotifications(String usertoken, String title, String message) {
//        Data data = new Data(title, message);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                        Toast.makeText(CustomerNotification.this, "Failed ", Toast.LENGTH_LONG);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
}