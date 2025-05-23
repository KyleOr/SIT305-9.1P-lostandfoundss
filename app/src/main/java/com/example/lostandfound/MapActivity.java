package com.example.lostandfound;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DatabaseHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        List<AdvertModel> adverts = dbHelper.getAllAdverts();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        for (AdvertModel advert : adverts) {
            try {
                List<Address> addresses = geocoder.getFromLocationName(advert.getLocation(), 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(advert.getType() + ": " + advert.getDescription()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
                }
            } catch (IOException e) {
                Toast.makeText(this, "Failed to geocode: " + advert.getLocation(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
