package com.example.easywash;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Nightmode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nightmode );


        Toast toast = Toast.makeText( getApplicationContext(), "Coming Soon......", Toast.LENGTH_SHORT );
        toast.setGravity( Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0 );
        View view = toast.getView();
        toast.show();

        //  getDelegate().setLocalNightMode( AppCompatDelegate.MODE_NIGHT_AUTO );

        Switch switch1 = findViewById( R.id.switch1 );
        switch1.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getDelegate().setLocalNightMode( AppCompatDelegate.MODE_NIGHT_YES );
                } else {
                    getDelegate().setLocalNightMode( AppCompatDelegate.MODE_NIGHT_NO );
                }
            }
        } );
    }
}