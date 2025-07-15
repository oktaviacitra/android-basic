# Daftar Game
- [Guess Number](#guess-number)
- [Tap](#tap)


# Guess Number
Game Tebak Angka adalah permainan edukatif sederhana berbasis Android yang dibuat menggunakan Java dan hanya menggunakan satu halaman (satu Activity). Dalam permainan ini, pengguna diminta menebak sebuah angka rahasia antara 1 hingga 10 yang dipilih secara acak oleh sistem. Pemain diberikan kesempatan maksimal tiga kali untuk menebak angka yang benar. Jika pemain menebak dengan benar, permainan menampilkan pesan kemenangan dan tombol cek dinonaktifkan. Namun, jika pemain melakukan tiga kali kesalahan berturut-turut, permainan akan berakhir dengan pesan Game Over dan juga menonaktifkan tombol cek. Fitur ini mengajarkan logika percobaan terbatas dan meningkatkan ketelitian pengguna dalam membuat keputusan.

<img width="265" height="568" alt="Screenshot 2025-07-15 at 12 13 42" src="https://github.com/user-attachments/assets/b1c7c1ee-55d0-4e6d-9850-c1df0a161197" />

## activity_main.xml
Kode program di atas merupakan file layout XML untuk aplikasi Android berbasis game Tebak Angka yang dirancang menggunakan komponen LinearLayout dengan orientasi vertikal. Layout ini menampilkan antarmuka pengguna yang terdiri dari beberapa elemen UI, yaitu TextView sebagai judul game, EditText untuk memasukkan tebakan angka, dua buah Button masing-masing untuk mengecek jawaban (btnCheck) dan mereset permainan (btnReset), serta TextView lain untuk menampilkan hasil atau feedback dari tebakan yang dimasukkan (tvResult). Komponen-komponen ini diatur secara terpusat di layar dengan padding 24dp dan margin antar elemen agar tampilan lebih rapi dan mudah digunakan oleh pemain. Input angka dibatasi melalui inputType="number" untuk memastikan hanya angka yang bisa dimasukkan.
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp"
    android:orientation="vertical"
    android:gravity="center">

    <TextView
        android:id="@+id/tvTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tebak Angka (0 - 9)"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="20dp" />

    <EditText
        android:id="@+id/etGuess"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Masukkan tebakan"
        android:inputType="number"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnCheck"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Cek Jawaban"
        android:layout_marginBottom="16dp"/>

    <TextView
        android:id="@+id/tvResult"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text=""
        android:textSize="18sp"
        android:textStyle="italic"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnReset"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Main Lagi" />
</LinearLayout>
```
## MainActivity
Kode program Java di atas merupakan implementasi dari logika utama game Android Tebak Angka yang dijalankan di dalam kelas MainActivity. Aplikasi ini menghasilkan angka rahasia secara acak antara 1 hingga 10 menggunakan objek Random, kemudian meminta pengguna menebak angka tersebut melalui input EditText. Program akan membandingkan angka tebakan dengan angka rahasia setiap kali tombol "Cek Jawaban" diklik. Jika tebakan benar, pengguna akan diberi pesan sukses dan tombol cek dinonaktifkan. Namun jika salah, jumlah kesalahan akan bertambah, dan jika kesalahan mencapai batas maksimal (3 kali), maka permainan berakhir dengan menampilkan pesan Game Over dan mematikan tombol cek. Tersedia juga tombol "Main Lagi" (Reset) yang memungkinkan pengguna memulai ulang permainan dengan mereset skor dan menghasilkan angka baru. Seluruh logika interaksi pengguna dan pembaruan UI ditangani di dalam metode onCreate, sementara angka acak dihasilkan dalam metode generateAngka().
```
package com.example.guessnumber;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int angkaRahasia;
    private int kesalahan = 0;
    private final int MAKSIMAL_SALAH = 3;

    private EditText etGuess;
    private TextView tvResult;
    private Button btnCheck, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGuess = findViewById(R.id.etGuess);
        tvResult = findViewById(R.id.tvResult);
        btnCheck = findViewById(R.id.btnCheck);
        btnReset = findViewById(R.id.btnReset);

        generateAngka();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tebakanStr = etGuess.getText().toString();
                if (tebakanStr.isEmpty()) {
                    tvResult.setText("Masukkan angka terlebih dahulu!");
                    return;
                }

                int tebakan = Integer.parseInt(tebakanStr);

                if (tebakan == angkaRahasia) {
                    tvResult.setText("🎉 Tebakan kamu BENAR!");
                    btnCheck.setEnabled(false);
                } else {
                    kesalahan++;
                    if (kesalahan >= MAKSIMAL_SALAH) {
                        tvResult.setText("💀 Game Over! Kamu salah 3 kali.\nAngka yang benar: " + angkaRahasia);
                        btnCheck.setEnabled(false);
                    } else {
                        tvResult.setText("❌ Salah! Kesempatan tersisa: " + (MAKSIMAL_SALAH - kesalahan));
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAngka();
                etGuess.setText("");
                tvResult.setText("");
                kesalahan = 0;
                btnCheck.setEnabled(true);
                Toast.makeText(MainActivity.this, "Game direset!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateAngka() {
        angkaRahasia = new Random().nextInt(10) + 1; // 1 - 10
    }
}
```
# Tap
Game Tap Hitung adalah permainan Android sederhana yang dirancang untuk menguji kecepatan dan refleks pemain dalam mengetuk tombol sebanyak mungkin dalam waktu 10 detik. Game ini dibangun hanya menggunakan satu halaman (MainActivity) dengan bahasa pemrograman Java di Android Studio. Saat pemain menekan tombol "Start", permainan dimulai dan tombol "Tap" akan diaktifkan. Setiap ketukan akan dihitung dan ditampilkan sebagai skor. Setelah waktu habis, skor akhir ditampilkan dan pemain dapat memilih untuk mengulang permainan. Game ini cocok sebagai latihan reaksi cepat dan bisa dimainkan secara kasual tanpa perlu banyak instruksi.
## activity_main.java
Layout XML di atas digunakan untuk membuat antarmuka game Android sederhana berbasis satu halaman, di mana pemain diminta mengetuk tombol secepat mungkin dalam waktu terbatas. Layout menggunakan LinearLayout dengan orientasi vertikal dan berpusat di tengah layar. Komponen TextView pertama (tvTimer) menampilkan sisa waktu permainan, sedangkan TextView kedua (tvScore) menunjukkan skor atau jumlah ketukan yang berhasil dilakukan pemain. Tombol btnTap digunakan sebagai tombol utama untuk mengetuk, yang awalnya dinonaktifkan dan hanya bisa digunakan setelah permainan dimulai. Sementara itu, btnStart berfungsi untuk memulai permainan, mengaktifkan tombol tap, mengatur ulang skor, dan memulai hitung mundur waktu. Tampilan ini memberikan struktur yang sederhana dan intuitif untuk game berbasis waktu dan refleks.
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="24dp">

    <TextView
        android:id="@+id/tvTimer"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Waktu: 10 detik"
        android:textSize="20sp"
        android:layout_marginBottom="16dp" />

    <TextView
        android:id="@+id/tvScore"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Skor: 0"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="24dp" />

    <Button
        android:id="@+id/btnTap"
        android:layout_width="200dp"
        android:layout_height="100dp"
        android:text="TAP!"
        android:textSize="20sp"
        android:enabled="false"
        android:layout_marginBottom="24dp"/>

    <Button
        android:id="@+id/btnStart"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Mulai Game" />
</LinearLayout>
```
