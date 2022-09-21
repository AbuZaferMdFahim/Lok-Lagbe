package com.example.lok_lagbe;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProviderRatingAdapter extends RecyclerView.Adapter<ProviderRatingAdapter.myViewHolder> {
    ArrayList<ProviderRatingModel> dataList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();



    public ProviderRatingAdapter(ArrayList<ProviderRatingModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public ProviderRatingAdapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.providerratingcard, parent, false);
        return new ProviderRatingAdapter.myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull ProviderRatingAdapter.myViewHolder holder, int position) {

        holder.Name.setText(dataList.get(position).getName());
        holder.Rate.setText(dataList.get(position).getRate());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void filterList(ArrayList<ProviderRatingModel> filteredList) {
        dataList = filteredList;
        notifyDataSetChanged();
    }


    class myViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Rate;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.raterNameId);
            Rate = itemView.findViewById(R.id.raterRateId);
        }
    }
}
