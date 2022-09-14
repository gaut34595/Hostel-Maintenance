package com.example.hostelmaintenance;

import static com.example.hostelmaintenance.R.color.green1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

public class LeaveVerifyAdapter extends RecyclerView.Adapter<LeaveVerifyAdapter.MyViewHolder> implements Filterable{
    private Context con;
    public ArrayList<GetLeaveData> leaveDatalist;
    public ArrayList<GetLeaveData> leaveDatalistFull;
    private OnLeaveListener mOnLeaveListener;
    public LeaveVerifyAdapter(Context con, ArrayList<GetLeaveData> leaveDatalist, OnLeaveListener monLeaveListener) {
        this.con = con;
        this.leaveDatalistFull= leaveDatalist;
        this.leaveDatalist = new ArrayList<>(leaveDatalistFull);
        this.mOnLeaveListener=monLeaveListener;

    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_comp_row,parent,false);
        return new MyViewHolder(v,mOnLeaveListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(leaveDatalist.get(position).getStudent_Name());
        holder.std_l_from.setText(leaveDatalist.get(position).getLeave_From() + " ");
        Log.d("------>>>>>>>>>>",leaveDatalist.get(position).getLeave_From());

        holder.std_l_to.setText(leaveDatalist.get(position).getLeave_to() + " ");
        holder.std_course.setText(leaveDatalist.get(position).getStudent_Course());
        holder.std_enroll.setText(leaveDatalist.get(position).getStudent_Enrollment());

        if(leaveDatalist.get(position).getVerified_CC()==0){
            holder.leave_stat.setText("Pending");
        }
        else if(leaveDatalist.get(position).getVerified_CC()==-1 && leaveDatalist.get(position).getVerified_HOD()==0){
            holder.leave_stat.setText("Rejected");
            holder.leave_stat.setBackgroundColor(R.color.red);
        }
        else if(leaveDatalist.get(position).getVerified_CC()==-1 && leaveDatalist.get(position).getVerified_HOD()==-1){
            holder.leave_stat.setText("Rejected by Principal Sir");
            holder.leave_stat.setBackgroundColor(green1);
        }
        else if(leaveDatalist.get(position).getVerified_CC()==1 && leaveDatalist.get(position).getVerified_HOD()==0){
            holder.leave_stat.setText("CC");
            holder.leave_stat.setBackgroundColor(R.color.yellow);
        }
        else if(leaveDatalist.get(position).getVerified_CC()==1 && leaveDatalist.get(position).getVerified_HOD()==1 &&leaveDatalist.get(position).getVerified_HW()==0){
            holder.leave_stat.setText("HOD");
            holder.leave_stat.setBackgroundColor(R.color.color_login_button);
        }
        else if(leaveDatalist.get(position).getVerified_HW()==1 && leaveDatalist.get(position).getGate_Validation_Out()==0){
            holder.leave_stat.setText("GP Generated");
            holder.leave_stat.setBackgroundColor(R.color.detail_color);
        }
        else if(leaveDatalist.get(position).getVerified_HW()==1 && leaveDatalist.get(position).getGate_Validation_Out()==1){ //&& leaveDatalist.get(position).getVerified_HW()==1 && leaveDatalist.get(position).getVerified_CC()==1 && leaveDatalist.get(position).getVerified_HOD()==1){
            holder.leave_stat.setText("Student Out(Gate)");
            holder.leave_stat.setBackgroundColor(R.color.purple_500);
        }
        else if(leaveDatalist.get(position).getVerified_HW()==1 && leaveDatalist.get(position).getGate_Validation_In()==1){
            holder.leave_stat.setText("Student In(Gate)");
            holder.leave_stat.setBackgroundColor(green1);
        }
        else if(leaveDatalist.get(position).getVerified_HW()==-1 && leaveDatalist.get(position).getGate_Validation_In()==-1){
            holder.leave_stat.setText("Student In");
            holder.leave_stat.setBackgroundColor(R.color.purple_700);
        }
    }

    @Override
    public int getItemCount() {
        return leaveDatalist.size();
    }

    @Override
    public Filter getFilter() {

        return leavefilter;
    }
    private  final  Filter leavefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetLeaveData> filteredleave = new ArrayList<>();
            if(charSequence== null || charSequence.length()==0){
                filteredleave.addAll(leaveDatalistFull);
            }else{
                String leavepattern = charSequence.toString().toLowerCase().trim();
                for(GetLeaveData ldata : leaveDatalistFull){
                    if(ldata.Student_Name.toLowerCase().contains(leavepattern) ||
                            ldata.Student_Enrollment.toLowerCase().contains(leavepattern)){

                        filteredleave.add(ldata);
                    }
                }
            }
            FilterResults leaveResults = new FilterResults();
            leaveResults.values = filteredleave;
            leaveResults.count = filteredleave.size();
            return leaveResults;

        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults leaveResults) {
            leaveDatalist.clear();
            leaveDatalist.addAll((ArrayList)leaveResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        TextView name;
        TextView std_l_from;
        TextView std_l_to;
        TextView std_course;
        TextView std_enroll;
        TextView leave_stat;
        OnLeaveListener onLeaveListener;
        public MyViewHolder(@NonNull View itemView, OnLeaveListener onLeaveListener) {
            super(itemView);
            mView=itemView;
            this.onLeaveListener=onLeaveListener;
            name= mView.findViewById(R.id.stud_name);
            std_l_from=mView.findViewById(R.id.leave_from);
            std_l_to=mView.findViewById(R.id.leave_to);
            std_course=mView.findViewById(R.id.stud_course);
            std_enroll=mView.findViewById(R.id.enrollment);
            leave_stat=mView.findViewById(R.id.status_leave);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
                    onLeaveListener.OnLeaveClick(getAdapterPosition());
        }
    }
    public interface OnLeaveListener{
        void OnLeaveClick(int position);
    }
}
