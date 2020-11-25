package com.thefatherinc.gethoumie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.Calendar;

public class RegistrationViaEmailActivity extends AppCompatActivity {

    private EditText user_email;
    private EditText user_pass;
    private EditText user_name;
    private Button btn_registration;
    private String[] genders = {"Мужчина", "Женщина"};
    Spinner gender;
    private String gender_string;
    private TextView birth;
    Calendar date = Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_via_email);
        birth = (TextView) findViewById(R.id.reg_via_email_birth);
        gender = (Spinner) findViewById(R.id.reg_via_email_gender);

        user_email = (EditText) findViewById(R.id.reg_via_email_email);
        user_pass = (EditText) findViewById(R.id.reg_via_email_pass);
        user_name = (EditText) findViewById(R.id.reg_via_email_name);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        gender.setAdapter(adapter);
        gender.setSelection(0);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                gender_string = parent.getAdapter().getItem(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btn_registration = (Button) findViewById(R.id.reg_via_email_btn_registration);
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MyApplication)getApplication().getApplicationContext()).client.sendMessage("registration_use_email", new String[] {user_email.getText().toString(),
                    user_pass.getText().toString(), user_name.getText().toString(), birth.getText().toString(), gender_string});
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setDate(View v) {
        new DatePickerDialog(RegistrationViaEmailActivity.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birth.setText(String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear) + "." + String.valueOf(year));
        }
    };
}
