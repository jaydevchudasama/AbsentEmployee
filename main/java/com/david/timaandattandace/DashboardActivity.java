package com.david.timaandattandace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.david.timaandattandace.database.Database;
import com.david.timaandattandace.model.EmpBean;

import java.nio.channels.InterruptedByTimeoutException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity {

    private TextView labelName;
    private ArrayList<EmpBean> arrayList;


    private ImageView imageViewBack;
    private ImageView imageViewClockIn;
    private ImageView imageViewClockOut;
    private ImageView imageViewHoliDays;
    private ImageView imageViewSickDays;
    Toolbar mTopToolbar;

    Database database;
    int p, id1, po;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        Intent intent = getIntent();

        /*String name = intent.getStringExtra("name");

        if (name != null) {
            labelName.setText(name);
        }*/


        po = intent.getIntExtra("pos", -1);
        if (po != -1) {
            p = po;
            database = new Database(this);
            arrayList = database.display();
            EmpBean empBean = arrayList.get(p);
            id1 = empBean.getEmpid();
        }
        init();


    }


    public void init() {
        labelName = findViewById(R.id.label_employee_name);
        imageViewBack = findViewById(R.id.image_back);
        imageViewClockIn = findViewById(R.id.image_clock_in);
        imageViewClockOut = findViewById(R.id.image_clock_out);
        imageViewHoliDays = findViewById(R.id.image_holidays);
        imageViewSickDays = findViewById(R.id.image_sickdays);
        clickEvent();
    }

    public void clickEvent() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.image_clock_in:

                        int no = database.clockin_out(id1);
                        if (no == 1) {
                            Toast.makeText(DashboardActivity.this, "already clock in", Toast.LENGTH_SHORT).show();

                        } else {
                            EmpBean clockinBean = new EmpBean();
                            clockinBean.setEmpid(id1);
                            clockinBean.setType1(1);
                            clockinBean.setDate1(DateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
                            clockinBean.setTime1(DateFormat.getTimeInstance().format(Calendar.getInstance().getTime()));
                            Database clockinDatabase = new Database(DashboardActivity.this);
                            clockinDatabase.adddatacheck(clockinBean);
                            Toast.makeText(DashboardActivity.this, "clock in", Toast.LENGTH_SHORT).show();
                            Intent clockinIntent = new Intent(DashboardActivity.this, EmployeeListActivity.class);
                            startActivity(clockinIntent);
                            overridePendingTransition(R.anim.right_out, R.anim.left_out);
                            finish();
                        }
                        break;

                    case R.id.image_clock_out:

                        int co = database.clockin_out(id1);
                        if (co == 1) {
                            EmpBean clockoutBean = new EmpBean();
                            clockoutBean.setEmpid(id1);
                            clockoutBean.setType1(2);
                            clockoutBean.setDate1(DateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
                            clockoutBean.setTime1(DateFormat.getTimeInstance().format(Calendar.getInstance().getTime()));
                            Database clockoutDatabase = new Database(DashboardActivity.this);
                            clockoutDatabase.adddatacheck(clockoutBean);
                            Toast.makeText(DashboardActivity.this, "clock out", Toast.LENGTH_SHORT).show();
                            Intent clockoutIntent = new Intent(DashboardActivity.this, EmployeeListActivity.class);
                            startActivity(clockoutIntent);
                            overridePendingTransition(R.anim.right_out, R.anim.left_out);
                            finish();

                        } else {

                            Toast.makeText(DashboardActivity.this, "clock in first", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.image_holidays:
                        String eid = String.valueOf(id1);
                        Intent intent = new Intent(DashboardActivity.this, HolidaysActivity.class);
                        intent.putExtra("posi", po);
                        intent.putExtra("hid", eid);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_out, R.anim.left_out);
//                        finish();

                        break;

                    case R.id.image_sickdays:

                        String sid = String.valueOf(id1);
                        Intent intent1 = new Intent(DashboardActivity.this, SickdaysActivity.class);
                        intent1.putExtra("posi", po);
                        intent1.putExtra("sid", sid);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.right_out, R.anim.left_out);
//                        finish();
                        break;

                    case R.id.image_back:
                        onBackPressed();
                        break;
                }

            }
        };


        imageViewClockIn.setOnClickListener(clickListener);
        imageViewClockOut.setOnClickListener(clickListener);
        imageViewHoliDays.setOnClickListener(clickListener);
        imageViewSickDays.setOnClickListener(clickListener);
        imageViewBack.setOnClickListener(clickListener);
    }
}