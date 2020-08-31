package com.example.easywash;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    //EditText mFullName,mEmail,mPassword,mPhone;
    TextInputLayout mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        mFullName = findViewById(R.id.fullName);
        mEmail    = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhone    = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn    = findViewById(R.id.createText);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        // user was loged in or not ckeck
        // if user was login then send mainactivity otherwise reg user
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            finish();
        }
        // for fileds
        mRegisterBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getEditText().getText().toString().trim();
                final String password = mPassword.getEditText().getText().toString().trim();
                final String fullName = mFullName.getEditText().getText().toString();
                final String phone = mPhone.getEditText().getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Requried");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Requried");
                    return;
                }

                if (password.length() < 6 ){
                    mPassword.setError( "Password Must be >= 6 Characters" );
                    return;
                }

                if (phone.length() < 10 ){
                    mPhone.setError( "Enter a valid mobile No." );
                    return;
                }

                // for progress bar

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgressTintList( ColorStateList.valueOf( Color.GREEN));

                // register the user in  firebase

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // display accoun is created or not
                        if (task.isSuccessful()){
                            // send verfication email

                            /*


                            FirebaseUser  fuser = mAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener( new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText( Register.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT ).show();
                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d( TAG, "onFailure: Email not Sent " + e.getMessage() );
                                }
                            } );*/







                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userId);//this also
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("password",password);

                            documentReference.set(user).addOnSuccessListener( new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d( TAG, "onSuccess: user Profile is Created for "+ userId);

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        }else {
                            Toast.makeText(Register.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar .setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
        mLoginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        } );
    }
}

