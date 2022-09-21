package com.example.lok_lagbe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProviderSetupProfile extends AppCompatActivity {

    private Button button, button1, date;
    LinearLayout a, b;
    private DatePickerDialog datePickerDialog;
    private EditText fName, lName, email, thikana, father, phone, Nid,chargeprovider;
    private RadioGroup gender;
    private RadioButton radioButton;
    private Spinner spinner;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    public String userId, DateOfBirth, FullName, Gender, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providersetupprofile);

        initDatePicker();

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        a = (LinearLayout) findViewById(R.id.luid);
        b = (LinearLayout) findViewById(R.id.lu1id);
        spinner = (Spinner) findViewById(R.id.JobId);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.Problem));

        button = (Button) findViewById(R.id.saveDataId);
        date = (Button) findViewById(R.id.datePickerId);
        button1 = (Button) findViewById(R.id.nextId);

        fName = (EditText) findViewById(R.id.firstNameId);
        lName = (EditText) findViewById(R.id.lastNameId);
        email = (EditText) findViewById(R.id.EmailId);
        thikana = (EditText) findViewById(R.id.ThikanaId);
        father = (EditText) findViewById(R.id.fatherId);
        phone = (EditText) findViewById(R.id.mobId);
        Nid = (EditText) findViewById(R.id.NidNoId);
        chargeprovider = (EditText) findViewById(R.id.ChargeId);

        date.setText(getTodaysDate());

        gender = (RadioGroup) findViewById(R.id.radioGroupId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateOfBirth = date.getText().toString().trim();
                String FirstName = fName.getText().toString().trim();
                String LastName = lName.getText().toString().trim();
                FullName = FirstName + " " + LastName;
                Email = email.getText().toString().trim();

                int SelectedId = gender.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(SelectedId);
                Gender = radioButton.getText().toString().trim();
                a.setVisibility(View.GONE);
                b.setVisibility(View.VISIBLE);

            }

        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Job = CountryData.Problem[spinner.getSelectedItemPosition()];
                String Address = thikana.getText().toString();
                if (Address.isEmpty()) {
                    thikana.setError("Address Can Not be Empty");
                }
                String Fathers_Name = father.getText().toString();
                if (Fathers_Name.isEmpty()) {
                    father.setError("Field Can Not be Empty");
                }
                String Phone = phone.getText().toString();
                if (Phone.isEmpty()) {
                    phone.setError("Phone can't be Empty");
                }

                String NidNo = Nid.getText().toString();
                if (NidNo.isEmpty()) {
                    Nid.setError("Nid Must be in the Field");
                }
                String charge = chargeprovider.getText().toString();
                if (charge.isEmpty()) {
                    chargeprovider.setError("Nid Must be in the Field");
                }

                userId = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = firestore.collection("providers").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("Full_Name", FullName);
                user.put("Email", Email);
                user.put("Date_Of_Birth", DateOfBirth);
                user.put("GENDER", Gender);
                user.put("Job", Job);
                user.put("Permanent_Address", Address);
                user.put("Fathers_Name", Fathers_Name);
                user.put("Mobile_No", Phone);
                user.put("Rating", "0.0");
                user.put("Nid_Number", NidNo);
                user.put("Charge", charge);
                user.put("CustomerUserId", null);
                user.put("Completed_Job", "0");
                user.put("UserId", userId);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "onSuccess" + userId);
//                        DocumentReference documentReference1 = firestore.collection("providershistory").document(userId).collection("history").document(userId);
//                        Map<String, Object> user1 = new HashMap<>();
//                        user1.put("CustomerName", "None");
//                        user1.put("CustomerAddress", "None");
//                        user1.put("CustomerPhone", "None");
//                        user1.put("Request", "None");
//                        user1.put("Charge", "None");
//                        user1.put("Status", "Completed");
//                        documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("TAG", "onSuccess" + userId);
//                                DocumentReference documentReference2 = firestore.collection("providersrating").document(userId).collection("rating").document(userId);
//                                Map<String, Object> user2 = new HashMap<>();
//                                user2.put("Name", "None");
//                                user2.put("Rate", "0.0");
//                                documentReference2.set(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Log.d("TAG", "onSuccess" + userId);
                        storeToken();
                        startActivity(new Intent(ProviderSetupProfile.this, ProviderSetupProfile1.class));
                        Toast.makeText(getApplicationContext(), "Successfully Save Your Data", Toast.LENGTH_LONG).show();
//                            }
//                        });
                    }
                });
            }
        });
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year, month, dayOfMonth);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String a = makeDateString(dayOfMonth, month, year);
                date.setText(a);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, dayOfMonth);
    }

    private String makeDateString(int dayofMonth, int month, int year) {
        return getMonthFormate(month) + " " + dayofMonth + " " + year;
    }

    private String getMonthFormate(int month) {
        if (month == 1)
            return "January";
        if (month == 2)
            return "February";
        if (month == 3)
            return "March";
        if (month == 4)
            return "April";
        if (month == 5)
            return "May";
        if (month == 6)
            return "June";
        if (month == 7)
            return "July";
        if (month == 8)
            return "August";
        if (month == 9)
            return "September";
        if (month == 10)
            return "October";
        if (month == 11)
            return "November";
        if (month == 12)
            return "December";
        return "January";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void storeToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult();

                            DocumentReference documentReference = firestore.collection("tokens").document(userId);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Token", token);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "onSuccess" + userId);
                                }
                            });
                        }
                    }
        });
    }
}