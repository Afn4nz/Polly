package com.example.polly.activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.polly.R;
import com.example.polly.database.DBHelper;
import com.example.polly.receivers.ReminderBroadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    TextView noAccount;
    Button signin;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signin = findViewById(R.id.SignInbutton);
        noAccount = findViewById(R.id.noAccount);
        //Register
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, Registeration.class);

                startActivity(i);
            }
        });


        //EditText

        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("") || password.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();


                Boolean checkuserpass = DBHelper.getInstance().checkusernamepassword(username.getText().toString(), password.getText().toString());
                if (checkuserpass) {
                    Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent); // home page for user
                } else {
                    Toast.makeText(MainActivity.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

