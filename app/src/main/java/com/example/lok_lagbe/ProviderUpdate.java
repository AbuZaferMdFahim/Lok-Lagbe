package com.example.lok_lagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProviderUpdate extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Button button;
    private EditText fNam,email,dob,lingo,telephone,address,job,fatherName,nidNo,charge;
    private RadioGroup gender;
    private RadioButton radioButton;
    private Spinner spinner,spinner1,spinner2;
    FirebaseAuth firebaseAuth;
    String Address;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerupdate);

        Intent data = getIntent();
        String fuName = data.getStringExtra("Full_Name");
        String emails = data.getStringExtra("Email");
        String jonmo = data.getStringExtra("Date_Of_Birth");
        String lngo = data.getStringExtra("GENDER");
        String bari = data.getStringExtra("Permanent_Address");
        String nid = data.getStringExtra("Nid_Number");
        String Abba = data.getStringExtra("Fathers_Name");
        String pone = data.getStringExtra("Mobile_No");
        String Charge = data.getStringExtra("Charge");
        Address = data.getStringExtra("ADDRESS");


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        button = (Button) findViewById(R.id.changeData13Id);

        fNam = (EditText) findViewById(R.id.firstName13Id);
        email = (EditText) findViewById(R.id.lastName13Id);
        dob = (EditText) findViewById(R.id.db13Id);
        lingo = (EditText) findViewById(R.id.lingo13Id);
        telephone = (EditText) findViewById(R.id.mb13Id);
        address = (EditText) findViewById(R.id.bari13Id);
        fatherName = (EditText) findViewById(R.id.abba13Id);
        nidNo = (EditText) findViewById(R.id.nation13Id);
        charge = (EditText) findViewById(R.id.kotoTakaId);

        fNam.setText(fuName);
        email.setText(emails);
        dob.setText(jonmo);
        lingo.setText(lngo);
        telephone.setText(pone);
        fatherName.setText(Abba);
        nidNo.setText(nid);
        address.setText(bari);
        charge.setText(Charge);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = firebaseFirestore.collection("providers").document(user.getUid());
                Map<String,Object> user = new HashMap<>();
                user.put("Full_Name",fNam.getText().toString());
                user.put("Email",email.getText().toString());
                user.put("Date_Of_Birth", dob.getText().toString());
                user.put("Fathers_Name",fatherName.getText().toString());
                user.put("Nid_Number",nidNo.getText().toString());
                user.put("Permanent_Address",address.getText().toString());
                user.put("Mobile_No", telephone.getText().toString());
                user.put("GENDER",lingo.getText().toString());
                user.put("Charge",charge.getText().toString());
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        Toast.makeText(getApplicationContext(),"Successfully Updated Your Data",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProviderUpdate.this,ProviderProfile.class);
                        intent.putExtra("ADDRESS",Address);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            Intent intent = new Intent(ProviderUpdate.this,ProviderProfile.class);
            intent.putExtra("ADDRESS",Address);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}