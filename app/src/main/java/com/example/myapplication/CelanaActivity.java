package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Celana;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CelanaActivity extends AppCompatActivity implements CelanaAdapter.CelanaClickListener {

    private static ApiService instance;
    private ApiService apiService;
    private Button btnTambah;
    private RecyclerView recyclerView;
    private CelanaAdapter celanaAdapter;
    private List<Celana> celanaList;

    private static final int REQUEST_TAMBAH_CELANA = 1;
    private static final int REQUEST_UBAH_CELANA = 2;

    public static ApiService getInstance() {
        return instance;
    }

    public static void setInstance(ApiService instance) {
        CelanaActivity.instance = instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celana);

        // Inisialisasi ApiService dari Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);

        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelanaActivity.this, TambahActivity2.class);
                startActivityForResult(intent, REQUEST_TAMBAH_CELANA);
            }
        });

        // Inisialisasi RecyclerView dan RecyclerViewAdapter
        recyclerView = findViewById(R.id.recyclerViewCelanas1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        celanaList = new ArrayList<>();
        celanaAdapter = new CelanaAdapter(celanaList, this);
        recyclerView.setAdapter(celanaAdapter);

        // Panggil metode untuk mengambil data celana dari API
        getCelanas();
    }

    private void getCelanas() {
        Call<List<Celana>> call = apiService.getCelanas();
        call.enqueue(new Callback<List<Celana>>() {
            @Override
            public void onResponse(Call<List<Celana>> call, Response<List<Celana>> response) {
                if (response.isSuccessful()) {
                    List<Celana> celanas = response.body();
                    if (celanas != null) {
                        // Update dataset di RecyclerViewAdapter
                        celanaList.clear();
                        celanaList.addAll(celanas);
                        celanaAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Tangani kesalahan respons dari API
                    Toast.makeText(CelanaActivity.this, "Gagal mengambil data celana", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Celana>> call, Throwable t) {
                // Tangani kesalahan jaringan atau koneksi
                Toast.makeText(CelanaActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAMBAH_CELANA && resultCode == RESULT_OK) {
            // Jika berhasil menambahkan celana, panggil metode untuk mengambil data celana dari API
            getCelanas();
        } else if (requestCode == REQUEST_UBAH_CELANA && resultCode == RESULT_OK) {
            // Jika berhasil mengubah celana, panggil metode untuk mengambil data celana dari API
            getCelanas();
        }
    }

    @Override
    public void onCelanaClick(int position) {
        Celana celana = celanaList.get(position);
        openUbahActivity(celana);
    }

    public void openUbahActivity(Celana celana) {
        Intent intent = new Intent(this, UbahActivity.class);
        intent.putExtra("celana", celana);
        startActivityForResult(intent, REQUEST_UBAH_CELANA);
    }
}
