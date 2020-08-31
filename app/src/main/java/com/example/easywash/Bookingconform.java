package com.example.easywash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class Bookingconform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bookingconform );



        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent( Bookingconform.this, UserDashboard.class);
                startActivity( i );
                finish();
            }
        },2000 );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu( menu );

        getMenuInflater().inflate( R.menu.h, menu );
        // getMenuInflater().inflate( R.menu.activity_setting_drawer,menu );

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.pro_file)
        {
            Intent Intent = new Intent(Bookingconform.this, MainActivity.class);
            startActivity(Intent);


        }

        return true;
    }
}

