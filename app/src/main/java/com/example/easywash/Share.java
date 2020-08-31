package com.example.easywash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_share );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sh, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.share_button:
                Intent sharingIntent = new Intent( Intent.ACTION_SEND );
                sharingIntent.setType( "text/plain" );
                String shareBody = BuildConfig.APPLICATION_ID+"\n\n";
                String shareSubject = "Your Subject here";

                sharingIntent.putExtra( Intent.EXTRA_TEXT,shareBody );
                sharingIntent.putExtra( Intent.EXTRA_SUBJECT,shareSubject );


                startActivity( Intent.createChooser( sharingIntent, "Share Using" ) );
                break;
        }

        return super.onOptionsItemSelected( item );
    }
}
