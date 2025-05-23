package com.example.lostandfound;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1001;

    RadioGroup radioGroupType;
    RadioButton radioLost, radioFound;
    EditText inputName, inputPhone, inputDescription, inputDate, inputLocation;
    Button btnSave, btnGetCurrentLocation;

    DatabaseHelper dbHelper;
    FusedLocationProviderClient fusedLocationClient;

    // Permission launcher
    ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAgD48zUmujHxrX7wyFqGmWgY9OMwHJn0U");
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Views
        radioGroupType = findViewById(R.id.radioGroupType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputDescription = findViewById(R.id.inputDescription);
        inputDate = findViewById(R.id.inputDate);
        inputLocation = findViewById(R.id.inputLocation);
        btnSave = findViewById(R.id.btnSave);
        btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation); // New button in XML

        dbHelper = new DatabaseHelper(this);

        // Disable keyboard for location input
        inputLocation.setFocusable(false);
        inputLocation.setOnClickListener(v -> launchPlaceAutocomplete());

        // Location permission launcher
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) getCurrentLocation();
                    else
                        Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
                }
        );

        // Handle current location button
        btnGetCurrentLocation.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        });

        // Save button logic
        btnSave.setOnClickListener(v -> {
            String postType = radioLost.isChecked() ? "Lost" : "Found";
            String name = inputName.getText().toString().trim();
            String phone = inputPhone.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();
            String date = inputDate.getText().toString().trim();
            String location = inputLocation.getText().toString().trim();

            boolean success = dbHelper.insertAdvert(postType, name, phone, description, date, location);

            if (success) {
                Toast.makeText(this, "Advert saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save advert.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchPlaceAutocomplete() {
        List<Place.Field> fields = Arrays.asList(
                Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG
        );

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields
        ).build(this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                inputLocation.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(CreateAdvertActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addresses.isEmpty()) {
                            String address = addresses.get(0).getAddressLine(0);
                            inputLocation.setText(address);
                        } else {
                            Toast.makeText(this, "Unable to get address.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "Geocoder error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Location is null.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) {
            Toast.makeText(this, "SecurityException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
