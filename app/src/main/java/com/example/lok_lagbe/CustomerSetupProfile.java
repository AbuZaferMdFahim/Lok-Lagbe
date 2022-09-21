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

public class CustomerSetupProfile extends AppCompatActivity {
    private Button button,date;
    private EditText fName,lName,customer;
    private DatePickerDialog datePickerDialog;
    private RadioGroup gender;
    private RadioButton radioButton;
    private Spinner spinner,spinner1,spinner2;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    public String userId,DateOfBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customersetupprofile);

        initDatePicker();

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        button = (Button) findViewById(R.id.saveDataId);
        date = (Button) findViewById(R.id.datePicker10Id);

        fName = (EditText) findViewById(R.id.firstNameId);
        lName = (EditText) findViewById(R.id.lastNameId);
        customer = (EditText) findViewById(R.id.customerphoneId);


        gender = (RadioGroup) findViewById(R.id.radioGroupId);

        date.setText(getTodaysDate());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FirstName = fName.getText().toString().trim();
                if(FirstName.isEmpty()){
                    fName.setError("First Name Can Not be Empty");
                }
                String LastName = lName.getText().toString().trim();
                if(LastName.isEmpty()){
                    lName.setError("Last Name Can Not be Empty");
                }
                String Phone = customer.getText().toString().trim();
                if(Phone.isEmpty()){
                    customer.setError("Phone Number Can Not be Empty");
                }
                DateOfBirth = date.getText().toString().trim();
                if(DateOfBirth.isEmpty()){
                    date.setError("Birth Field Can Not be Empty");
                }
                int SelectedId = gender.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(SelectedId);
                String Gender = radioButton.getText().toString().trim();
                if(Gender.isEmpty()){
                    radioButton.setError("Gender Can Not be Empty");
                }

                String FullName = FirstName +" " + LastName;
                userId = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = firestore.collection("users").document(userId);

                Map<String, Object> user = new HashMap<>();
                user.put("FullName",FullName);
                user.put("Date Of Birth", DateOfBirth);
                user.put("GENDER",Gender);
                user.put("Phone_Number",Phone);
                user.put("UserId",userId);
                user.put("ADDRESS","null");
                user.put("ProviderUserId",null);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "onSuccess" +userId);
//                        DocumentReference documentReference1 = firestore.collection("usersmessages").document(userId).collection("messages").document();
//                        Map<String, Object> user1 = new HashMap<>();
//                        user1.put("Subject","null");
//                        user1.put("Message", "null");

                        storeToken();
                        startActivity(new Intent(CustomerSetupProfile.this,CustomerLocation.class));
                        Toast.makeText(getApplicationContext(),"Successfully Save Your Data",Toast.LENGTH_LONG).show();

                    }
                });
            }

        });

    }
    public void storeToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult();
                            DocumentReference documentReference = firestore.getInstance().collection("tokens").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("tokens",token);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "onSuccess" + userId);
                                }
                            });
                        }
                        // Get new FCM registration token
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
}

//
//    private Button button, button1, date;
//    LinearLayout a, b;
//    private DatePickerDialog datePickerDialog;
//    private EditText fName, lName, email, thikana, father, phone, Nid;
//    ;
//    private RadioGroup gender;
//    private RadioButton radioButton;
//    private Spinner spinner;
//    FirebaseAuth mAuth;
//    FirebaseFirestore firestore;
//    public String userId, DateOfBirth, FullName, Gender, Email;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_customersetupprofile);
//        initDatePicker();
//
//        mAuth = FirebaseAuth.getInstance();
//        firestore = FirebaseFirestore.getInstance();
//        button = (Button) findViewById(R.id.saveDataId);
//
//        fName = (EditText) findViewById(R.id.firstNameId);
//        lName = (EditText) findViewById(R.id.lastNameId);
//
//        date.setText(getTodaysDate());
//
//        gender = (RadioGroup) findViewById(R.id.radioGroupId);
//
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DateOfBirth = date.getText().toString().trim();
//                String FirstName = fName.getText().toString().trim();
//                String LastName = lName.getText().toString().trim();
//                FullName = FirstName + " " + LastName;
//                Email = email.getText().toString().trim();
//
//                int SelectedId = gender.getCheckedRadioButtonId();
//                radioButton = (RadioButton) findViewById(SelectedId);
//                Gender = radioButton.getText().toString().trim();
//                userId = mAuth.getCurrentUser().getUid();
//
//                DocumentReference documentReference = firestore.collection("users").document(userId);
//
//                Map<String, Object> user = new HashMap<>();
//                user.put("First Name",FirstName);
//                user.put("Last Name",LastName);
//                user.put("Date Of Birth", DateOfBirth);
//                user.put("GENDER",Gender);
//                user.put("UserId",userId);
//
//                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("TAG", "onSuccess" +userId);
//                        storeToken();
//                        startActivity(new Intent(CustomerSetupProfile.this,CustomerLocation.class));
//                        Toast.makeText(getApplicationContext(),"Successfully Save Your Data",Toast.LENGTH_LONG).show();
//
//                    }
//                });
//            }
//        });
//
//    }
//    private String getTodaysDate() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        month = month + 1;
//        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        return makeDateString(year, month, dayOfMonth);
//    }
//
//    private void initDatePicker() {
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                String a = makeDateString(dayOfMonth, month, year);
//                date.setText(a);
//            }
//        };
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        int style = AlertDialog.THEME_HOLO_LIGHT;
//        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, dayOfMonth);
//    }
//
//    private String makeDateString(int dayofMonth, int month, int year) {
//        return getMonthFormate(month) + " " + dayofMonth + " " + year;
//    }
//
//    private String getMonthFormate(int month) {
//        if (month == 1)
//            return "January";
//        if (month == 2)
//            return "February";
//        if (month == 3)
//            return "March";
//        if (month == 4)
//            return "April";
//        if (month == 5)
//            return "May";
//        if (month == 6)
//            return "June";
//        if (month == 7)
//            return "July";
//        if (month == 8)
//            return "August";
//        if (month == 9)
//            return "September";
//        if (month == 10)
//            return "October";
//        if (month == 11)
//            return "November";
//        if (month == 12)
//            return "December";
//        return "January";
//    }
//
//    public void storeToken(){
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (task.isSuccessful()) {
//                            String token = task.getResult();
//                            DocumentReference documentReference = firestore.getInstance().collection("tokens").document(userId);
//                            Map<String, Object> user = new HashMap<>();
//                            user.put("tokens",token);
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Log.d("TAG", "onSuccess" + userId);
//                                }
//                            });
//                        }
//                        // Get new FCM registration token
//                    }
//                });
//    }
//}
