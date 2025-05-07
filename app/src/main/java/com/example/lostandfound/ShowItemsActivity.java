package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowItemsActivity extends AppCompatActivity implements AdvertAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    AdvertAdapter adapter;
    DatabaseHelper dbHelper;
    List<AdvertModel> advertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        loadAdverts(); // Load list for the first time
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdverts(); // Reload list every time screen resumes
    }

    private void loadAdverts() {
        advertList = dbHelper.getAllAdverts();
        adapter = new AdvertAdapter(advertList, this);
        recyclerView.setAdapter(adapter);
    }

    // When a user clicks on a row
    @Override
    public void onItemClick(AdvertModel advert) {
        Intent intent = new Intent(this, AdvertDetailActivity.class);
        intent.putExtra("advert", advert);
        startActivity(intent);
    }
}
