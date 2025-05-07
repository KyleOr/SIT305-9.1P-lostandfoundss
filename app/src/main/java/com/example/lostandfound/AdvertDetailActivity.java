package com.example.lostandfound;

import android.os.Bundle;
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
            textType.setText(getString(R.string.advert_type, advert.getType()));
            textName.setText(getString(R.string.advert_name, advert.getName()));
            textPhone.setText(getString(R.string.advert_phone, advert.getPhone()));
            textDescription.setText(getString(R.string.advert_description, advert.getDescription()));
            textDate.setText(getString(R.string.advert_date, advert.getDate()));
            textLocation.setText(getString(R.string.advert_location, advert.getLocation()));
        }

        btnRemove.setOnClickListener(v -> {
            boolean success = dbHelper.deleteAdvert(advert.getId());
            Toast.makeText(
                    AdvertDetailActivity.this,
                    success ? R.string.advert_removed : R.string.advert_remove_failed,
                    Toast.LENGTH_SHORT
            ).show();
            if (success) finish();
        });
    }
}
