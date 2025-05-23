package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreateAdvert, btnShowAllItems;
    Button btnShowOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAdvert = findViewById(R.id.btnCreateAdvert);
        btnShowAllItems = findViewById(R.id.btnShowAllItems);
        btnShowOnMap = findViewById(R.id.btnShowOnMap); // new button

        btnCreateAdvert.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateAdvertActivity.class))
        );

        btnShowAllItems.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ShowItemsActivity.class))
        );

        btnShowOnMap.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MapActivity.class)) // new activity
        );
    }
}
