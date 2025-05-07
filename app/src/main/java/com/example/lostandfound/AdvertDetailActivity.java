package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdvertDetailActivity extends AppCompatActivity {

    TextView textType, textName, textPhone, textDescription, textDate, textLocation;
    Button btnRemove;
    DatabaseHelper dbHelper;
    AdvertModel advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail);

        dbHelper = new DatabaseHelper(this);

        // Initialize views
        textType = findViewById(R.id.textType);
        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.textPhone);
        textDescription = findViewById(R.id.textDescription);
        textDate = findViewById(R.id.textDate);
        textLocation = findViewById(R.id.textLocation);
        btnRemove = findViewById(R.id.btnRemove);

        // Get the advert object
        advert = (AdvertModel) getIntent().getSerializableExtra("advert");

        if (advert != null) {
            textType.setText("Type: " + advert.getType());
            textName.setText("Name: " + advert.getName());
            textPhone.setText("Phone: " + advert.getPhone());
            textDescription.setText("Description: " + advert.getDescription());
            textDate.setText("Date: " + advert.getDate());
            textLocation.setText("Location: " + advert.getLocation());
        }

        // Remove button
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = dbHelper.deleteAdvert(advert.getId());
                if (success) {
                    Toast.makeText(AdvertDetailActivity.this, "Advert removed", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdvertDetailActivity.this, "Failed to remove advert", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
