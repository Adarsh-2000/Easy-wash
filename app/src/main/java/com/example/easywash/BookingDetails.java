package com.example.easywash;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class BookingDetails extends AppCompatActivity {
    TextInputLayout mdate,mtime,mname,mphone,mcar;

    Button b1;
    String canId;
    FirebaseFirestore fstore;

    private Object Tag;
    private String TAG;
    FirebaseFirestore fStore;
    String userIde;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking_details );

        mname = findViewById( R.id.bdename );
        mdate = findViewById( R.id.bddate );
        mtime = findViewById( R.id.bdtime );
        mcar = findViewById( R.id.bdcarno );
        mphone = findViewById( R.id.bdphone );

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        userIde = mAuth.getCurrentUser().getUid();
        final FirebaseUser user = mAuth.getCurrentUser();

        final DocumentReference documentReference = fStore.collection("Bookings").document(userIde);
        documentReference.addSnapshotListener( this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mname.getEditText().setText(documentSnapshot.getString("name"));
                mdate.getEditText().setText(documentSnapshot.getString("date"));
                mtime.getEditText().setText(documentSnapshot.getString("time"));
                mcar.getEditText().setText(documentSnapshot.getString("carno"));
                mphone.getEditText().setText(documentSnapshot.getString("phone"));


            }
        });


        //-------------------------------------------------------
        //Cancel button

        b1 = findViewById( R.id.b110 );
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                canId = mAuth.getCurrentUser().getUid();// this method for retreving the data
                DocumentReference documentReference = fstore.collection("Bookings").document(userIde);//this also
                Map<String,Object> user = new HashMap<>(); // using#map method data store
                user.put("can","Canceled");


                documentReference.set(user).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d( TAG, "onSuccess: user Profile is Created for "+ userIde);

                    }
                });
                //Toast.makeText(BookingDetails.this," Your Booking Was Canceled ",Toast.LENGTH_SHORT).show();
                openDialog();
            }

        });


    }

    private void openDialog() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Car Wash");
        builder.setMessage("Your Booking Was Sucessfully Canceled...");
        // add a button
        builder.setPositiveButton("OK", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
