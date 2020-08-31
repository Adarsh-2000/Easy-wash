package com.example.easywash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1000;

    //varibels
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_main );

        //animation
        topAnim = AnimationUtils.loadAnimation( this,R.anim.top_animation );
        bottomAnim = AnimationUtils.loadAnimation( this,R.anim.bottom_animation );


        image = findViewById( R.id.imageView );
        //logo = findViewById( R.id.textView );
        slogan = findViewById( R.id.textView2 );

        image.setAnimation( topAnim );
       // logo.setAnimation( bottomAnim );
        slogan.setAnimation( bottomAnim );

        //GO TO NEXT ACTIVITY
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( MainActivity.this,Register.class);
                startActivity( intent );
                finish();

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(slogan,"logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( MainActivity.this,pairs );
                startActivity( intent,options.toBundle() );
            }
        },SPLASH_SCREEN);
    }
}

