package com.example.lok_lagbe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.myViewHolder> {


    ArrayList<ProviderModel> dataList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public ProviderAdapter(ArrayList<ProviderModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.providercard, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {
        holder.Name.setText(dataList.get(position).getCustomerName());
        holder.Phone.setText(dataList.get(position).getCustomerPhone());
        holder.Address.setText(dataList.get(position).getCustomerAddress());
        holder.order.setText(dataList.get(position).getRequest());
        holder.WorkStatus.setText(dataList.get(position).getStatus());
        holder.charge.setText(dataList.get(position).getCharge());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

            TextView Name,Phone,Address,WorkStatus,order,charge;

            public myViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                Name = itemView.findViewById(R.id.customerNameId);
                Phone = itemView.findViewById(R.id.customerPhone10Id);
                Address = itemView.findViewById(R.id.customerBashaId);
                order = itemView.findViewById(R.id.providerorderId);
                WorkStatus = itemView.findViewById(R.id.providerWorkStatusId);
                charge = itemView.findViewById(R.id.customerChargeId);
            }
        }

//    Context context;
//    ArrayList<Model> userArrayList;
//
//    public Adapter(Context context, ArrayList<Model> userArrayList) {
//        this.context = context;
//        this.userArrayList = userArrayList;
//    }
//
//    @NotNull
//    @Override
//    public Adapter.MyviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
//        return new MyviewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull Adapter.MyviewHolder holder, int position) {
//        Model model = userArrayList.get(position);
//
//        holder.Name.setText(model.Full_Name);
//        holder.Phone.setText(model.Mobile_No);
//        holder.Address.setText(model.Permanent_Address);
//        holder.Work.setText(model.Job);
//        holder.fName.setText(model.fName);
//        holder.dob.setText(model.dob);
//        holder.Address.setText(model.Permanent_Address);
//        holder.email.setText(model.email);
//        holder.fName.setText(model.fName);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return userArrayList.size();
//    }
//    public static class MyviewHolder extends RecyclerView.ViewHolder{
//
//        TextView Name,Phone,Address,Work,dob,fName,gen,email,nid;
//
//        public MyviewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            Name = itemView.findViewById(R.id.nameId);
//            Phone = itemView.findViewById(R.id.numberId);
//            Address = itemView.findViewById(R.id.BashaId);
//            Work = itemView.findViewById(R.id.workId);
//            dob = itemView.findViewById(R.id.jonmuId);
//            fName = itemView.findViewById(R.id.abbarNameId);
//            gen = itemView.findViewById(R.id.shonaId);
//            email = itemView.findViewById(R.id.malId);
//            nid = itemView.findViewById(R.id.nIdenId);
//        }
//    }

}
