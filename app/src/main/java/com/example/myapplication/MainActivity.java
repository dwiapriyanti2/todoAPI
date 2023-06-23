package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Baju;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BajuAdapter.BajuClickListener {

    private ApiService apiService;
    private Button btnTambah;
    private RecyclerView recyclerView;
    private BajuAdapter bajuAdapter;
    private List<Baju> bajuList;

    private static final int REQUEST_TAMBAH_BAJU = 1;
    private static final int REQUEST_UBAH_BAJU = 2;

    public static ApiService getInstance() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi ApiService dari Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);

        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                startActivityForResult(intent, REQUEST_TAMBAH_BAJU);
            }
        });

        // Inisialisasi RecyclerView dan RecyclerViewAdapter
        recyclerView = findViewById(R.id.recyclerViewBajus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bajuList = new ArrayList<>();
        bajuAdapter = new BajuAdapter(bajuList, this);
        recyclerView.setAdapter(bajuAdapter);

        // Panggil metode untuk mengambil data baju dari API Vercel
        getBajus();
    }

    private void getBajus() {
        Call<List<Baju>> call = apiService.getBajus();
        call.enqueue(new Callback<List<Baju>>() {
            @Override
            public void onResponse(Call<List<Baju>> call, Response<List<Baju>> response) {
                if (response.isSuccessful()) {
                    List<Baju> bajus = response.body();
                    if (bajus != null) {
                        // Update dataset di RecyclerViewAdapter
                        bajuList.clear();
                        bajuList.addAll(bajus);
                        bajuAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Tangani kesalahan respons dari API Vercel
                    Toast.makeText(MainActivity.this, "Gagal mengambil data baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Baju>> call, Throwable t) {
                // Tangani kesalahan jaringan atau koneksi
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAMBAH_BAJU && resultCode == RESULT_OK) {
            // Jika berhasil menambahkan baju, panggil metode untuk mengambil data baju dari API Vercel
            getBajus();
        } else if (requestCode == REQUEST_UBAH_BAJU && resultCode == RESULT_OK) {
            // Jika berhasil mengubah baju, panggil metode untuk mengambil data baju dari API Vercel
            getBajus();
        }
    }

    @Override
    public void onBajuClick(int position) {
        Baju baju = bajuList.get(position);
        openUbahActivity(baju);
    }

    public void openUbahActivity(Baju baju) {
        Intent intent = new Intent(this, UbahActivity.class);
        intent.putExtra("baju", baju);
        startActivityForResult(intent, REQUEST_UBAH_BAJU);
    }
}
