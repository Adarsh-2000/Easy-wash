package com.example.easywash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPajeAdapter introViewPajeAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //make the actiity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // when this activity is about to launch  we need to check if openned before or not

        if(restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_intro);

        // hide action bar
     //   getSupportActionBar().hide();


        //ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animationn);


        // fill list screen

        final List<Screenitem> mList = new ArrayList<>();
        mList.add(new Screenitem("Welcome to","",R.drawable.carlog));
        mList.add(new Screenitem("Car Wash App","",R.drawable.splash));
        mList.add(new Screenitem("Thank you","",R.drawable.thankyou));



        // setup viewpaje
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPajeAdapter = new IntroViewPajeAdapter(this,mList);
        screenPager.setAdapter(introViewPajeAdapter);


        // setup  tablayout with view pager

        tabIndicator.setupWithViewPager(screenPager);


        // next button click Listner

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position == mList.size()-1){ //when we reach the last screen
                    // Todo : show getstaeted Button and hide the indicator and the next button

                    laodLastScreen();



            }   }
        });

        // tablayout and change listener

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == mList.size()-1){

                    laodLastScreen();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        // get started button click listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open main activity

                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);

                // also we need to save a boolean value to storage so next time when the user run the app
                // we colud already check the intro screen activity
                // so we use shared prefrence

                savePrefsData();
                finish();
            }
        });


    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpendBefore = pref.getBoolean("isIntroOpend",false);
        return isIntroActivityOpendBefore;
    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpend",true);
        editor.commit();
    }

    //show getstaeted Button and hide the indicator and the next button
    private void laodLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        // Todo : ADD an animation the getstarted button

        //  setup animation
        btnGetStarted.setAnimation(btnAnim);




    }
}