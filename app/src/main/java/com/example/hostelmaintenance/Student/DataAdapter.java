package com.example.hostelmaintenance.Student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelmaintenance.GetComplaintData;
import com.example.hostelmaintenance.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    public List<GetComplaintData> getComplaintDataList;
    public  DataAdapter(Context context,List <GetComplaintData> getComplaintDataList){
        this.getComplaintDataList=getComplaintDataList;
        this.context=context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            View mView ;
            TextView comp_type;
            TextView comp_title;
            TextView comp_date;
            TextView comp_status;
            TextView comp_roomNo;
            TextView comp_time;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            mView=itemView;
            comp_type = mView.findViewById(R.id.status_complain_type);
            comp_title= mView.findViewById(R.id.status_complain_title);
            comp_date = mView.findViewById(R.id.status_complain_date);
            comp_status=mView.findViewById(R.id.status_complain);
            comp_roomNo=mView.findViewById(R.id.status_roomNo);
            comp_time=mView.findViewById(R.id.comp_time);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_comp_row,parent,false);

        return new ViewHolder(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.comp_type.setText(getComplaintDataList.get(position).getComplaint_Type());
        holder.comp_title.setText(getComplaintDataList.get(position).getComplaint_Title());
        holder.comp_date.setText(getComplaintDataList.get(position).getComplaint_Date());
        holder.comp_roomNo.setText(getComplaintDataList.get(position).getRoom_No());
        holder.comp_time.setText(getComplaintDataList.get(position).getComplaint_Time());


        if(getComplaintDataList.get(position).getComplaint_Status()==0) {
            holder.comp_status.setText("Pending");
        }else{
            holder.comp_status.setText("Resolved");
            holder.comp_status.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {

        return getComplaintDataList.size();
    }
}
