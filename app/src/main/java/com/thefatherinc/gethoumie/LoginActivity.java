package com.thefatherinc.gethoumie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

    private Button btn_to_registration_via_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_to_registration_via_email = (Button) findViewById(R.id.login_btn_to_reg);
        btn_to_registration_via_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationViaEmailActivity.class);
                startActivity(intent);
            }
        });
    }
}
