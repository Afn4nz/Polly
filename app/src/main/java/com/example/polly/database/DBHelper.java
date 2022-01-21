package com.example.polly.database;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.polly.activities.post;
import com.example.polly.models.Poll;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class DBHelper extends SQLiteOpenHelper{

    public static String UserName;
    private static DBHelper instance = null;
    private static Context context;

    private DBHelper(Context context) {
        super(context, "Polly.db", null, 1);
    }

    public static void init(Context c){
        context = c;
    }

    public static DBHelper getInstance(){
        if (instance == null){
            instance = new DBHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("CREATE TABLE Users(Username TEXT primary key , Password TEXT  ) ");
        DB.execSQL("CREATE TABLE poll (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, des TEXT, Location TEXT, status BOOLEAN, YES INTEGER, NOO INTEGER, Usernamee TEXT, FOREIGN KEY (Usernamee) REFERENCES Users(Username)) ");
       // DB.execSQL("CREATE TABLE result ( option TEXT, nvotes INTEGER, idd INTEGER, FOREIGN KEY (idd) REFERENCES poll(id))");
        DB.execSQL("CREATE TABLE pollvoter (vUsername TEXT, pollid INTEGER, YES INTEGER, NOO INTEGER, FOREIGN KEY (vUsername) REFERENCES Users(Username), FOREIGN KEY (pollid) REFERENCES poll(id), PRIMARY KEY (vUsername, pollid))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop table if exists Users");
        DB.execSQL("drop table if exists poll");
        DB.execSQL("drop table if exists pollvoter");

    }

    public Boolean insertUsers(String username , String password ){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("Username", username );
        contentValues.put("Password", password);

        long result=DB.insert("Users",null, contentValues );
        if(result==-1){
            return false;
        }

        return true;
    }

    public Boolean updateUsers(String username , String password ){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        Cursor cursor=DB.rawQuery("SELECT * FROM Users where username=?",new String[]{username});
        if(cursor.getCount()>0){
        long result=DB.update("Users", contentValues,"username=?", new String[]{username});
        if(result==-1){
            return false;
        }

        return true;}

        return false;
    }


    /* public Boolean deletePolls(String PollContentRow){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("SELECT * FROM Polls where Poll=?",new String[]{Poll});
        if(cursor.getCount()>0){
            long result=DB.update("Users", contentValues,"poll=?", new String[]{poll});
            if(result==-1){
                return false;
            }

            return true;}

        return false;
    }*/


    //will need it for retrieving result voting
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Users", null);
        return cursor;
     }
     
     
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});

        if(cursor.getCount()>0){
            UserName=username;
            return true;}
        else
            return false;
    }

    public Boolean insertpoll(Poll poll){

        SQLiteDatabase DB= this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put("title", poll.getTitle());
        contentValues.put("des", poll.getDesc());
        contentValues.put("Location" ,poll.getLocationString());
        contentValues.put("status" , poll.getStatus());
        contentValues.put("YES" , poll.getYes());
        contentValues.put("NOO" , poll.getNo());
        contentValues.put("Usernamee", UserName);

        long result=DB.insert("poll",null, contentValues );

        //DB.execSQL("CREATE TABLE poll (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, des TEXT, Location TEXT, status BOOLEAN, YES INTEGER, NOO INTEGER, Usernamee TEXT, FOREIGN KEY (Usernamee) REFERENCES Users(Username)) ");

        if(result==-1){


        return false;
        }

        return true;
    }

    public Boolean updatePoll(Poll poll){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("status",poll.isVoted());
        contentValues.put("YES",poll.getYes());
        contentValues.put("NOO",poll.getNo());
        Cursor cursor=DB.rawQuery("SELECT * FROM poll where id=?",new String[]{poll.getIdString()});

        if(cursor.getCount()>0){
            long result=DB.update("poll", contentValues,"id= ?", new String[]{poll.getIdString()});

            if(result==-1){
                return false;
            }

            return true;}

        return false;
    }

    public Boolean  insertvPoll(Poll poll) {
        int yesnum =-1;
        int nonum=-1;
int y=0;
int n=0;
        if(poll.getYes()==1){
            y++;}
        else{
            n++;}
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from pollvoter where pollid = ? ", new String[]{poll.getIdString()});
        if(cursor.getCount() == 0){

            ContentValues contentValues = new ContentValues();
            contentValues.put("vUsername", UserName);
            contentValues.put("pollid", poll.getIdString());
            contentValues.put("YES", y);
            contentValues.put("NOO", n);
            long result = DB.insert("pollvoter", null, contentValues);

            if (result == -1) {
                return false;
            }

            return true;

        }else{
            while (cursor.moveToNext()){
            yesnum = Integer.parseInt(cursor.getString(2));
            nonum =Integer.parseInt(cursor.getString(3));
            if(poll.getYes()==1){
                yesnum++;}
            else{
            nonum++;}}
        }
        // DB.execSQL("CREATE TABLE pollvoter (vUsername TEXT, pollid INTEGER, YES INTEGER, NOO INTEGER, FOREIGN KEY (vUsername) REFERENCES Users(Username),  FOREIGN KEY (pollid) REFERENCES poll(id))");


                ContentValues contentValues = new ContentValues();
                contentValues.put("vUsername", UserName);
                contentValues.put("pollid", poll.getIdString());
                contentValues.put("YES", yesnum);
                contentValues.put("NOO", nonum);
                long result = DB.insert("pollvoter", null, contentValues);

            if (result == -1) {
                return false;
            }

            return true;


    }



    public Cursor readAllData() {


       // String query = "SELECT * FROM poll where Usernamee=?\",new String[]{UserName}";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){

            cursor=  db.rawQuery("Select * from poll ", null);

        }
        return cursor;
    }

    public Cursor readAllVotes() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String>  _id = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select pollid from pollvoter where vUsername = ? ", new String[]{UserName});


        return cursor;
    }

    public Cursor readAllVotes2(String idd) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from pollvoter where pollid = ? ", new String[]{idd});


        if(db != null){
           return cursor ;
        }
        return cursor;
    }
    public Cursor readAllVotes3(String idd) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from poll where pollid = ? ", new String[]{idd});


        if(db != null){
            return cursor ;
        }
        return cursor;
    }

    public ArrayList<Poll> getNearestPolls(String userLocation){

        ArrayList<Poll> nearestPolls = new ArrayList<>();

        Cursor cursor = readAllData();
        if(cursor.getCount() == 0){
            return nearestPolls;

        }
        else{

            while (cursor.moveToNext()){

                if (cursor.getString(3) != null) {
                    if (userLocation.equals(cursor.getString(3)) || userLocation.contains(cursor.getString(3))) {
                        Poll poll = new Poll();
                        poll.setId(Integer.parseInt(cursor.getString(0)));
                        poll.setTitle(cursor.getString(1));
                        poll.setLocationString(cursor.getString(3));

                        nearestPolls.add(poll);
                    }
                }
            }
        }

        return nearestPolls;
    }

    public Cursor readonerow(String pid) {
        Cursor cursor = null;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        if(MyDB != null){
         cursor = MyDB.rawQuery("Select * from poll where id = ?", new String[] {pid});}

            return cursor;

    }



}
