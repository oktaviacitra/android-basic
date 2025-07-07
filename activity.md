# Buat proyek baru
**Empty Views Activity**
- Nama: Fun
- Language: Java
- Build configuration language: Groovy DSL
  
# Membangun halaman utama
## activity_main.xml
- Root Layout Vertikal dengan Padding: Menggunakan LinearLayout dengan orientasi vertikal dan padding="16dp" agar semua elemen disusun berurutan dari atas ke bawah dengan jarak tepi konsisten.
- Tampilan Aktivitas Saat Ini: TextView ber-id="@+id/activityTextView" menampilkan teks placeholder “Activity will appear here” (ukuran teks 18sp) untuk menunjukkan aktivitas acak yang diambil.
- Tombol “Next Activity”: Button dengan id="@+id/nextButton" berfungsi untuk memicu dan menampilkan aktivitas acak berikutnya.
- Tombol “Save Activity”: Button dengan id="@+id/saveButton" (dengan margin atas 10dp) digunakan untuk menyimpan aktivitas yang sedang ditampilkan.
- Header Daftar Tersimpan: TextView bertuliskan “Saved Activities:” (ukuran teks 18sp) sebagai judul bagi daftar aktivitas yang telah disimpan.
- Container Daftar Aktivitas Tersimpan: LinearLayout ber-id="@+id/savedActivitiesLayout" dengan orientasi vertikal dan margin atas 10dp, berfungsi sebagai wadah dinamis untuk menambahkan TextView atau elemen lain yang merepresentasikan setiap aktivitas yang sudah disimpan.
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <!-- Displaying the random activity -->
    <TextView
        android:id="@+id/activityTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Activity will appear here"
        android:textSize="18sp"
        android:layout_marginBottom="20dp"/>

    <!-- Next Button -->
    <Button
        android:id="@+id/nextButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Next Activity" />

    <!-- Save Button -->
    <Button
        android:id="@+id/saveButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Save Activity"
        android:layout_marginTop="10dp"/>

    <!-- Displaying saved activities -->
    <TextView
        android:text="Saved Activities:"
        android:textSize="18sp"
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <LinearLayout
        android:id="@+id/savedActivitiesLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_marginTop="10dp"/>

</LinearLayout>
```

## MainActivity.java
- Inisialisasi UI & SharedPreferences: Pada onCreate(), komponen UI (TextView, dua Button, dan LinearLayout) dihubungkan dengan findViewById, lalu SharedPreferences diinisialisasi untuk menyimpan/memuat daftar aktivitas.
- Memuat Aktivitas Tersimpan: Metode loadSavedActivities() membaca jumlah (activityCount) dan setiap entri (activity_0, activity_1, dst.) dari SharedPreferences ke dalam List<String> savedActivities.
- Mengambil Aktivitas Acak: Metode fetchRandomActivity() menjalankan Thread terpisah untuk melakukan HTTP GET ke API “https://bored-api.appbrewery.com/random”, membaca respons, memanggil parseActivityFromResponse() untuk ekstrak teks aktivitas, dan menampilkan hasilnya di activityTextView lewat runOnUiThread.
- Parsing Respons Sederhana: parseActivityFromResponse(String) melakukan pemotongan string (substring) untuk mengambil nilai dari kunci "activity"; jika gagal, mengembalikan pesan error.
- Tombol “Next”: Klik pada nextButton memicu fetchRandomActivity(), sehingga UI selalu menampilkan aktivitas baru.
- Tombol “Save”: Klik pada saveButton mengambil teks aktivitas saat ini, menambahkannya ke savedActivities, memanggil saveActivities() untuk menyimpan ulang seluruh list ke SharedPreferences, memperbarui tampilan daftar dengan updateSavedActivitiesUI(), dan menampilkan Toast.
- Menyimpan & Memperbarui Daftar: saveActivities(List<String>) menulis jumlah dan setiap aktivitas ke SharedPreferences.updateSavedActivitiesUI() mengosongkan savedActivitiesLayout dan menambahkan satu TextView baru untuk setiap item di list, sehingga daftar tersimpan selalu sinkron dengan data.
```
package com.example.fun;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView activityTextView;
    private Button nextButton, saveButton;
    private LinearLayout savedActivitiesLayout;
    private SharedPreferences sharedPreferences;

    private List<String> savedActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI elements
        activityTextView = findViewById(R.id.activityTextView);
        nextButton = findViewById(R.id.nextButton);
        saveButton = findViewById(R.id.saveButton);
        savedActivitiesLayout = findViewById(R.id.savedActivitiesLayout);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("ActivityPrefs", MODE_PRIVATE);
        savedActivities = loadSavedActivities();

        // Fetch random activity on start
        fetchRandomActivity();

        // Next button to fetch another random activity
        nextButton.setOnClickListener(v -> fetchRandomActivity());

        // Save button to save the current activity to SharedPreferences
        saveButton.setOnClickListener(v -> saveActivityToSharedPreferences());
    }

    // Fetches a random activity from the Bored API
    private void fetchRandomActivity() {
        new Thread(() -> {
            try {
                URL url = new URL("https://bored-api.appbrewery.com/random");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the response
                String activity = parseActivityFromResponse(response.toString());

                // Update the UI
                runOnUiThread(() -> activityTextView.setText(activity));
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error fetching activity", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    // Parses the activity from the API response
    private String parseActivityFromResponse(String response) {
        try {
            // Extract the "activity" key value from the response (using simple string parsing here)
            String activity = response.substring(response.indexOf("\"activity\":") + 12);
            activity = activity.substring(0, activity.indexOf("\""));
            return activity;
        } catch (Exception e) {
            return "Error fetching activity";
        }
    }

    // Save current activity to SharedPreferences
    private void saveActivityToSharedPreferences() {
        String activity = activityTextView.getText().toString();
        if (!activity.isEmpty()) {
            savedActivities.add(activity);
            saveActivities(savedActivities);

            // Update the saved activities section
            updateSavedActivitiesUI();

            Toast.makeText(MainActivity.this, "Activity saved!", Toast.LENGTH_SHORT).show();
        }
    }

    // Save activities list to SharedPreferences
    private void saveActivities(List<String> activities) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("activityCount", activities.size());
        for (int i = 0; i < activities.size(); i++) {
            editor.putString("activity_" + i, activities.get(i));
        }
        editor.apply();
    }

    // Load saved activities from SharedPreferences
    private List<String> loadSavedActivities() {
        List<String> activities = new ArrayList<>();
        int count = sharedPreferences.getInt("activityCount", 0);
        for (int i = 0; i < count; i++) {
            activities.add(sharedPreferences.getString("activity_" + i, ""));
        }
        return activities;
    }

    // Update UI with saved activities
    private void updateSavedActivitiesUI() {
        savedActivitiesLayout.removeAllViews();
        for (String activity : savedActivities) {
            TextView textView = new TextView(this);
            textView.setText(activity);
            savedActivitiesLayout.addView(textView);
        }
    }
}
```
# Menambahkan permission pada app/manifests/AndroidManifest.xml
```
<uses-permission android:name="android.permission.INTERNET"/>
```
<img width="1680" alt="Screenshot 2025-07-07 at 23 19 52" src="https://github.com/user-attachments/assets/a3b24e7c-e75a-483c-b5d1-26c8a4caaf0c" />
