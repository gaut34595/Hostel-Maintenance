package com.example.hostelmaintenance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdap extends RecyclerView.Adapter<MyAdap.MyViewHolder> implements Filterable {

    private Context con;
    public ArrayList<GetComplaintData> complArrayList;
    public ArrayList<GetComplaintData> complArrayListFull;

    public MyAdap(Context con, ArrayList<GetComplaintData> compArrayList) {
        this.con = con;
        this.complArrayListFull = compArrayList;
        this.complArrayList= new ArrayList<>(complArrayListFull);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_comp_row,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.comp_type.setText(complArrayList.get(position).getComplaint_Type());
        holder.comp_date.setText(complArrayList.get(position).getComplaint_Date());
        holder.comp_title.setText(complArrayList.get(position).getComplaint_Title());
        holder.comp_roomNo.setText(complArrayList.get(position).getRoom_No());
        holder.comp_time.setText(complArrayList.get(position).getComplaint_Time());
        if(complArrayList.get(position).getComplaint_Status()==0) {
            holder.comp_status.setText("Pending");
        }else{
            holder.comp_status.setText("Resolved");
            holder.comp_status.setBackgroundColor(con.getResources().getColor(R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return complArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return complaintfilter;
    }
    private final Filter complaintfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetComplaintData>  filteredComplaint = new ArrayList<>();
            if(charSequence== null || charSequence.length() == 0){
                filteredComplaint.addAll(complArrayListFull);
            }else{
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for(GetComplaintData data : complArrayListFull){
                    if(data.Room_No.toLowerCase().contains(filterpattern) || data.Complaint_Type.toLowerCase().contains(filterpattern)){
                        filteredComplaint.add(data);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredComplaint;
            filterResults.count = filteredComplaint.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            complArrayList.clear();
            complArrayList.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView comp_type;
        TextView comp_title;
        TextView comp_date;
        TextView comp_status;
        TextView comp_roomNo;
        TextView comp_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            comp_type = mView.findViewById(R.id.status_complain_type);
            comp_title= mView.findViewById(R.id.status_complain_title);
            comp_date = mView.findViewById(R.id.status_complain_date);
            comp_status=mView.findViewById(R.id.status_complain);
            comp_roomNo=mView.findViewById(R.id.status_roomNo);
            comp_time = mView.findViewById(R.id.comp_time);
        }
    }
}

