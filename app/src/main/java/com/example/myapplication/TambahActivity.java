package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Callback;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Baju;

import retrofit2.Call;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText etNama, etJenis, etJumlah;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        // Inisialisasi ApiService dari Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);

        etNama = findViewById(R.id.etNama);
        etJenis = findViewById(R.id.etJenis);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanBaju();
            }
        });
    }

    private void simpanBaju() {
        String nama = etNama.getText().toString().trim();
        String jenis = etJenis.getText().toString().trim();
        String jumlah = etJumlah.getText().toString().trim();

        // Validasi input
        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(jenis)) {
            Toast.makeText(this, "Nama dan jenis tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi jumlah
        if (TextUtils.isEmpty(jumlah)) {
            jumlah = "0"; // Jumlah default jika tidak diisi
        }

        // Buat objek Baju baru
        Baju baju = new Baju(nama, jenis, jumlah);

        // Panggil metode untuk tambah baju ke API Vercel
        Call<Baju> call = apiService.createBaju(baju);
        call.enqueue(new Callback<Baju>() {
            @Override
            public void onResponse(Call<Baju> call, Response<Baju> response) {
                if (!response.isSuccessful()) {
                    // Tangani kesalahan respons dari API
                    Toast.makeText(TambahActivity.this, "Gagal menambahkan baju", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahActivity.this, "Baju berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    MainActivity.getInstance().getBajus();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Baju> call, Throwable t) {
                // Tangani kesalahan jaringan atau koneksi
                Toast.makeText(TambahActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
