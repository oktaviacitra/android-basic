# Daftar Isi
- [BangunDatar](#bangundatar)
- [BangunRuang](#bangunruang)

# BangunDatar
Aplikasi Bangun Datar adalah aplikasi Android sederhana yang dibuat menggunakan bahasa pemrograman Java di Android Studio untuk membantu pengguna menghitung luas dan keliling dari berbagai bentuk bangun datar seperti persegi, persegi panjang, segitiga, dan lingkaran. Pengguna cukup memilih jenis bangun datar dari menu pilihan (spinner), kemudian memasukkan nilai sesuai bentuk yang dipilih seperti sisi, panjang, lebar, alas, tinggi, atau jari-jari. Setelah itu, aplikasi secara otomatis menghitung dan menampilkan hasil luas serta keliling dari bangun datar tersebut secara cepat dan akurat, sehingga sangat bermanfaat sebagai alat bantu belajar matematika dasar.
## activity_main.xml
Aplikasi Bangun Datar merupakan aplikasi edukatif berbasis Android yang dirancang untuk memudahkan pengguna dalam menghitung luas dan keliling berbagai bentuk bangun datar seperti persegi, persegi panjang, segitiga, dan lingkaran. Dengan antarmuka yang sederhana, pengguna cukup memilih jenis bangun datar, mengisi nilai parameter yang dibutuhkan (seperti sisi, panjang, lebar, alas, tinggi, atau jari-jari), lalu aplikasi akan menampilkan hasil perhitungan secara otomatis. Aplikasi ini sangat bermanfaat sebagai media pembelajaran interaktif bagi siswa maupun siapa saja yang ingin memahami konsep dasar geometri dengan cara yang praktis dan cepat.
```
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:padding="16dp"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:text="Pilih Bangun Datar:"
            android:textSize="18sp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <Spinner
            android:id="@+id/spinnerShapes"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <EditText
            android:id="@+id/input1"
            android:hint="Input 1"
            android:inputType="numberDecimal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <EditText
            android:id="@+id/input2"
            android:hint="Input 2 (opsional)"
            android:inputType="numberDecimal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <Button
            android:id="@+id/btnHitung"
            android:text="Hitung Luas"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"/>

        <TextView
            android:id="@+id/txtHasil"
            android:text="Hasil akan ditampilkan di sini"
            android:textSize="18sp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"/>
    </LinearLayout>
</ScrollView>
```
## MainActivity.java
Aplikasi Bangun Datar adalah aplikasi Android sederhana yang dibuat menggunakan Java di Android Studio untuk membantu pengguna menghitung luas dan keliling dari beberapa bentuk bangun datar seperti persegi, persegi panjang, segitiga, dan lingkaran. Melalui antarmuka interaktif berbasis spinner dan input teks, pengguna dapat memilih jenis bangun datar, mengisi nilai parameter yang sesuai (misalnya sisi, panjang, lebar, alas, tinggi, atau jari-jari), lalu menekan tombol “Hitung” untuk melihat hasil perhitungan luas dan keliling. Aplikasi ini cocok digunakan sebagai alat bantu belajar konsep dasar geometri secara praktis dan efisien.
```
package com.example.bangundatar;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerShapes;
    EditText input1, input2;
    Button btnHitung;
    TextView txtHasil;

    String[] shapes = {"Persegi", "Persegi Panjang", "Segitiga", "Lingkaran"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerShapes = findViewById(R.id.spinnerShapes);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        btnHitung = findViewById(R.id.btnHitung);
        txtHasil = findViewById(R.id.txtHasil);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, shapes);
        spinnerShapes.setAdapter(adapter);

        spinnerShapes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateHint(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnHitung.setOnClickListener(v -> hitungLuasKeliling());
    }

    private void updateHint(int position) {
        switch (position) {
            case 0: // Persegi
                input1.setHint("Sisi");
                input2.setVisibility(View.GONE);
                break;
            case 1: // Persegi Panjang
                input1.setHint("Panjang");
                input2.setHint("Lebar");
                input2.setVisibility(View.VISIBLE);
                break;
            case 2: // Segitiga
                input1.setHint("Alas");
                input2.setHint("Tinggi");
                input2.setVisibility(View.VISIBLE);
                break;
            case 3: // Lingkaran
                input1.setHint("Jari-jari");
                input2.setVisibility(View.GONE);
                break;
        }
        input1.setText("");
        input2.setText("");
        txtHasil.setText("Hasil akan ditampilkan di sini");
    }

    private void hitungLuasKeliling() {
        int shape = spinnerShapes.getSelectedItemPosition();
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();

        if (s1.isEmpty() || (input2.getVisibility() == View.VISIBLE && s2.isEmpty())) {
            txtHasil.setText("Mohon lengkapi input.");
            return;
        }

        double v1 = Double.parseDouble(s1);
        double v2 = s2.isEmpty() ? 0 : Double.parseDouble(s2);

        double luas = 0;
        double keliling = 0;

        switch (shape) {
            case 0: // Persegi
                luas = v1 * v1;
                keliling = 4 * v1;
                break;
            case 1: // Persegi Panjang
                luas = v1 * v2;
                keliling = 2 * (v1 + v2);
                break;
            case 2: // Segitiga
                luas = 0.5 * v1 * v2;
                // Keliling segitiga diasumsikan segitiga sama kaki untuk contoh
                double sisiMiring = Math.sqrt(Math.pow(v1 / 2, 2) + Math.pow(v2, 2));
                keliling = v1 + 2 * sisiMiring;
                break;
            case 3: // Lingkaran
                luas = Math.PI * v1 * v1;
                keliling = 2 * Math.PI * v1;
                break;
        }

        txtHasil.setText("Luas: " + String.format("%.2f", luas) + "\nKeliling: " + String.format("%.2f", keliling));
    }
}
```
# BangunRuang
Aplikasi Bangun Ruang adalah aplikasi Android yang dikembangkan menggunakan bahasa Java di Android Studio untuk menghitung volume dan luas permukaan berbagai bangun ruang seperti kubus, balok, bola, tabung, dan limas segi empat. Pengguna dapat memilih jenis bangun ruang melalui menu dropdown, lalu memasukkan nilai-nilai yang diperlukan seperti sisi, panjang, lebar, tinggi, atau jari-jari. Setelah menekan tombol "Hitung", aplikasi akan menampilkan hasil perhitungan volume dan luas permukaan secara otomatis. Aplikasi ini bermanfaat sebagai alat bantu pembelajaran geometri yang interaktif dan praktis, terutama bagi pelajar sekolah dasar hingga menengah.
## activity_main.java
Aplikasi Bangun Ruang adalah aplikasi Android berbasis Java yang dirancang untuk menghitung volume dan luas permukaan dari berbagai bentuk bangun ruang seperti kubus, balok, bola, tabung, dan limas segi empat. Antarmuka aplikasi menggunakan komponen seperti Spinner untuk memilih bentuk bangun, beberapa EditText untuk memasukkan nilai parameter (sisi, panjang, lebar, tinggi, atau jari-jari), serta tombol Hitung untuk memproses perhitungan. Hasil perhitungan kemudian ditampilkan secara otomatis dalam TextView. Tampilan antarmuka dibungkus dalam ScrollView agar tetap dapat diakses dengan nyaman di berbagai ukuran layar, menjadikan aplikasi ini praktis digunakan sebagai alat bantu pembelajaran geometri ruang.
```
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:orientation="vertical"
        android:padding="16dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:text="Pilih Bangun Ruang:"
            android:textSize="18sp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <Spinner
            android:id="@+id/spinnerShapes"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <EditText
            android:id="@+id/input1"
            android:hint="Input 1"
            android:inputType="numberDecimal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <EditText
            android:id="@+id/input2"
            android:hint="Input 2 (opsional)"
            android:inputType="numberDecimal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <EditText
            android:id="@+id/input3"
            android:hint="Input 3 (opsional)"
            android:inputType="numberDecimal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <Button
            android:id="@+id/btnHitung"
            android:text="Hitung Volume"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"/>

        <TextView
            android:id="@+id/txtHasil"
            android:text="Hasil akan ditampilkan di sini"
            android:textSize="18sp"
            android:layout_marginTop="16dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </LinearLayout>
</ScrollView>
```
## MainActivity.java
Aplikasi Bangun Ruang adalah aplikasi Android yang dikembangkan menggunakan Java di Android Studio untuk membantu pengguna menghitung volume dan luas permukaan dari berbagai bangun ruang seperti kubus, balok, bola, tabung, dan limas segi empat. Aplikasi ini menggunakan elemen antarmuka seperti Spinner untuk memilih bentuk bangun ruang, EditText untuk memasukkan nilai parameter (seperti sisi, panjang, lebar, tinggi, atau jari-jari), serta tombol Hitung untuk memproses perhitungan. Hasil perhitungan ditampilkan secara otomatis dalam TextView, mencakup nilai volume dan luas permukaan. Dengan logika pemrograman yang terstruktur dan antarmuka yang sederhana, aplikasi ini cocok digunakan sebagai alat bantu belajar interaktif dalam memahami konsep geometri ruang secara praktis dan efisien.
```
package com.example.bangunruang;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerShapes;
    EditText input1, input2, input3;
    Button btnHitung;
    TextView txtHasil;

    String[] shapes = {"Kubus", "Balok", "Bola", "Tabung", "Limas Segi Empat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerShapes = findViewById(R.id.spinnerShapes);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        btnHitung = findViewById(R.id.btnHitung);
        txtHasil = findViewById(R.id.txtHasil);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, shapes);
        spinnerShapes.setAdapter(adapter);

        spinnerShapes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateHint(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnHitung.setOnClickListener(v -> hitungVolumeDanLuasPermukaan());
    }

    private void updateHint(int shape) {
        input1.setText("");
        input2.setText("");
        input3.setText("");

        switch (shape) {
            case 0: // Kubus
                input1.setHint("Sisi");
                input2.setVisibility(View.GONE);
                input3.setVisibility(View.GONE);
                break;
            case 1: // Balok
                input1.setHint("Panjang");
                input2.setHint("Lebar");
                input2.setVisibility(View.VISIBLE);
                input3.setHint("Tinggi");
                input3.setVisibility(View.VISIBLE);
                break;
            case 2: // Bola
                input1.setHint("Jari-jari");
                input2.setVisibility(View.GONE);
                input3.setVisibility(View.GONE);
                break;
            case 3: // Tabung
                input1.setHint("Jari-jari");
                input2.setHint("Tinggi");
                input2.setVisibility(View.VISIBLE);
                input3.setVisibility(View.GONE);
                break;
            case 4: // Limas Segi Empat
                input1.setHint("Panjang Alas");
                input2.setHint("Lebar Alas");
                input3.setHint("Tinggi Limas");
                input2.setVisibility(View.VISIBLE);
                input3.setVisibility(View.VISIBLE);
                break;
        }
        txtHasil.setText("Hasil akan ditampilkan di sini");
    }

    private void hitungVolumeDanLuasPermukaan() {
        int shape = spinnerShapes.getSelectedItemPosition();
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        String s3 = input3.getText().toString();

        if (s1.isEmpty() || (input2.getVisibility() == View.VISIBLE && s2.isEmpty()) ||
                (input3.getVisibility() == View.VISIBLE && s3.isEmpty())) {
            txtHasil.setText("Mohon lengkapi input.");
            return;
        }

        double v1 = Double.parseDouble(s1);
        double v2 = s2.isEmpty() ? 0 : Double.parseDouble(s2);
        double v3 = s3.isEmpty() ? 0 : Double.parseDouble(s3);

        double volume = 0;
        double luasPermukaan = 0;

        switch (shape) {
            case 0: // Kubus
                volume = Math.pow(v1, 3);
                luasPermukaan = 6 * Math.pow(v1, 2);
                break;

            case 1: // Balok
                volume = v1 * v2 * v3;
                luasPermukaan = 2 * ((v1 * v2) + (v1 * v3) + (v2 * v3));
                break;

            case 2: // Bola
                volume = (4.0 / 3.0) * Math.PI * Math.pow(v1, 3);
                luasPermukaan = 4 * Math.PI * Math.pow(v1, 2);
                break;

            case 3: // Tabung
                volume = Math.PI * Math.pow(v1, 2) * v2;
                luasPermukaan = 2 * Math.PI * v1 * (v1 + v2);
                break;

            case 4: // Limas Segi Empat (dengan perkiraan luas segitiga samping)
                volume = (1.0 / 3.0) * v1 * v2 * v3;
                // Estimasi tinggi sisi miring limas:
                double tinggiSegitiga = Math.sqrt(Math.pow((v1 / 2), 2) + Math.pow(v3, 2));
                double luasAlas = v1 * v2;
                double luasSisi = 2 * (0.5 * v1 * tinggiSegitiga) + 2 * (0.5 * v2 * tinggiSegitiga);
                luasPermukaan = luasAlas + luasSisi;
                break;
        }

        txtHasil.setText(
                "Volume: " + String.format("%.2f", volume) + "\n" +
                        "Luas Permukaan: " + String.format("%.2f", luasPermukaan)
        );
    }
}
```
