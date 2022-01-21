package com.example.polly.activities;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.recyclerview.widget.RecyclerView;

import com.example.polly.R;
import com.example.polly.adapters.NearPollsAdapter;
import com.example.polly.database.DBHelper;
import com.example.polly.support.Global;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

import java.util.List;
import java.util.Locale;

public class NearPollsActivity extends LocationBaseActivity {
    private ProgressDialog progressDialog;
    private RecyclerView pollsList;
    private NearPollsAdapter adapter;

    private String addresss = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        adapter = new NearPollsAdapter(this);
        pollsList = (RecyclerView) findViewById(R.id.pollsList);
        pollsList.setAdapter(adapter);
    }

    private void populateNearestPolls(){
        adapter.setData(DBHelper.getInstance().getNearestPolls(addresss));
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
            Geocoder geocoder = new Geocoder(NearPollsActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            addresss =address;

            Log.i(Global.TAG,"address : "+ address);

        }catch (Exception e){
            e.printStackTrace();
        }

        populateNearestPolls();

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
        else {
            initLocation();
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
