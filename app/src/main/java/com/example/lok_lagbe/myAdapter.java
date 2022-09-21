package com.example.lok_lagbe;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {


    ArrayList<Model> dataList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();



    public myAdapter(ArrayList<Model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {


        holder.Name.setText(dataList.get(position).getFull_Name());
        String Name = dataList.get(position).getFull_Name();

//        holder.Phone.setText(dataList.get(position).getMobile_No());
        String Phone = dataList.get(position).getMobile_No();

        holder.Address.setText(dataList.get(position).getPermanent_Address());
        String Address = dataList.get(position).getPermanent_Address();

//        holder.Work.setText(dataList.get(position).getJob());
        String Job = dataList.get(position).getJob();

        holder.charge.setText(dataList.get(position).getCharge());
        holder.CompletedJOb.setText(dataList.get(position).getCompleted_Job());
        holder.rate.setText(dataList.get(position).getRating());

        String userId = dataList.get(position).getUserId();




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CustomerNotification.class);
                intent.putExtra("Uid",userId);
                intent.putExtra("ProviderAddress",Address);
                intent.putExtra("ProviderJob",Job);
                intent.putExtra("ProviderName",Name);
                intent.putExtra("ProviderPhone",Phone);
//                intent.putExtra("ProviderCharge",charge);
                v.getContext().startActivity(intent);

            }
        });
}

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void filterList(ArrayList<Model> filteredList) {
        dataList = filteredList;
        notifyDataSetChanged();
    }


    class myViewHolder extends RecyclerView.ViewHolder{

            TextView Name,Phone,Address,Work,charge,CompletedJOb,rate;

            public myViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);

                Name = itemView.findViewById(R.id.nameId);
                Address = itemView.findViewById(R.id.BashaId);
                charge = itemView.findViewById(R.id.kotoRakhbeId);
                CompletedJOb = itemView.findViewById(R.id.completedJobId);
                rate = itemView.findViewById(R.id.rateDekhabeId);


            }
        }

}
