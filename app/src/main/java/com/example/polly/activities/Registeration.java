package com.example.polly.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polly.R;
import com.example.polly.database.DBHelper;

public class Registeration extends AppCompatActivity {

    EditText username, password , confirm;
    Button register ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration);

        username=findViewById(R.id.Rusername);
        password=findViewById((R.id.Rpassword));
        confirm=findViewById(R.id.confirm);

        register=findViewById(R.id.Registerbutton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.equals("")||password.equals("")||confirm.equals(""))
                    Toast.makeText(Registeration.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();

                if(password.getText().toString().equals(confirm.getText().toString())){
                    boolean insert=DBHelper.getInstance().insertUsers(username.getText().toString(),password.getText().toString());
                    if(insert){
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent); // home page for user
                        Toast.makeText(Registeration.this,"Welcome To Polly!" ,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Registeration.this,"Database Error!" ,Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Registeration.this,"Password Does not match!" ,Toast.LENGTH_SHORT).show();
                }
            }
        });


        //if the user click on new poll the location will be updated

}


    }

