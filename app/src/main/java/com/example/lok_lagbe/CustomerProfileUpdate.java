package com.example.lok_lagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CustomerProfileUpdate extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Button button;
    private EditText fNam,phon,db,lin;
    private RadioGroup gender;
    private RadioButton radioButton;
    private Spinner spinner,spinner1,spinner2;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customeprofileupdate);

        Intent data = getIntent();
        String fuName = data.getStringExtra("FullName");
        String phune = data.getStringExtra("Phone_Number");
        String jonmo = data.getStringExtra("Date Of Birth");
        String lngo = data.getStringExtra("GENDER");
        String address = data.getStringExtra("ADDRESS");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        button = (Button) findViewById(R.id.changeDataId);

        fNam = (EditText) findViewById(R.id.firstName10Id);
        phon = (EditText) findViewById(R.id.MobileNumber10Id);
        db = (EditText) findViewById(R.id.db10Id);
        lin = (EditText) findViewById(R.id.lingo10Id);

        fNam.setText(fuName);
        phon.setText(phune);
        db.setText(jonmo);
        lin.setText(lngo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = firebaseFirestore.collection("users").document(user.getUid());
                Map<String,Object> user = new HashMap<>();
                user.put("FullName",fNam.getText().toString());
                user.put("Phone_Number",phon.getText().toString());
                user.put("Date Of Birth", db.getText().toString());
                user.put("GENDER",lin.getText().toString());
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        Intent intent = new Intent(CustomerProfileUpdate.this,CustomerProfile.class);
                        intent.putExtra("ADDRESS", address);
                        startActivity(intent);
                    }
                });

            }
        });




    }
}