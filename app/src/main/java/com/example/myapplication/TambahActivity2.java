package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Celana;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity2 extends AppCompatActivity {

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
                simpanCelana();
            }
        });
    }

    private void simpanCelana() {
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

        // Buat objek Celana baru
        Celana celana = new Celana(nama, jenis, jumlah);

        // Panggil metode untuk tambah celana ke API
        Call<Celana> call = apiService.createCelana(celana);
        call.enqueue(new Callback<Celana>() {
            @Override
            public void onResponse(Call<Celana> call, Response<Celana> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TambahActivity2.this, "Celana berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    CelanaActivity.getInstance().getCelanas();
                    finish();
                } else {
                    // Tangani kesalahan respons dari API
                    Toast.makeText(TambahActivity2.this, "Gagal menambahkan celana", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Celana> call, Throwable t) {
                // Tangani kesalahan jaringan atau koneksi
                Toast.makeText(TambahActivity2.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
