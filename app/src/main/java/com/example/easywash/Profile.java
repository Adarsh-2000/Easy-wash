package com.example.easywash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    TextInputLayout fullName,email,phone,password,full_name;//verifyMsg;
    // TextView ;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    // Button resendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );




        phone = findViewById(R.id.profilePhone);
        fullName= findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        password = findViewById(R.id.profilepassword);
        full_name = findViewById(R.id.profileName);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // resendCode = findViewById(R.id.resendCode);
        // verifyMsg = findViewById(R.id.verifyMsg);

        userId = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();
        // email not  vervefaction
        /*
        if(!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    user.sendEmailVerification().addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification Email Has Been Sent", Toast.LENGTH_SHORT ).show();
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d( "tag", "onFailure: Email not Sent " + e.getMessage() );
                        }
                    } );
                }
            } );

        }*/



        // retrevae the data from the database

        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener( this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.getEditText().setText(documentSnapshot.getString("phone"));
                fullName.getEditText().setText(documentSnapshot.getString("fName"));
                full_name.getEditText().setText(documentSnapshot.getString("fName"));
                email.getEditText().setText(documentSnapshot.getString("email"));
                password.getEditText().setText(documentSnapshot.getString("password"));
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();// logout user  then send to login activity
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
