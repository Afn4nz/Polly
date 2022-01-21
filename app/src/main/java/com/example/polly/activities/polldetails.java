package com.example.polly.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polly.R;
import com.example.polly.database.DBHelper;
import com.example.polly.support.Global;

import java.util.ArrayList;

public class polldetails extends AppCompatActivity {
    String id, title, des, loca, yess, noo;
    TextView   t , d, dd, y , n ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polld);
        getAndSetIntentData();

    }
    void getAndSetIntentData(){
        if(Global.currentSelectedPoll != null){
            //Getting Data from Intent
            id = Global.currentSelectedPoll.getIdString();
            title = Global.currentSelectedPoll.getTitle();
            loca = Global.currentSelectedPoll.getLocationString();
            //Setting Intent Data
 t = findViewById(R.id.textView5);
 d = findViewById(R.id.textView6);
        dd=    findViewById(R.id.textView7);
        y= findViewById(R.id.YES);
                n=findViewById(R.id.NO);
        des="NOOOO";

            ArrayList<String> _id, desa, yes, no;
            desa = new ArrayList<>();
           _id = new ArrayList<>();
           yes=new ArrayList<>();
                   no =new ArrayList<>();
            Cursor cursor = DBHelper.getInstance().readAllData();
            Cursor cursor2= DBHelper.getInstance().readAllVotes2(id);
            if(cursor.getCount() == 0){
                Toast.makeText(this, "no description", Toast.LENGTH_SHORT).show();
            }else{
                while (cursor.moveToNext()){
                    _id.add(cursor.getString(0));
                    desa.add(cursor.getString(2));
                    //yes.add(cursor.getString(5));
                   //no.add(cursor.getString(6));
                    if(cursor.getString(0).equals(id)){
                        des=cursor.getString(2);
                    //yess =cursor.getString(5);
                     //noo = cursor.getString(6);
                    }
                }


        }
            yess="0";
            noo="0";
            if(cursor2.getCount() == 0){
                Toast.makeText(this, "no result", Toast.LENGTH_SHORT).show();
            }else{
                while (cursor2.moveToNext()){

                    yes.add(cursor2.getString(2));
                    no.add(cursor2.getString(3));
                    //if(cursor2.getString(0).equals(id)){
                       // des=cursor.getString(2);
                        yess =cursor2.getString(2);
                        noo = cursor2.getString(3);
                    //}
                }


            }







            t.setText(title);
            d.setText(loca);
            dd.setText(des);
            y.setText(yess);
            n.setText(noo);


        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    }
