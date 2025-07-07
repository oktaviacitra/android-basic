# Buat proyek baru
**Empty Views Activity**
- Nama: Joke
- Language: Java
- Build configuration language: Groovy DSL
  
# Membangun halaman utama
## activity_main.xml
- Struktur Scrollable: Menggunakan ScrollView sebagai root dengan padding="16dp" agar konten dapat digulir jika melebihi tinggi layar.
- Tampilan Lelucon: TextView dengan id="@+id/tvJoke" menampilkan lelucon yang sedang diambil, berukuran teks 18sp dan padding="8dp".
- Tombol Navigasi: Sebuah LinearLayout horizontal di bawah lelucon berisi dua tombol: btnNext untuk mengambil lelucon selanjutnya (menggunakan layout_weight="1" agar sama lebar), btnSave untuk menyimpan lelucon saat ini (dengan margin kiri 8dp).
- Judul Daftar Tersimpan: TextView bertuliskan “Saved Jokes:” dengan gaya tebal (textStyle="bold") dan ukuran 16sp, sebagai header daftar.
- Container Daftar: LinearLayout vertikal dengan id="@+id/layoutSaved" berfungsi sebagai wadah dinamis untuk menambahkan item lelucon yang disimpan.

```
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!-- Tempat tampil lelucon -->
        <TextView
            android:id="@+id/tvJoke"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Fetching joke..."
            android:textSize="18sp"
            android:padding="8dp"/>

        <!-- Tombol Next & Save -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center"
            android:layout_marginTop="8dp">

            <Button
                android:id="@+id/btnNext"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Next"/>

            <Button
                android:id="@+id/btnSave"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Save"
                android:layout_marginStart="8dp"/>
        </LinearLayout>

        <!-- Judul daftar tersimpan -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Saved Jokes:"
            android:textStyle="bold"
            android:textSize="16sp"
            android:layout_marginTop="16dp"/>

        <!-- Layout untuk daftar lelucon yang disimpan -->
        <LinearLayout
            android:id="@+id/layoutSaved"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"/>

    </LinearLayout>
</ScrollView>
```
## MainActivity.java
- Inisialisasi Komponen & SharedPreferences: Pada onCreate, kode menghubungkan TextView (tvJoke), dua Button (btnNext, btnSave), dan LinearLayout (layoutSaved), lalu mendapatkan instance SharedPreferences (JokePrefs) dan memanggil loadSaved() untuk memuat lelucon yang sudah disimpan.
- Memuat Lelucon Tersimpan (loadSaved()): Membaca jumlah lelucon (count) dan setiap entri (joke_0, joke_1, …) dari SharedPreferences, menyimpannya ke dalam ArrayList<String> saved, kemudian memperbarui tampilan daftar tersimpan melalui updateSavedUI().
- Mengambil Lelucon dari API (fetchJoke()): Menjalankan thread latar untuk melakukan HTTP GET ke https://v2.jokeapi.dev/joke/Any?safe-mode, membaca respons JSON, memeriksa apakah tipe single atau twopart, lalu menampilkan teks lelucon di tvJoke lewat runOnUiThread. Jika terjadi error, menampilkan pesan kesalahan.
- Tombol “Next”: Saat diklik, memanggil fetchJoke() untuk mengambil dan menampilkan lelucon baru.
- Tombol “Save”: Saat diklik, mengambil teks lelucon saat ini, mengecek agar tidak kosong dan belum ada di saved, kemudian menambahkannya ke list, memanggil saveAll() untuk menyimpan ulang semua lelucon ke SharedPreferences, memperbarui UI dengan updateSavedUI(), dan menampilkan toast “Joke saved!”.
- Menyimpan Semua Lelucon (saveAll()): Menyimpan properti count dan setiap entri lelucon (joke_i) ke dalam SharedPreferences via apply().
- Memperbarui UI Daftar Tersimpan (updateSavedUI()): Mengosongkan layoutSaved, lalu untuk tiap string di saved membuat TextView baru yang berisi lelucon tersebut (dengan bullet “• ”) dan menambahkannya ke layout.
```
package com.example.joke;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvJoke;
    private Button btnNext, btnSave;
    private LinearLayout layoutSaved;
    private SharedPreferences prefs;
    private ArrayList<String> saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvJoke     = findViewById(R.id.tvJoke);
        btnNext    = findViewById(R.id.btnNext);
        btnSave    = findViewById(R.id.btnSave);
        layoutSaved= findViewById(R.id.layoutSaved);

        prefs = getSharedPreferences("JokePrefs", MODE_PRIVATE);
        loadSaved();

        fetchJoke();

        btnNext.setOnClickListener(v -> fetchJoke());
        btnSave.setOnClickListener(v -> {
            String joke = tvJoke.getText().toString();
            if (!joke.isEmpty() && !saved.contains(joke)) {
                saved.add(joke);
                saveAll();
                updateSavedUI();
                Toast.makeText(this, "Joke saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchJoke() {
        tvJoke.setText("Loading...");
        new Thread(() -> {
            try {
                URL url = new URL("https://v2.jokeapi.dev/joke/Any?safe-mode");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) sb.append(line);
                rd.close();

                String json = sb.toString();
                JSONObject o = new JSONObject(json);
                final String joke;
                if (o.getBoolean("error")) {
                    joke = "Error fetching joke.";
                } else if (o.getString("type").equals("single")) {
                    joke = o.getString("joke");
                } else {
                    joke = o.getString("setup") + "\n\n" + o.getString("delivery");
                }

                runOnUiThread(() -> tvJoke.setText(joke));

            } catch (Exception e) {
                runOnUiThread(() -> tvJoke.setText("Error fetching joke."));
            }
        }).start();
    }

    private void loadSaved() {
        saved = new ArrayList<>();
        int count = prefs.getInt("count", 0);
        for (int i = 0; i < count; i++) {
            saved.add(prefs.getString("joke_" + i, ""));
        }
        updateSavedUI();
    }

    private void saveAll() {
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("count", saved.size());
        for (int i = 0; i < saved.size(); i++) {
            ed.putString("joke_" + i, saved.get(i));
        }
        ed.apply();
    }

    private void updateSavedUI() {
        layoutSaved.removeAllViews();
        for (String j : saved) {
            TextView tv = new TextView(this);
            tv.setText("• " + j);
            tv.setPadding(0,8,0,8);
            layoutSaved.addView(tv);
        }
    }
}
```
# Menambahkan permission pada app/manifests/AndroidManifest.xml
```
<uses-permission android:name="android.permission.INTERNET"/>
```
<img width="1680" alt="Screenshot 2025-07-07 at 23 19 52" src="https://github.com/user-attachments/assets/a3b24e7c-e75a-483c-b5d1-26c8a4caaf0c" />

# Hasil
https://github.com/user-attachments/assets/f3a122f6-b0b1-415c-b4cc-8d394332f0f9


