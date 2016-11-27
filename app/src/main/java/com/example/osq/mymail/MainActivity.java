package com.example.osq.mymail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button gotologin,gotosend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotologin=(Button)findViewById(R.id.tologin);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittologin=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(ittologin);
            }
        });

        gotosend=(Button)findViewById(R.id.tosendbtn);
        gotosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittosend=new Intent(MainActivity.this,WriteActivity.class);
                startActivity(ittosend);
            }
        });

    }
}
