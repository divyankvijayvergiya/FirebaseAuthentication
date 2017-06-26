package com.example.dell.firebaseauth;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView usertextemail;
    private Button btlogout;
    private DatabaseReference databaseReference;
    private EditText etname,etaddress;
    private Button savebutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        etname= (EditText) findViewById(R.id.editTextname);
        etaddress= (EditText) findViewById(R.id.editTextaddress);
        savebutton= (Button) findViewById(R.id.savebutton);

        usertextemail= (TextView) findViewById(R.id.usertextemail);
        usertextemail.setText("welcome "+user.getEmail());
        btlogout= (Button) findViewById(R.id.btlog);
        btlogout.setOnClickListener(this);
        savebutton.setOnClickListener(this);
    }
    private void saveUserInformation(){
        String name=etname.getText().toString().trim();
        String add=etaddress.getText().toString().trim();
        UserInformation userInformation=new UserInformation(name,add);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this,"Information Saved....",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if (v==btlogout) {
            firebaseAuth.signOut();

            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(v==savebutton){
            saveUserInformation();
        }


    }
}
