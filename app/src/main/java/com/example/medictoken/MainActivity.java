package com.example.medictoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    EditText name,number;
    Button token;
    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(this);

        name = findViewById(R.id.name);
        token = findViewById(R.id.token);
        number = findViewById(R.id.number);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tokens_live");


        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameInput = name.getText().toString();
                String numberInput = number.getText().toString();

                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();

                if(nameInput.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter your Name",Toast.LENGTH_LONG).show();
                }
                else if(numberInput.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter your mobile number",Toast.LENGTH_LONG).show();
                }
                else{
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Please wait while we are making a token for you!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Log.d("Tag","In final else");
                    addData(nameInput,numberInput);
                }
            }
        });

    }

    private void addData(String nameInput, String numberInput) {

        TokenModel tokenModel = new TokenModel(nameInput,numberInput);
        long timeNow = System.currentTimeMillis();


        myRef.child(String.valueOf(timeNow)).setValue(tokenModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Token generated successfully!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Loginn.class));
        finish();
    }

}