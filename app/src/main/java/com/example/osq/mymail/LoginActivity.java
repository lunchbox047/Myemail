package com.example.osq.mymail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button btnlogin;
    EditText idedt,pswedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        idedt=(EditText)findViewById(R.id.idedt);
        pswedt=(EditText)findViewById(R.id.pswedt);

        btnlogin=(Button)findViewById(R.id.login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tosend=new Intent(LoginActivity.this,WriteActivity.class);
                tosend.putExtra("user_id",idedt.getText().toString());
                tosend.putExtra("user_psw",pswedt.getText().toString());
                startActivity(tosend);
            }
        });
    }
}
