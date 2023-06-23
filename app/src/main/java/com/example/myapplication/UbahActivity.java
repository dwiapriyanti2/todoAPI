package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Baju;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UbahActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText etNama, etJenis, etJumlah;
    private Button btnSimpan, btnHapus;
    private Baju baju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        // Inisialisasi ApiService dari Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);

        etNama = findViewById(R.id.etNama);
        etJenis = findViewById(R.id.etJenis);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);

        // Ambil data baju dari Intent
        baju = getIntent().getParcelableExtra("baju");
        if (baju != null) {
            etNama.setText(baju.getNama());
            etJenis.setText(baju.getJenis());
            etJumlah.setText(String.valueOf(baju.getJumlah()));
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahBaju();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusBaju();
            }
        });
    }

    private void ubahBaju() {
        String nama = etNama.getText().toString().trim();
        String jenis = etJenis.getText().toString().trim();
        int jumlah = Integer.parseInt(etJumlah.getText().toString().trim());

        // Validasi input
        if (nama.isEmpty() || jenis.isEmpty()) {
            Toast.makeText(this, "Nama dan jenis tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update atribut baju
        baju.setNama(nama);
        baju.setJenis(jenis);
        baju.setJumlah(jumlah);

        // Panggil metode untuk update baju ke API
        Call<Baju> call = apiService.updateBaju(baju.getId(), baju);
        call.enqueue(new Callback<Baju>() {
            @Override
            public void onResponse(Call<Baju> call, Response<Baju> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UbahActivity.this, "Baju berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // Tangani kesalahan respons dari API
                    Toast.makeText(UbahActivity.this, "Gagal memperbarui baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Baju> call, Throwable t) {
                // Tangani kesalahan jaringan atau koneksi
                if (t instanceof IOException) {
                    // IOException occurred, handle it as a network error
                    Toast.makeText(UbahActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                } else {
                    // Other types of exceptions occurred, handle them accordingly
                    Toast.makeText(UbahActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hapusBaju() {
        // Panggil metode untuk hapus baju dari API
        Call<Void> call = apiService.deleteBaju(baju.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UbahActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // Tangani kesalahan respons dari API
                    Toast.makeText(UbahActivity.this, "Gagal menghapus baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Tangani kesalahan jaringan atau koneksi
                if (t instanceof IOException) {
                    // IOException occurred, handle it as a network error
                    Toast.makeText(UbahActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                } else {
                    // Other types of exceptions occurred, handle them accordingly
                    Toast.makeText(UbahActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
