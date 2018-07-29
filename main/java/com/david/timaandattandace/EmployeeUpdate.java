package com.david.timaandattandace;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.david.timaandattandace.database.Database;
import com.david.timaandattandace.model.EmpBean;

import java.util.ArrayList;

public class EmployeeUpdate extends RecyclerView.Adapter<EmployeeUpdate.MyHolder> {
    ArrayList<EmpBean> arrayList;
    //  ItemClickListener mClickListener;
    int s;

    public EmployeeUpdate(ArrayList<EmpBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_employee_list, parent, false);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(EmployeeUpdate.MyHolder holder, int position) {
        EmpBean empBean = arrayList.get(position);
        String first = String.valueOf(empBean.getName().charAt(0));


        holder.labelId.setText(first);
        holder.labelName.setText(empBean.getName() + " " + empBean.getEmplname());
        /*holder.labelId.setText(String.valueOf(empBean.getEmpid()));
        holder.labelName.setText(empBean.getName());*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView labelId, labelName;
        Context context;

        public MyHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            labelId = itemView.findViewById(R.id.txtid);
            labelName = itemView.findViewById(R.id.txtname);
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.add("Update").setOnMenuItemClickListener(menuItemClickListener);
                    contextMenu.add("Delete").setOnMenuItemClickListener(menuItemClickListener);
                    // AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) ;

                }
            });

        }

        MenuItem.OnMenuItemClickListener menuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = getAdapterPosition();
                final EmpBean empBean = arrayList.get(position);

                if (item.getTitle().equals("Update")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setTitle("Update Employee");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.partial_update_employee);
                    final AppCompatImageButton appclose = dialog.findViewById(R.id.appclose1);
                    final AppCompatButton appclear = dialog.findViewById(R.id.appclear1);
                    final AppCompatButton appupdate = dialog.findViewById(R.id.appupdate);
                    final AppCompatSpinner appspinner = dialog.findViewById(R.id.appspinner1);
                    final AppCompatEditText appfname = dialog.findViewById(R.id.appfname1);
                    final AppCompatEditText applname = dialog.findViewById(R.id.applname1);
                    final AppCompatEditText apppin = dialog.findViewById(R.id.apppin1);
                    appfname.setText(empBean.getName());
                    applname.setText(empBean.getEmplname());
                    apppin.setText(empBean.getPin());
                    Log.e("Shift", String.valueOf(empBean.getShifted()));
                    s = empBean.getShifted();
                    Log.e("Shift", String.valueOf(s));
                    appspinner.setSelection(s);
                    appspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            s = i;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.appupdate:
                                    if (TextUtils.isEmpty(appfname.getText().toString())) {
                                        Toast.makeText(context, "Enter First Name", Toast.LENGTH_SHORT).show();
                                    } else if (TextUtils.isEmpty(applname.getText().toString())) {
                                        Toast.makeText(context, "Enter Last Name", Toast.LENGTH_SHORT).show();
                                    } else if (TextUtils.isEmpty(apppin.getText().toString())) {
                                        Toast.makeText(context, "Enter Pin", Toast.LENGTH_SHORT).show();
                                    } else if (s == 0) {
                                        Toast.makeText(context, "Select Shift", Toast.LENGTH_SHORT).show();
                                    } else {
                                        EmpBean empBean1 = new EmpBean();
                                        empBean1.setEmpid(empBean.getEmpid());
                                        empBean1.setName(appfname.getText().toString());
                                        empBean1.setEmplname(applname.getText().toString());
                                        empBean1.setPin(apppin.getText().toString());
                                        empBean1.setShifted(s);
                                        Database database = new Database(context);
                                        database.emp_update(empBean1);
                                        Toast.makeText(context, "Employee detail updated...", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        context.startActivity(new Intent(view.getContext(), InsertUpdateActivity.class));
                                    }
                                    break;
                                case R.id.appclear1:
                                    appfname.setText(null);
                                    applname.setText(null);
                                    apppin.setText(null);
                                    appspinner.setSelection(0);
                                    break;
                                case R.id.appclose1:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };
                    appclose.setOnClickListener(clickListener);
                    appclear.setOnClickListener(clickListener);
                    appupdate.setOnClickListener(clickListener);
                    dialog.show();
                }
                if (item.getTitle().equals("Delete")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are You Sure Delete This " + empBean.getName() + " Emp Detail");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Database database = new Database(context);
                            database.emp_delete(empBean.getEmpid());
                            Toast.makeText(context, "Delete The Detail", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, InsertUpdateActivity.class));
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Not Delete Detail", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return true;
            }
        };

    }
}
