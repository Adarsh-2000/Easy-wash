package com.example.easywash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private Button resetPassword;
    private TextInputLayout passwordEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forget_password );

        passwordEmail = findViewById(R.id.etPasswordEmail);
        resetPassword = findViewById( R.id.btnPasswordReset );
        mAuth = FirebaseAuth.getInstance();

        // forgot password link genreate
        resetPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getEditText().getText().toString().trim();

                if (useremail.equals( "" )){
                    Toast.makeText(ForgetPassword.this,"Please Enter Your Register Email Id", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail( useremail ).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText( ForgetPassword.this, "Password Reset Email Was Sent!" , Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity( new Intent( ForgetPassword.this,Login.class ) );
                            }else {
                                Toast.makeText( ForgetPassword.this, "Error in sending Password Reset Email" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    } );
                }
            }
        } );
    }
}
