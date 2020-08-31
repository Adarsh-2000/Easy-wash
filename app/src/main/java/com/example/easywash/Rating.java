package com.example.easywash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Rating extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_rating );

        final RatingBar ratingBar = (RatingBar) findViewById( R.id.ratingBar );
        Button submit = (Button) findViewById( R.id.button20 );
        final TextView textView = (TextView) findViewById( R.id.textView10 );
        ratingBar.setRating( load() );

        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                textView.setText( " Your given rating is here :" + ratingBar.getRating() );
                Toast.makeText(Rating.this," Your Given Rating Are Saved ",Toast.LENGTH_SHORT).show();
                //Toast toast = Toast.makeText( Rating.this,"Your given rating are save" ,Toast.LENGTH_SHORT);
               // View toastView = toast.getView();
               // textView.setTextColor( Color.GREEN );
                ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        textView.setText( ""+  v  );
                        float f = 0;
                        save( f );
                    }
                } );

            }
        } );

    }
    public void save (float f){
        SharedPreferences sharedPreferences = getSharedPreferences( "folder" ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat( "rating",f );
        editor.commit();
    }

    public  float load(){
        SharedPreferences sharedPreferences = getSharedPreferences( "folder",MODE_PRIVATE );
        float f = sharedPreferences.getFloat( "rating",0f )  ;
        return f;

    }
}
