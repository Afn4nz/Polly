package com.example.polly.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.polly.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    TextView post, vote ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        post = findViewById(R.id.postpoll);
        vote =findViewById(R.id.voteonpoll);



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), mypolls.class);
                startActivity(intent);

            }
        });

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), NearPollsActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settingmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent  = new Intent(getApplicationContext(), App_Setting.class);
        startActivity(intent);

        return true;
    }

}
