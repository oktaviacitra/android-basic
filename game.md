# Daftar Game
- [Guess Number](#guess-number)
- [Tap](#tap)
- [Suit](#suit)
- [Bomb](#bomb)

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

<img width="266" height="566" alt="Screenshot 2025-07-15 at 12 32 15" src="https://github.com/user-attachments/assets/8c0331c0-1dbe-40f5-9489-54a2054b052a" />

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
## MainActivity.java
Kode program di atas merupakan implementasi dari game Android sederhana bernama Tap Hitung, yang dibangun menggunakan Java dan dijalankan dalam satu aktivitas (MainActivity). Game ini menguji kecepatan pemain dalam mengetuk tombol sebanyak mungkin dalam waktu 10 detik. Saat pemain menekan tombol "Start", fungsi mulaiGame() dijalankan: skor di-reset ke nol, tombol "Tap" diaktifkan, dan timer mulai menghitung mundur setiap detik menggunakan CountDownTimer. Setiap kali tombol "Tap" ditekan, nilai skor bertambah satu dan ditampilkan di TextView. Setelah 10 detik, tombol "Tap" dinonaktifkan, timer berhenti, dan hasil akhir ditampilkan melalui Toast. Game ini sederhana namun efektif untuk melatih refleks pengguna dalam antarmuka yang minimalis.
```
package com.example.tap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore, tvTimer;
    private Button btnTap, btnStart;
    private int score = 0;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        btnTap = findViewById(R.id.btnTap);
        btnStart = findViewById(R.id.btnStart);

        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                tvScore.setText("Skor: " + score);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mulaiGame();
            }
        });
    }

    private void mulaiGame() {
        score = 0;
        tvScore.setText("Skor: 0");
        btnTap.setEnabled(true);
        btnStart.setEnabled(false);

        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Waktu: " + millisUntilFinished / 1000 + " detik");
            }

            public void onFinish() {
                btnTap.setEnabled(false);
                tvTimer.setText("Waktu: 0 detik");
                Toast.makeText(MainActivity.this, "Waktu Habis! Skor: " + score, Toast.LENGTH_LONG).show();
                btnStart.setEnabled(true);
            }
        }.start();
    }
}
```
# Suit
Game Suit Jawa adalah versi digital dari permainan klasik “Batu, Gunting, Kertas” yang dibangun menggunakan Java dalam satu halaman aplikasi Android. Dalam game ini, pemain memilih salah satu dari tiga opsi—Batu, Gunting, atau Kertas—melalui tombol, lalu sistem secara acak memilih opsi lawan. Setelah keduanya memilih, aplikasi secara otomatis menampilkan hasil pertandingan berupa menang, kalah, atau seri berdasarkan aturan tradisional: Batu mengalahkan Gunting, Gunting mengalahkan Kertas, dan Kertas mengalahkan Batu. Game ini sederhana, cepat dimainkan, dan cocok untuk hiburan ringan sambil melatih logika reaksi pengguna.

<img width="268" height="560" alt="Screenshot 2025-07-15 at 12 31 06" src="https://github.com/user-attachments/assets/68921034-d9bf-46a9-ab2b-93c838512f9b" />

## activity_main.xml
Kode XML di atas merupakan tampilan antarmuka (UI) untuk game Android sederhana “Suit Jawa” yang dibuat menggunakan LinearLayout dengan orientasi vertikal dan posisi elemen terpusat. Terdapat sebuah TextView dengan ID tvResult yang berfungsi untuk menampilkan hasil permainan, seperti menang, kalah, atau seri. Di bawahnya terdapat tiga buah tombol (Button) dengan ID masing-masing btnBatu, btnGunting, dan btnKertas, yang memungkinkan pemain memilih salah satu dari opsi permainan Batu, Gunting, atau Kertas. Desain ini dibuat simpel dan responsif agar mudah digunakan serta memberikan pengalaman bermain yang cepat dan menyenangkan bagi pengguna.
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp"
    android:gravity="center">

    <TextView
        android:id="@+id/tvResult"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Pilih tanganmu!"
        android:textSize="20sp"
        android:layout_marginBottom="20dp"/>

    <Button
        android:id="@+id/btnBatu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="🪨 Batu"
        android:layout_marginBottom="10dp"/>

    <Button
        android:id="@+id/btnGunting"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="✂️ Gunting"
        android:layout_marginBottom="10dp"/>

    <Button
        android:id="@+id/btnKertas"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="📄 Kertas"/>
</LinearLayout>
```
## MainActivity.java
Kode program Java di atas merupakan implementasi logika game Suit Jawa (Batu, Gunting, Kertas) dalam aplikasi Android satu halaman. Di dalam kelas MainActivity, pemain dapat memilih salah satu dari tiga tombol—Batu, Gunting, atau Kertas—yang kemudian dibandingkan dengan pilihan acak dari komputer menggunakan objek Random. Fungsi mainkan() akan menentukan hasil permainan berdasarkan aturan klasik suit: Batu mengalahkan Gunting, Gunting mengalahkan Kertas, dan Kertas mengalahkan Batu. Hasil permainan ditampilkan melalui TextView tvResult, yang menampilkan pesan menang, kalah, atau seri beserta pilihan komputer. Struktur kode ini memanfaatkan lambda expressions untuk mempercepat penanganan event tombol, dan menyederhanakan interaksi pengguna dalam antarmuka yang intuitif.
```
package com.example.suit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnBatu, btnGunting, btnKertas;

    private String[] pilihan = {"Batu", "Gunting", "Kertas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        btnBatu = findViewById(R.id.btnBatu);
        btnGunting = findViewById(R.id.btnGunting);
        btnKertas = findViewById(R.id.btnKertas);

        btnBatu.setOnClickListener(v -> mainkan("Batu"));
        btnGunting.setOnClickListener(v -> mainkan("Gunting"));
        btnKertas.setOnClickListener(v -> mainkan("Kertas"));
    }

    private void mainkan(String pilihanPlayer) {
        String pilihanKomputer = pilihan[new Random().nextInt(pilihan.length)];
        String hasil;

        if (pilihanPlayer.equals(pilihanKomputer)) {
            hasil = "🤝 Seri! Komputer juga memilih " + pilihanKomputer;
        } else if (
                (pilihanPlayer.equals("Batu") && pilihanKomputer.equals("Gunting")) ||
                (pilihanPlayer.equals("Gunting") && pilihanKomputer.equals("Kertas")) ||
                (pilihanPlayer.equals("Kertas") && pilihanKomputer.equals("Batu"))
        ) {
            hasil = "🎉 Kamu MENANG!\nKomputer memilih " + pilihanKomputer;
        } else {
            hasil = "💀 Kamu KALAH!\nKomputer memilih " + pilihanKomputer;
        }

        tvResult.setText(hasil);
    }
}
```
# Bomb
Game Hindari Bom adalah permainan sederhana berbasis Android yang menantang pemain untuk menekan tombol-tombol secara hati-hati dalam sebuah grid (misalnya 3x3) tanpa memilih tombol yang tersembunyi bomnya. Di awal permainan, beberapa tombol dipilih secara acak sebagai bom dan sisanya aman. Pemain akan mendapatkan poin setiap kali berhasil menekan tombol yang aman, dan permainan akan langsung berakhir (game over) jika pemain menekan tombol yang berisi bom. Terdapat fitur reset yang memungkinkan pemain untuk memulai ulang permainan dengan posisi bom yang juga diacak ulang, sehingga meningkatkan aspek tantangan dan replayability dari game ini.

<img width="273" height="566" alt="Screenshot 2025-07-15 at 12 41 42" src="https://github.com/user-attachments/assets/c49fd1c3-a4ce-47e9-90e2-e5238da0f00f" />

## activity_main.xml
Tampilan antarmuka game Hindari Bom ini dirancang menggunakan elemen-elemen layout Android seperti LinearLayout dengan orientasi vertikal yang memusatkan komponen secara simetris di layar. Di bagian atas terdapat TextView yang menampilkan skor pemain secara real-time, diikuti oleh GridLayout berukuran 3x3 yang menampung tombol-tombol sebagai area permainan tempat pemain menekan kotak untuk menghindari bom. Setiap tombol dapat berubah warna dan memberikan umpan balik tergantung apakah pemain memilih tombol aman atau berisi bom. Di bagian bawah terdapat tombol Reset Game yang memungkinkan pemain memulai permainan baru dengan posisi bom yang diacak ulang, membuat game ini mudah dimainkan berulang kali dengan tingkat keseruan yang konsisten.
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <TextView
        android:id="@+id/tvScore"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Skor: 0"
        android:textSize="18sp"
        android:layout_marginBottom="16dp"/>

    <GridLayout
        android:id="@+id/grid"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:rowCount="3"
        android:columnCount="3"
        android:alignmentMode="alignMargins"
        android:useDefaultMargins="true"/>

    <Button
        android:id="@+id/btnReset"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Reset Game"
        android:layout_marginTop="16dp"/>
</LinearLayout>
```
## MainActivity.java
Kode program Java di atas merupakan implementasi dari game Android sederhana bernama Hindari Bom, di mana pemain harus menekan tombol-tombol dalam grid 3x3 tanpa mengenai bom tersembunyi. Game ini menggunakan GridLayout untuk menampilkan sembilan tombol, dan secara acak memilih tiga tombol sebagai lokasi bom yang disimpan dalam Set<Integer>. Setiap kali pemain menekan tombol, sistem memeriksa apakah tombol tersebut mengandung bom. Jika ya, tombol berubah warna merah dan permainan berakhir; jika tidak, tombol berubah hijau dan skor pemain bertambah. Terdapat juga fitur pengecekan kemenangan jika semua tombol aman berhasil ditekan. Selain itu, game menyediakan tombol reset yang dapat digunakan untuk mengulang permainan dengan lokasi bom yang baru secara acak, menjadikan game ini dinamis dan menantang.
```
package com.example.bomb;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private TextView tvScore;
    private Button btnReset;
    private int score = 0;
    private Set<Integer> lokasiBom = new HashSet<>();
    private final int JUMLAH_BOM = 1;
    private final int TOTAL_TOMBOL = 9; // 3x3 grid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = findViewById(R.id.tvScore);
        grid = findViewById(R.id.grid);
        btnReset = findViewById(R.id.btnReset);

        setupGame();

        btnReset.setOnClickListener(v -> {
            setupGame();
            Toast.makeText(this, "Game direset!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupGame() {
        lokasiBom.clear();
        score = 0;
        tvScore.setText("Skor: 0");
        grid.removeAllViews();

        // Acak lokasi bom
        Random rand = new Random();
        while (lokasiBom.size() < JUMLAH_BOM) {
            lokasiBom.add(rand.nextInt(TOTAL_TOMBOL));
        }

        // Buat tombol-tombol
        for (int i = 0; i < TOTAL_TOMBOL; i++) {
            Button btn = new Button(this);
            btn.setText("");
            int index = i;

            btn.setOnClickListener(v -> {
                if (lokasiBom.contains(index)) {
                    btn.setBackgroundColor(Color.RED);
                    btn.setText("💣");
                    Toast.makeText(this, "Game Over! Skor akhir: " + score, Toast.LENGTH_LONG).show();
                    nonaktifkanSemuaTombol();
                } else {
                    btn.setBackgroundColor(Color.GREEN);
                    btn.setEnabled(false);
                    score++;
                    tvScore.setText("Skor: " + score);

                    if (score == TOTAL_TOMBOL - JUMLAH_BOM) {
                        Toast.makeText(this, "🎉 Menang! Semua tombol aman ditekan!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            grid.addView(btn);
        }
    }

    private void nonaktifkanSemuaTombol() {
        for (int i = 0; i < grid.getChildCount(); i++) {
            Button btn = (Button) grid.getChildAt(i);
            btn.setEnabled(false);
        }
    }
}
```
