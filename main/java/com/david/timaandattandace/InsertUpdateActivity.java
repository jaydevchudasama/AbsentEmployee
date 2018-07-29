package com.david.timaandattandace;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.david.timaandattandace.database.Database;
import com.david.timaandattandace.model.EmpBean;

import java.util.ArrayList;

public class InsertUpdateActivity extends AppCompatActivity {

    ImageView imageViewBack;
    ImageView imageViewAdd;
    RecyclerView recy_update;
    ArrayList<EmpBean> arrayList;
    int s = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_update);

        Toolbar mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        imageViewAdd = findViewById(R.id.image_add);
        imageViewBack = findViewById(R.id.image_back);
        recy_update = findViewById(R.id.recy_update);

        final Database database = new Database(getApplicationContext());
        arrayList = database.display();
        if (arrayList.size() != 0) {
            EmployeeUpdate employeeAdapter = new EmployeeUpdate(arrayList);
            recy_update.setAdapter(employeeAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recy_update.setLayoutManager(layoutManager);
            DividerItemDecoration Decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            recy_update.addItemDecoration(Decoration);
            //  registerForContextMenu(recy_update);
        }

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertUpdateActivity.this, EmployeeListActivity.class));
                finish();
            }
        });

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertUpdateActivity.this, AdminAcivity.class);
                startActivity(intent);
                finish();


               /* final Dialog dialog = new Dialog(InsertUpdateActivity.this);
                dialog.setTitle("Add Employee");
                dialog.setContentView(R.layout.partial_add_employee);
                dialog.setCanceledOnTouchOutside(false);

                dialog.show();
*/
                /*final AppCompatImageButton appclose = dialog.findViewById(R.id.appclose);
                final AppCompatButton appadd = dialog.findViewById(R.id.appadd);
                final AppCompatButton appclear = dialog.findViewById(R.id.appclear);
                final AppCompatSpinner appspinner = dialog.findViewById(R.id.appspinner);
                final AppCompatEditText appfname = dialog.findViewById(R.id.appfname);
                final AppCompatEditText applname = dialog.findViewById(R.id.applname);
                final AppCompatEditText apppin = dialog.findViewById(R.id.apppin);
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
                            case R.id.appadd:
                                if (TextUtils.isEmpty(appfname.getText().toString())) {
                                    Toast.makeText(InsertUpdateActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(applname.getText().toString())) {
                                    Toast.makeText(InsertUpdateActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(apppin.getText().toString())) {
                                    Toast.makeText(InsertUpdateActivity.this, "Enter Pin", Toast.LENGTH_SHORT).show();
                                } else if (s == 0) {
                                    Toast.makeText(InsertUpdateActivity.this, "Select Shift", Toast.LENGTH_SHORT).show();
                                } else {
                                    EmpBean empBean = new EmpBean();
                                    empBean.setName(appfname.getText().toString());
                                    empBean.setEmplname(applname.getText().toString());
                                    empBean.setPin(apppin.getText().toString());
                                    empBean.setShifted(s);
                                    database.adddataemp(empBean);
                                    dialog.dismiss();
                                    final Database database = new Database(getApplicationContext());
                                    arrayList = database.display();
                                    if (arrayList.size() != 0) {
                                        EmployeeUpdate employeeAdapter = new EmployeeUpdate(arrayList);
                                        recy_update.setAdapter(employeeAdapter);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                        recy_update.setLayoutManager(layoutManager);
                                        DividerItemDecoration Decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                                        recy_update.addItemDecoration(Decoration);
                                        //  registerForContextMenu(recy_update);
                                    }
                                }
                                break;
                            case R.id.appclear:
                                appfname.setText(null);
                                applname.setText(null);
                                apppin.setText(null);
                                appspinner.setSelection(0);
                                break;
                            case R.id.appclose:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                appadd.setOnClickListener(clickListener);
                appclear.setOnClickListener(clickListener);
                appclose.setOnClickListener(clickListener);
                dialog.show();*/
            }
        });
    }
}
