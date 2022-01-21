package com.example.polly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polly.R;
import com.example.polly.database.DBHelper;
import com.example.polly.receivers.ReminderBroadcast;
import com.example.polly.support.Global;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.material.snackbar.Snackbar;

public class VoteActivity extends AppCompatActivity {

    LinearLayout parentView;
    TextView title, location, status;
    RadioGroup voteGroup;
    RadioButton yesBtn, noBtn;
    boolean n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        initViews();
    }

    private void initViews(){
        createNotificationChannel();

        title = (TextView) findViewById(R.id.poll_tv);
        location = (TextView) findViewById(R.id.poll_location_tv);
        status = (TextView) findViewById(R.id.status_tv);

        voteGroup = (RadioGroup) findViewById(R.id.voteGroup);
        yesBtn = (RadioButton)findViewById(R.id.yesVoteBtn);
        noBtn = (RadioButton)findViewById(R.id.noVoteBtn);

        parentView = (LinearLayout) findViewById(R.id.parentView);

        title.setText(Global.currentSelectedPoll.getTitle());
        location.setText(Global.currentSelectedPoll.getLocationString());


        if (Global.currentSelectedPoll.isVoted() != null) {
            status.setVisibility(View.VISIBLE);
            if (Global.currentSelectedPoll.isVoted()) {
                yesBtn.setChecked(true);
                noBtn.setChecked(false);
            } else {
                noBtn.setChecked(true);
                yesBtn.setChecked(false);
            }
        }

        voteGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    int radioButtonId = radioGroup.getCheckedRadioButtonId();

                    if (radioButtonId == R.id.yesVoteBtn){
                        yesBtn.setChecked(true);
                        noBtn.setChecked(false);
                        Global.currentSelectedPoll.setIsVoted(true);
                    }

                    if (radioButtonId == R.id.noVoteBtn){
                        noBtn.setChecked(true);
                        yesBtn.setChecked(false);

                        Global.currentSelectedPoll.setIsVoted(false);
                    }

                    updatePoll();
                }
            }
        });
    }

    private void updatePoll(){
        boolean s = DBHelper.getInstance().updatePoll(Global.currentSelectedPoll);
        
         n = DBHelper.getInstance().insertvPoll(Global.currentSelectedPoll);
        Snackbar.make(parentView, "Poll Posted on "+ Global.currentSelectedPoll.getLocationString() , Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        sendNotif();
        finish();
    }

    private void sendNotif() {
        if (n){
            Toast.makeText(this, "Voted! " , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (VoteActivity.this, ReminderBroadcast.class) ;
            PendingIntent pendingIntent= PendingIntent . getBroadcast(VoteActivity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE) ;
            long timeAt = System.currentTimeMillis();
            long twentyfour = 100000 * 864;

            long res=timeAt + twentyfour;
            alarmManager.set(AlarmManager.RTC_WAKEUP,res,pendingIntent);
    }else{Toast.makeText(this, "You Can Not Vote Twice! " , Toast.LENGTH_SHORT).show();}

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "VotingResultReminderChannel";
            String description = "Channel for voting result Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("VotingResult ", name, importance);
            channel.setDescription(description);
        }
    }
}