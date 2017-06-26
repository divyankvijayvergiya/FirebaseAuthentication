package com.example.dell.firebaseauth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etemail,etpass;
    private Button btregister;
    private TextView textalreadysignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

       if(firebaseAuth.getCurrentUser()!=null){

               //profile activity here
               finish();
           startActivity(new Intent(getApplicationContext(), ProfileActivity.class));


       }



        progressDialog=new ProgressDialog(this);
        etemail = (EditText) findViewById(R.id.etemail);
        etpass = (EditText) findViewById(R.id.etpass);
        textalreadysignin= (TextView) findViewById(R.id.textsigninalready);
        btregister = (Button) findViewById(R.id.btregister);
        btregister.setOnClickListener(this);
        textalreadysignin.setOnClickListener(this);
    }
    private void registeruser(){
        String email=etemail.getText().toString().trim();
        String password=etpass.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter the email",Toast.LENGTH_SHORT).show();
            //stopping the function executing further
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT).show();
            //stopping the function executing further
            return;
        }
        //if validations are ok
        //we will first show a progress bar
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //task is successfull user can successfully registered and loggedin
                    //we will start the profile activity here




                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                }
                else{
                    Toast.makeText(MainActivity.this,"could not Registered succesfully please try again..",Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==btregister){
            registeruser();
        }
        if (view==textalreadysignin){
            //will open login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}