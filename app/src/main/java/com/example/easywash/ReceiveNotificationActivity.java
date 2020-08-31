package com.example.easywash;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiveNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_receive_notification );

        TextView categotyTv = findViewById(R.id.category);
        TextView brandTv = findViewById(R.id.brand);

        if (getIntent().hasExtra("category")){
            String category = getIntent().getStringExtra("category");
            String brand = getIntent().getStringExtra("brandId");
            categotyTv.setText(category);
            brandTv.setText(brand);
        }
    }
}

