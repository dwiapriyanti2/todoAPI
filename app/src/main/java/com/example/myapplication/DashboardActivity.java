package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
    public void pindah(View view) {
        Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void pindah1(View view) {
        Intent intent = new Intent(DashboardActivity.this,CelanaActivity.class);
        Log.d("check if clicked", "the pinda1 button is clicked");
        startActivity(intent);
    }
}