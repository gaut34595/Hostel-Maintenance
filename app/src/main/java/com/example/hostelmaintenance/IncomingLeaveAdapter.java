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

import com.example.hostelmaintenance.College.CC_College_Stats;

import java.util.ArrayList;

public class IncomingLeaveAdapter extends RecyclerView.Adapter<IncomingLeaveAdapter.MyViewHolder> implements Filterable {
    private Context con;
    public ArrayList<GetLeaveData> leaveDlist;
    public ArrayList<GetLeaveData> leaveDlistfull;
    private IncomingListener mIncomingListener;

    public IncomingLeaveAdapter(Context con, ArrayList<GetLeaveData> leaveDlist, IncomingListener mIncomingListener){
        this.con = con;
        this.leaveDlistfull = leaveDlist;
        this.leaveDlist = new ArrayList<>(leaveDlistfull);
        this.mIncomingListener = mIncomingListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_incoming_leave, parent, false);

        return new MyViewHolder(v,mIncomingListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(leaveDlist.get(position).getStudent_Name());
        holder.std_l_from.setText(leaveDlist.get(position).getLeave_From() + " ");
        Log.d("------>>>>>>>>>>", leaveDlist.get(position).getLeave_From());

        holder.std_l_to.setText(leaveDlist.get(position).getLeave_to() + " ");
        holder.std_course.setText(leaveDlist.get(position).getStudent_Course());
        holder.std_late.setText("Late by " + leaveDlist.get(position).getLate_by());

        if (leaveDlist.get(position).getVerified_CC() == 0) {
            holder.leave_stat.setText("Pending");
        } else if (leaveDlist.get(position).getVerified_CC() == -1) {
            holder.leave_stat.setText("Rejected");
            holder.leave_stat.setBackgroundColor(R.color.red);
        } else if (leaveDlist.get(position).getVerified_CC() == 1 && leaveDlist.get(position).getVerified_HOD() == 0) {
            holder.leave_stat.setText("CC");
            holder.leave_stat.setBackgroundColor(R.color.yellow);
        } else if (leaveDlist.get(position).getVerified_CC() == 1 && leaveDlist.get(position).getVerified_HOD() == 1 && leaveDlist.get(position).getVerified_HW() == 0) {
            holder.leave_stat.setText("HOD");
            holder.leave_stat.setBackgroundColor(R.color.color_login_button);
        } else if (leaveDlist.get(position).getVerified_CC() == 1 && leaveDlist.get(position).getVerified_HOD() == 1 && leaveDlist.get(position).getVerified_HW() == 1) {
            holder.leave_stat.setText("On Leave");
            holder.leave_stat.setBackgroundColor(R.color.detail_color);
        } else if (leaveDlist.get(position).getVerified_HW() == -1) {
            holder.leave_stat.setText("Student In");
            holder.leave_stat.setBackgroundColor(green1);
        }
    }

    @Override
    public int getItemCount() {
        return leaveDlist.size();
    }

    @Override
    public Filter getFilter() {
        return incomingfilter;
    }

    private final Filter incomingfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetLeaveData> filteredleave = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredleave.addAll(leaveDlistfull);
            } else {
                String leavepatt = charSequence.toString().toLowerCase().trim();
                for (GetLeaveData ldata : leaveDlistfull) {
                    if (ldata.Student_Name.toLowerCase().contains(leavepatt) || ldata.Student_Enrollment.toLowerCase().contains(leavepatt)) {
                        filteredleave.add(ldata);
                    }
                }
            }
            FilterResults incomres = new FilterResults();
            incomres.values = filteredleave;
            incomres.count = filteredleave.size();
            return incomres;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            leaveDlist.clear();
            leaveDlist.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View mView;
        TextView name;
        TextView std_l_from;
        TextView std_l_to;
        TextView std_course;
        TextView std_late;
        TextView leave_stat;
        IncomingListener incomingListener;

        public MyViewHolder(@NonNull View itemView ,IncomingListener mIncomingListener) {
            super(itemView);
            mView = itemView;
            this.incomingListener=mIncomingListener;
            name = mView.findViewById(R.id.stud_name);
            std_l_from = mView.findViewById(R.id.leave_from);
            std_l_to = mView.findViewById(R.id.leave_to);
            std_course = mView.findViewById(R.id.stud_course);
            std_late = mView.findViewById(R.id.late_by);
            leave_stat = mView.findViewById(R.id.status_leave);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            incomingListener.oLeaveClick(getAdapterPosition());

        }
    }
    public interface IncomingListener{
        void oLeaveClick(int position);
    }
    }
