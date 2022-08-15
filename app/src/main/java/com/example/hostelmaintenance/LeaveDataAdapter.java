package com.example.hostelmaintenance;


import static com.example.hostelmaintenance.R.color.colorAccent;
import static com.example.hostelmaintenance.R.color.green1;
import static com.example.hostelmaintenance.R.color.textcolor;
import static com.example.hostelmaintenance.R.color.yellow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaveDataAdapter extends RecyclerView.Adapter<LeaveDataAdapter.ViewHolder> {

    private Context con;
    public List<GetLeaveData> getLeaveDataList;

    public LeaveDataAdapter(Context con, List<GetLeaveData> getLeaveDataList) {
        this.con = con;
        this.getLeaveDataList = getLeaveDataList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_comp_row,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(getLeaveDataList.get(position).getStudent_Name());
         holder.std_l_from.setText(getLeaveDataList.get(position).getLeave_From() + " ");
        holder.std_l_to.setText(getLeaveDataList.get(position).getLeave_to() + " ");
        holder.std_course.setText(getLeaveDataList.get(position).getStudent_Course());
        holder.std_enroll.setText(getLeaveDataList.get(position).getStudent_Enrollment());

        if(getLeaveDataList.get(position).getVerified_CC()==0){
            holder.leave_stat.setText("Pending");
        }
        else if(getLeaveDataList.get(position).getVerified_CC()==-1){
            holder.leave_stat.setText("Rejected");
            holder.leave_stat.setBackgroundColor(textcolor);
        }
        else if(getLeaveDataList.get(position).getVerified_CC()==1 && getLeaveDataList.get(position).getVerified_HOD()==0){
            holder.leave_stat.setText("CC");
            holder.leave_stat.setBackgroundColor(yellow);
        }
        else if(getLeaveDataList.get(position).getVerified_CC()==1 && getLeaveDataList.get(position).getVerified_HOD()==1 &&getLeaveDataList.get(position).getVerified_HW()==0){
            holder.leave_stat.setText("HOD");
            holder.leave_stat.setBackgroundColor(green1);
        }
        else if(getLeaveDataList.get(position).getVerified_CC()==1 && getLeaveDataList.get(position).getVerified_HOD()==1 &&getLeaveDataList.get(position).getVerified_HW()==1){
            holder.leave_stat.setText("On Leave");
            holder.leave_stat.setBackgroundColor(colorAccent);
        }
        else if(getLeaveDataList.get(position).getVerified_HW()==-1){
            holder.leave_stat.setText("Student In");
            holder.leave_stat.setBackgroundColor(green1);
        }

    }

    public int getItemCount() {
        return getLeaveDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView name;
        TextView std_l_from;
        TextView std_l_to;
        TextView std_course;
        TextView std_enroll;
        TextView leave_stat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            name= mView.findViewById(R.id.stud_name);
            std_l_from=mView.findViewById(R.id.leave_from);
            std_l_to=mView.findViewById(R.id.leave_to);
            std_course=mView.findViewById(R.id.stud_course);
            std_enroll=mView.findViewById(R.id.enrollment);
            leave_stat=mView.findViewById(R.id.status_leave);

        }
    }

}