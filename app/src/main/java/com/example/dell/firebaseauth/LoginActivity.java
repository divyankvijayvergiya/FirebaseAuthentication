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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Dell on 09-04-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btlogin;
    private EditText etemailogin, etpasslogin;
    private TextView textsignup,UserLogin;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actvitymain);
        firebaseauth=FirebaseAuth.getInstance();
        if(firebaseauth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog=new ProgressDialog(this);
        btlogin= (Button) findViewById(R.id.btlogin);
        etemailogin= (EditText) findViewById(R.id.etemaillogin);
        etpasslogin= (EditText) findViewById(R.id.etpasslogin);
        textsignup= (TextView) findViewById(R.id.textsignup);
        UserLogin= (TextView) findViewById(R.id.userlogin);

        btlogin.setOnClickListener(this);
        textsignup.setOnClickListener(this);
    }
    private void UserLogin(){
        String email=etemailogin.getText().toString().trim();
        String password=etpasslogin.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter the email",Toast.LENGTH_LONG).show();
            //stopping the function executing further
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_LONG).show();
            //stopping the function executing further
            return;
        }
        //if validations are ok
        //we will first show a progress bar
        progressDialog.setMessage("logging user...");
        progressDialog.show();
        firebaseauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //launch profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==btlogin){
            UserLogin();
        }
        if(v==textsignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}