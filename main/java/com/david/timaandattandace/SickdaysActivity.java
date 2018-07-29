package com.david.timaandattandace;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.david.timaandattandace.database.Database;
import com.david.timaandattandace.model.EmpBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SickdaysActivity extends AppCompatActivity {


    private TextView labelFromDate;
    private TextView labelFromTime;
    private TextView labelToDate;
    private TextView labelToTime;
    private ImageView imageViewBack;
    private Button buttonSumit;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog time;
    String id, day;
    int po;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sickdays);

        Toolbar mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);


        Intent intent = getIntent();
        id = intent.getStringExtra("sid");
        po = intent.getIntExtra("posi", -1);

        init();

    }

    public void init() {
        imageViewBack = findViewById(R.id.image_back);
        labelFromDate = findViewById(R.id.label_from_date);
        labelFromTime = findViewById(R.id.label_from_time);
        labelToDate = findViewById(R.id.label_to_date);
        labelToTime = findViewById(R.id.label_to_time);
        buttonSumit = findViewById(R.id.button_submit);

        clickEvent();
    }

    public void clickEvent() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.label_from_date:
                        final TextView d = findViewById(view.getId());

                        date = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                //        updateLabel();
                                String myFormat = "MM/dd/yy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                d.setText(sdf.format(calendar.getTime()));
                            }
                        };

                        new DatePickerDialog(SickdaysActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                        break;

                    case R.id.label_from_time:
                        final TextView t = findViewById(view.getId());
                        int h = calendar.get(Calendar.HOUR);
                        int m = calendar.get(Calendar.MINUTE);

                        time = new TimePickerDialog(SickdaysActivity.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                t.setText(hourOfDay + ":" + minute);
                            }
                        }, h, m, true);
                        time.setTitle("Select Time");
                        time.show();
                        break;

                    case R.id.label_to_date:

                        final TextView todate = findViewById(view.getId());
                        date = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                //        updateLabel();
                                String myFormat = "MM/dd/yy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                todate.setText(sdf.format(calendar.getTime()));
                            }
                        };

                        new DatePickerDialog(SickdaysActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                        break;

                    case R.id.label_to_time:
                        final TextView totime = findViewById(view.getId());
                        int hh = calendar.get(Calendar.HOUR);
                        int mm = calendar.get(Calendar.MINUTE);

                        time = new TimePickerDialog(SickdaysActivity.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                totime.setText(hourOfDay + ":" + minute);
                            }
                        }, hh, mm, true);
                        time.setTitle("Select Time");
                        time.show();
                        break;

                    case R.id.button_submit:

                        EmpBean empBean = new EmpBean();
                        empBean.setEmpid(Integer.parseInt(id));
                        empBean.setType1(1);
                        empBean.setTo_date(labelToDate.getText().toString());
                        empBean.setFrom_date(labelFromDate.getText().toString());
                        empBean.setFrom_time(labelFromTime.getText().toString());
                        empBean.setTo_time(labelToTime.getText().toString());
                        Database db = new Database(SickdaysActivity.this);
                        db.adddataab(empBean);
                        Toast.makeText(SickdaysActivity.this, "Your SickDays Date From " + labelFromDate.getText().toString() + " To Date " + labelToDate.getText().toString(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SickdaysActivity.this, EmployeeListActivity.class));
                        finish();
                        break;
                    case R.id.image_back:
                        onBackPressed();
                        break;
                }
            }
        };

        labelFromTime.setOnClickListener(clickListener);
        labelFromDate.setOnClickListener(clickListener);
        labelToDate.setOnClickListener(clickListener);
        labelToTime.setOnClickListener(clickListener);
        imageViewBack.setOnClickListener(clickListener);
        buttonSumit.setOnClickListener(clickListener);
    }
}
