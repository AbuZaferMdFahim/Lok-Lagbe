package com.example.lok_lagbe.SendNotificationPack;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Retrofit retrofit=null;

    public static Retrofit getClient(String url)
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
//progressDialog = new ProgressDialog(v.getContext());
//        progressDialog.setCancelable(false);
//
//        AlertDialogBuilder = new AlertDialog.Builder(v.getContext());
//
//        AlertDialogBuilder.setTitle(R.string.Title);
//        AlertDialogBuilder.setMessage(R.string.message);
//        AlertDialogBuilder.setIcon(R.drawable.ic_baseline_person_24);
//
//        AlertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface dialog, int which) {
//        DocumentReference documentReference = firebaseFirestore.collection("providers").document(provideruserId);
//        Map<String,Object> user = new HashMap<>();
//        user.put("CustomerUserId",userId);
//        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void unused) {
//        progressDialog.show();
//        DocumentReference documentReference2 = firebaseFirestore.collection("users").document(userId);
//        Map<String,Object> user2 = new HashMap<>();
//        user2.put("ProviderUserId",provideruserId);
//        documentReference2.update(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void unused) {
//        DocumentReference documentReference1 = firebaseFirestore.collection("usersrequest").document(userId).collection("request").document(provideruserId);
//        Map<String,Object> user1 = new HashMap<>();
//        user1.put("ProviderName",Name);
//        user1.put("ProviderPhone",Phone);
//        user1.put("ProviderAddress",Address);
//        user1.put("ProviderJob",Job);
//        user1.put("ProviderCharge",null);
//        user1.put("Request","Processing");
//        user1.put("Status",null);
//
//        documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void unused) {
//        Log.d("TAG", "onSuccess" + userId);
//
//
//        if (progressDialog.isShowing()) {
//        progressDialog.dismiss();
//        }
//        Intent intent = new Intent(v.getContext(), CustomerHome.class);
//        intent.putExtra("ADDRESS", Address);
//        v.getContext().startActivity(intent);
//        }
//        });
//        }
//        });
//        }
//        });
//        }
//        });
//
//        AlertDialog alertDialog = AlertDialogBuilder.create();
//        alertDialog.show();
//        }
//
//FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        String userId = firebaseAuth.getCurrentUser().getUid();
//        String provideruserId;
//        AlertDialog.Builder AlertDialogBuilder;
//        ProgressDialog progressDialog;