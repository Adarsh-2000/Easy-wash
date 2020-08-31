package com.example.easywash;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Booking extends AppCompatActivity {

    public static final String TAG = "TAG";
    private static TextInputLayout mName;
    private static TextInputLayout mPhone;
    private static EditText DateEdit;
    private static EditText TimeEdit;
    private static EditText carNo;
    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    Button b1;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String userIde;

  //  FirebaseDatabase rootNode;
  //  DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking );
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (getIntent().hasExtra( "category" )) {
            Intent intent = new Intent( Booking.this, ReceiveNotificationActivity.class );
            intent.putExtra( "category", getIntent().getStringExtra( "category" ) );
            intent.putExtra( "brandId", getIntent().getStringExtra( "brandId" ) );
            startActivity( intent );
        }


        mName = findViewById( R.id.editTextName );
        mPhone = findViewById( R.id.ediTextphone );

        DateEdit = findViewById( R.id.editText1 );
        TimeEdit = findViewById( R.id.editText2 );
        carNo = findViewById( R.id.editText3 );
        b1 = findViewById( R.id.b1 );

        mRequestQue = Volley.newRequestQueue( this );
        FirebaseMessaging.getInstance().subscribeToTopic( "news" );


        DateEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showTruitonTimePickerDialog(v);
                showTruitonDatePickerDialog( v );
                // String date = DateEdit.getText().toString().trim();
            }
        } );

        TimeEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog( v );
                //showTruitonDatePickerDialog(v);
                //String time = TimeEdit.getText().toString().trim();
            }
        } );


        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // rootNode = FirebaseDatabase.getInstance();
                //reference = rootNode.getReference( "Booking" );

                 String date = DateEdit.getText().toString();
                 String time = TimeEdit.getText().toString();
                 String car = carNo.getText().toString();

                 String name = mName.getEditText().getText().toString();
                 String phone = mPhone.getEditText().getText().toString();


                if (TextUtils.isEmpty( date )) {
                    DateEdit.setError( "date is Requried" );
                    return;
                }

                if (TextUtils.isEmpty( time )) {
                    TimeEdit.setError( "Password is Requried" );
                    return;
                }

                if (TextUtils.isEmpty( name )) {
                    mName.setError( "name is Requried" );
                    return;
                }

                if (TextUtils.isEmpty( phone )) {
                    mPhone.setError( "phone is Requried" );
                    return;
                }

                if (TextUtils.isEmpty( car )) {
                    carNo.setError( "Registration Numer is Requried" );
                    return;
                }

                userIde = mAuth.getCurrentUser().getUid();// this method for retreving the data
                final DocumentReference documentReference = fStore.collection( "Bookings" ).document( userIde );
                //this also
                Map<String, Object> user = new HashMap<>(); // using#map method data store
                user.put( "name", name );
                user.put( "date", date );
                user.put( "time", time );
                user.put( "carno", car );
                user.put( "phone", phone );

                documentReference.set( user ).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d( TAG, "onSuccess: user Profile is Created for " + userIde );

                    }
                } );


               // UserHelper helper = new UserHelper( name, phone, car, date, time );
                //reference.child( phone ).setValue( helper );

                //startActivity(new Intent(getApplicationContext(),BookingNotifi.class));
                senNotification();
               // reference.child( date ).setValue( helper );


            }


        } );

    }
    // startActivity(new Intent(getApplicationContext(),Bookingconform.class));




        /*b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Bookingconform.class));

            }
        } ); */


    // -------------------------------------------------------------------------------------------------menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu( menu );

        getMenuInflater().inflate( R.menu.h, menu );
        // getMenuInflater().inflate( R.menu.activity_setting_drawer,menu );

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.boo_k) {
            Intent Intent = new Intent( Booking.this, BookingDetails.class );
            startActivity( Intent );


        } else if (item.getItemId() == R.id.pro_file) {
            Intent Intent = new Intent( Booking.this, Profile.class );
            startActivity( Intent );
        } else if (item.getItemId() == R.id.ni_m) {
            Intent Intent = new Intent( Booking.this, Nightmode.class );
            startActivity( Intent );
        }


        return true;
    }


    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new Booking.DatePickerFragment();
        newFragment.show( getSupportFragmentManager(), "datePicker" );
    }


    public void buttonClick(View view) {
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( "MyDate" );
        DatabaseReference myRef1 = database.getReference( "MyTime" );
        EditText editText = (EditText) findViewById( R.id.editText1 );
        EditText editText1 = (EditText) findViewById( R.id.editText2 );
        String message = editText.getText().toString();
        String message1 = editText1.getText().toString();
        myRef.setValue( message );
        myRef1.setValue( message1 );*/

        // startActivity( new Intent( getApplicationContext(), Bookingconform.class ) );


    }
    // Notification Sending Bar

    public void displayNotification(View view) {


    }

    //addValueEventListener(new ValueEventListener() {


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get( Calendar.YEAR );
            int month = c.get( Calendar.MONTH );
            int day = c.get( Calendar.DAY_OF_MONTH );

            //  DatePickerDialog and return it
            return new DatePickerDialog( getActivity(), this, year, month, day );
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            // date chosen by the user
            DateEdit.setText( day + "/" + (month + 1) + "/" + year );
        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new Booking.TimePickerFragment();
        newFragment.show( getSupportFragmentManager(), "timePicker" );
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get( Calendar.HOUR_OF_DAY );
            int minute = c.get( Calendar.MINUTE );

            // Create a  TimePickerDialog and return it
            return new TimePickerDialog( getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat( getActivity() ) );
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //  time chosen by the user
            //TimeEdit.setText( TimeEdit.getText() + "" + hourOfDay + ":" + minute );

            int hour = hourOfDay;
            int minutes = minute;
            String timeSet = "";
            if (hour > 12) {
                hour -= 12;
                timeSet = "PM";
            } else if (hour == 0) {
                hour += 12;
                timeSet = "AM";
            } else if (hour == 12) {
                timeSet = "PM";
            } else {
                timeSet = "AM";
            }

            String min = "";
            if (minutes < 10)
                min = "0" + minutes;
            else
                min = String.valueOf( minutes );

            // Append in a StringBuilder
            String aTime = new StringBuilder().append( hour ).append( ':' )
                    .append( min ).append( " " ).append( timeSet ).toString();
            TimeEdit.setText( aTime );
        }


        public String formatDate(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
            return sdf.format( date );
        }


    }


    private void senNotification() {


        JSONObject json = new JSONObject();
        try {
            json.put( "to", "/topics/" + "news" );
            JSONObject notificationObj = new JSONObject();
            notificationObj.put( "title", "Car Wash  Notification" );
            notificationObj.put( "body", "Car Wash Booking is successfully Booked..." );

            JSONObject extraData = new JSONObject();
            extraData.put( "brandId", "Audi s Series" );
            extraData.put( "category", "S7" );


            json.put( "notification", notificationObj );
            json.put( "data", extraData );


            JsonObjectRequest request = new JsonObjectRequest( Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d( "MUR", "onResponse: " );
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d( "MUR", "onError: " + error.networkResponse );
                }
            }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put( "content-type", "application/json" );
                    header.put( "authorization","key=AAAAICzVih8:APA91bEauPOtFWfjMIO4AQJfR0Gv0pBOr0MmKjBaEfbTFeKb6sgd5kjaFF4oWwuNHasiVbMEO7PCLMkgcEH1Yu4A2PskBfFqOMTWfjj83JU2e-4gB3m6sEqo7c0tBIshkrT98bUpxg9c" );
                    return header;
                }
            };
            mRequestQue.add( request );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startActivity( new Intent( getApplicationContext(), Bookingconform.class ) );

    }


}