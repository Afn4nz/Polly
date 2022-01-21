package com.example.polly.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.polly.R;
import com.example.polly.database.DBHelper;
import com.example.polly.fragments.CoustmDilog;
import com.example.polly.models.Poll;
import com.example.polly.support.Global;
import com.google.android.material.snackbar.Snackbar;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class post extends LocationBaseActivity  {
    private int id;
    private String title;
    private String des;
    private String [] option;
    private int YES;
    private int NO;
    private boolean status;
    private String addresss;
    private String clocation;
    public static int  i;
    LocationManager locationManager;

    Button submit;
    EditText titleEtv, descEtv;

    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        submit = findViewById(R.id.submit);

        titleEtv = findViewById(R.id.ptitle);
                descEtv =findViewById(R.id.pdes);

        initLocation();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Runtime permissions
                if (ContextCompat.checkSelfPermission(post.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(post.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },100);
                }

                // get locaion
                String [] j ={"YES", "NO"};
                Poll p = new Poll (titleEtv.getText().toString(), descEtv.getText().toString(),addresss);
                boolean s = DBHelper.getInstance().insertpoll(p);
                Snackbar.make(v, "the Poll is posted on "+ clocation  , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }



    @Override
    public String toString() {
        return "post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", option=" + Arrays.toString(option) +
                ", status=" + status +
                ", location='" + clocation + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitlle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String[] getOption() {
        return option;
    }

    public void setOption(String [] option) {
        this.option = option;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLocationString() {
        return clocation;
    }

    public void setLocation(String location) {
        this.clocation = location;
    }



    private void initLocation(){
        getLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        StringBuilder locationString = new StringBuilder();
        locationString.append("Longitude: "+location.getLongitude());
        locationString.append("\n");
        locationString.append("Latitude: "+location.getLatitude());
        Log.i(Global.TAG,"location : "+ locationString);

        try {
            Geocoder geocoder = new Geocoder(post.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            addresss =address;
            clocation = address;

            Log.i(Global.TAG,"address : "+ address);

        }catch (Exception e){
            e.printStackTrace();
        }

        dismissProgress();
    }

    @Override
    public void onLocationFailed(int type) {

    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Location permission!", "Would you mind to turn GPS on?");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
