package com.david.timaandattandace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.david.timaandattandace.database.Database;
import com.david.timaandattandace.model.EmpBean;

public class AdminAcivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText textName;
    private EditText textLastName;
    private EditText textPin;
    private Button buttonSubmit;
    int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_acivity);
        Toolbar mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        init();


    }

    public void init() {
        spinner = findViewById(R.id.spinner);
        textName = findViewById(R.id.text_name);
        textLastName = findViewById(R.id.text_last_name);
        textPin = findViewById(R.id.text_pin);
        buttonSubmit = findViewById(R.id.button_submit);
        spinner.setOnItemSelectedListener(this);
        clickEvent();
    }

    public void clickEvent() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_submit:

                        String name = textName.getText().toString().trim();
                        String last_name = textLastName.getText().toString().trim();
                        String pin = textPin.getText().toString().trim();

                        if (TextUtils.isEmpty(name)) {
                            textName.setError("Enter Firstname");
                            textName.requestFocus();
                            break;
                        }
                        if (TextUtils.isEmpty(last_name)) {
                            textLastName.setError("Enter Lastname");
                            textLastName.requestFocus();
                            break;
                        }
                        if (TextUtils.isEmpty(pin)) {
                            textPin.setError("Enter Pin");
                            textPin.requestFocus();
                            break;
                        } else {
                            EmpBean empBean = new EmpBean();
                            empBean.setEmplname(textLastName.getText().toString());
                            empBean.setName(textName.getText().toString());
                            empBean.setPin(textPin.getText().toString());
                            empBean.setShifted(s);
                            Database database = new Database(AdminAcivity.this);
                            database.adddataemp(empBean);
                            startActivity(new Intent(AdminAcivity.this, EmployeeListActivity.class));
                        }

                        break;
                }

            }
        };
        buttonSubmit.setOnClickListener(clickListener);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
