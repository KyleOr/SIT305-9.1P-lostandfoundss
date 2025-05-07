package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    RadioGroup radioGroupType;
    RadioButton radioLost, radioFound;
    EditText inputName, inputPhone, inputDescription, inputDate, inputLocation;
    Button btnSave;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        // Initialize database
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        radioGroupType = findViewById(R.id.radioGroupType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputDescription = findViewById(R.id.inputDescription);
        inputDate = findViewById(R.id.inputDate);
        inputLocation = findViewById(R.id.inputLocation);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from inputs
                String postType = radioLost.isChecked() ? "Lost" : "Found";
                String name = inputName.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();
                String description = inputDescription.getText().toString().trim();
                String date = inputDate.getText().toString().trim();
                String location = inputLocation.getText().toString().trim();

                // Basic validation
                if (name.isEmpty() || phone.isEmpty() || description.isEmpty() ||
                        date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(CreateAdvertActivity.this,
                            "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save to database
                boolean success = dbHelper.insertAdvert(postType, name, phone, description, date, location);

                if (success) {
                    Toast.makeText(CreateAdvertActivity.this, "Advert saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Error saving advert.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
