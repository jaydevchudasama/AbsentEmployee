package com.david.timaandattandace.adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.david.timaandattandace.DashboardActivity;
import com.david.timaandattandace.R;
import com.david.timaandattandace.model.EmpBean;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyHolder> {

    ArrayList<EmpBean> arrayList;
//    ItemClickListener mClickListener;


    public EmployeeAdapter(ArrayList<EmpBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_employee_list, parent, false);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        EmpBean empBean = arrayList.get(position);

        String first = String.valueOf(empBean.getName().charAt(0));
        

        holder.labelId.setText(first);
        holder.labelName.setText(empBean.getName() + " " + empBean.getEmplname());
//        holder.labelId.setText(builder.toString());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView labelId, labelName;

        public MyHolder(View itemView) {
            super(itemView);
            labelId = itemView.findViewById(R.id.txtid);
            labelName = itemView.findViewById(R.id.txtname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            EmpBean empBean = arrayList.get(getAdapterPosition());
            int eid = empBean.getEmpid();
            Intent intent = new Intent(v.getContext(), DashboardActivity.class);

            intent.putExtra("pos", getAdapterPosition());
            v.getContext().startActivity(intent);
//            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

    }


    /*public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }*/
}