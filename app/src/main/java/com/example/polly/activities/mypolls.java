package com.example.polly.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polly.R;
import com.example.polly.database.DBHelper;
import com.example.polly.adapters.pollomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class mypolls extends AppCompatActivity {
    ArrayList<String> _id, _title, _location;
    ArrayList<Integer> _totalVotes,yesVotes,noVotes;
    pollomAdapter pollAdapt;
    RecyclerView recyclerView1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypolls);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), post.class);
                startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView1 = findViewById(R.id.r1);

    }
    void storeDataInArrays(){
        _id = new ArrayList<>();
        _title = new ArrayList<>();
        _location = new ArrayList<>();
        /*_totalVotes = new ArrayList<>();
        yesVotes = new ArrayList<>();
        noVotes = new ArrayList<>();*/
        Cursor cursor = DBHelper.getInstance().readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "you have no polls", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                _id.add(cursor.getString(0));
                _title.add(cursor.getString(1));
                _location.add(cursor.getString(3));
                /*yesVotes.add(cursor.getInt(5));
                noVotes.add(cursor.getInt(6));*/
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        storeDataInArrays();

        pollAdapt = new pollomAdapter(mypolls.this,this, _id, _title,  _location);
        recyclerView1.setAdapter(pollAdapt);
        recyclerView1.setLayoutManager(new LinearLayoutManager(mypolls.this));
    }
}
