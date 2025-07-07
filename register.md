# Buat proyek baru
**Empty Views Activity**
- Nama: Register
- Language: Java
- Build configuration language: Groovy DSL
  
# Membangun halaman utama
## activity_main.xml
Kode program ini mendefinisikan layout relatif dengan sebuah TextView yang menampilkan teks "Welcome to SharedPreferences App" di tengah layar.

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/splashText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome to SharedPreferences App"
        android:textSize="24sp"
        android:layout_centerInParent="true" />
</RelativeLayout>
```
## MainActivity.java
- Kode ini memeriksa apakah pengguna sudah login dengan membaca nilai username dari SharedPreferences, jika ada, aplikasi akan membuka HomeActivity, jika tidak, akan membuka RegisterActivity.
- Setelah memulai aktivitas baru (baik HomeActivity atau RegisterActivity), MainActivity ditutup menggunakan finish() agar tidak kembali ke layar utama.

```
package com.example.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cek apakah pengguna sudah login atau belum
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        // Jika username ada, berarti pengguna sudah login, pindah ke HomeActivity
        if (username != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // Jika belum login, arahkan ke RegisterActivity
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        // Tutup MainActivity setelah berpindah halaman
        finish();
    }
}
```

# Membangun halaman register
## activity_register.xml
- Kode ini mendefinisikan layout vertikal dengan beberapa elemen input seperti EditText untuk nama lengkap, username, password, dan tanggal lahir, serta dua tombol untuk pengiriman dan navigasi ke halaman login.
- Layout menggunakan LinearLayout dengan padding 16dp, di mana setiap elemen input dan tombol memiliki lebar penuh (match_parent) dan tinggi yang disesuaikan dengan konten.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/fullName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Full Name" />

    <EditText
        android:id="@+id/username"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Username" />

    <EditText
        android:id="@+id/password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword" />

    <EditText
        android:id="@+id/birthDate"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Birth Date (DD/MM/YYYY)"
        android:focusable="false"
        android:clickable="true" />

    <Button
        android:id="@+id/submitButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Submit" />

    <Button
        android:id="@+id/loginButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Go to Login" />
</LinearLayout>
```
## RegisterActivity.java
- Inisialisasi Komponen UI: Kode ini menghubungkan komponen UI seperti EditText untuk input nama lengkap, username, password, dan tanggal lahir, serta dua tombol: satu untuk mengirim data pendaftaran dan satu lagi untuk menuju halaman login.
- Pengaturan DatePicker untuk Tanggal Lahir: Ketika pengguna mengklik kolom birthDateEditText, sebuah DatePickerDialog akan muncul, memungkinkan pengguna untuk memilih tanggal lahir. Setelah memilih, tanggal yang dipilih akan diformat dan ditampilkan di kolom tersebut.
- Proses Pendaftaran Pengguna: Ketika tombol submitButton diklik, aplikasi memeriksa apakah semua kolom terisi. Jika tidak, pesan kesalahan ditampilkan. Jika semua kolom terisi, data pengguna (nama lengkap, username, password, tanggal lahir) disimpan di SharedPreferences.
- Penyimpanan Data di SharedPreferences: Setelah data berhasil disubmit, data pengguna disimpan di SharedPreferences untuk digunakan pada proses login berikutnya. Pesan sukses ditampilkan menggunakan Toast.
- Navigasi ke Halaman Login: Ketika tombol loginButton diklik, aplikasi akan mengalihkan pengguna ke halaman login (LoginActivity), dan halaman pendaftaran (RegisterActivity) ditutup menggunakan finish().
```
package com.example.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText fullNameEditText, usernameEditText, passwordEditText, birthDateEditText;
    Button submitButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameEditText = findViewById(R.id.fullName);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        birthDateEditText = findViewById(R.id.birthDate);
        submitButton = findViewById(R.id.submitButton);
        loginButton = findViewById(R.id.loginButton);

        // Setting up DatePicker for Birth Date
        birthDateEditText.setOnClickListener(v -> showDatePickerDialog());

        // Handle submit button click
        submitButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String birthDate = birthDateEditText.getText().toString();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || birthDate.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save data to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("fullName", fullName);
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("birthDate", birthDate);
            editor.apply(); // Save the data

            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
        });

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to show DatePickerDialog
    private void showDatePickerDialog() {
        // Get current date to set as default
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date and set it to EditText
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    birthDateEditText.setText(sdf.format(calendar.getTime()));
                }, year, month, day);

        // Show DatePickerDialog
        datePickerDialog.show();
    }
}
```
# Membangun halaman login
## activity_login.xml
- Layout Vertikal: Kode ini menggunakan LinearLayout dengan orientasi vertikal, di mana semua elemen UI disusun dari atas ke bawah, dan terdapat padding 16dp di sekitar konten.
- EditText untuk Input: Terdapat dua EditText untuk input pengguna: satu untuk username (loginUsername) dan satu lagi untuk password (loginPassword), di mana password memiliki tipe input sebagai textPassword untuk keamanan.
- Tombol Login dan Register: Ada dua tombol: tombol loginButton untuk memproses login pengguna, dan tombol registerButton yang mengarahkan pengguna ke halaman pendaftaran (Register).
- Pengaturan Ukuran: Semua elemen memiliki lebar yang sesuai dengan lebar tampilan (match_parent) dan tinggi disesuaikan dengan konten (wrap_content).
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/loginUsername"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Username" />

    <EditText
        android:id="@+id/loginPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword" />

    <Button
        android:id="@+id/loginButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Login" />

    <Button
        android:id="@+id/registerButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Go to Register" />
</LinearLayout>
```
## LoginActivity.java
- Inisialisasi Tampilan dan Komponen UI: Kode ini menghubungkan komponen UI seperti EditText untuk input username dan password, serta dua tombol (loginButton dan registerButton) untuk login dan registrasi.
- Proses Login: Ketika tombol login diklik, program memeriksa apakah username dan password tidak kosong. Kemudian, data username dan password yang tersimpan di SharedPreferences dibandingkan dengan input pengguna; jika cocok, pengguna diarahkan ke HomeActivity, jika tidak, pesan error ditampilkan menggunakan Toast.
- Pendaftaran Pengguna Baru: Jika tombol registrasi diklik, aplikasi akan membuka RegisterActivity, memungkinkan pengguna untuk melakukan pendaftaran baru.
- Menangani Kejadian Klik: Setiap tombol memiliki listener yang menangani aksi sesuai, baik itu login dengan memvalidasi kredensial atau membuka halaman registrasi untuk pengguna yang belum memiliki akun.
```
package com.example.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.loginUsername);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Retrieve stored data from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String storedUsername = sharedPreferences.getString("username", null);
            String storedPassword = sharedPreferences.getString("password", null);

            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                // Login successful
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Login failed
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle register button click
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
```
# Membangun halaman home
## activity_home.java
- Layout Vertikal dengan Padding: Kode ini menggunakan LinearLayout dengan orientasi vertikal, dimana semua elemen UI disusun dari atas ke bawah, dan terdapat padding 16dp di sekitar konten.
- Tiga TextView untuk Menampilkan Data Pengguna: Ada tiga TextView yang menampilkan informasi pengguna: fullNameTextView, usernameTextView, dan birthDateTextView, masing-masing dengan teks yang menunjukkan nama lengkap, username, dan tanggal lahir, serta ukuran teks 18sp.
- Tombol Logout: Terdapat tombol logoutButton yang, ketika diklik, memungkinkan pengguna untuk keluar dari aplikasi atau beralih ke aktivitas lain untuk logout.
- Pengaturan Ukuran Elemen UI: Semua elemen, seperti TextView dan tombol, memiliki lebar yang disesuaikan dengan lebar tampilan (match_parent) dan tinggi yang disesuaikan dengan konten (wrap_content).
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/fullNameTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Full Name: "
        android:textSize="18sp" />

    <TextView
        android:id="@+id/usernameTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Username: "
        android:textSize="18sp" />

    <TextView
        android:id="@+id/birthDateTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Birth Date: "
        android:textSize="18sp" />

    <Button
        android:id="@+id/logoutButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Logout" />
</LinearLayout>
```
## HomeActivity.java
- Inisialisasi Tampilan: Kode ini menghubungkan komponen UI seperti TextView untuk menampilkan nama lengkap, username, dan tanggal lahir, serta tombol logout yang digunakan untuk keluar dari aplikasi.
- Membaca Data dari SharedPreferences: Data seperti nama lengkap, username, dan tanggal lahir diambil dari SharedPreferences dan ditampilkan pada tampilan TextView.
- Tombol Logout: Ketika tombol logout diklik, aplikasi berpindah ke RegisterActivity menggunakan Intent, dan HomeActivity ditutup dengan finish().
- Pemrosesan UI: Setiap TextView menampilkan data yang sesuai dengan informasi yang disimpan di SharedPreferences, memberikan informasi pengguna yang telah terdaftar.
```
package com.example.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView fullNameTextView, usernameTextView, birthDateTextView;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fullNameTextView = findViewById(R.id.fullNameTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        birthDateTextView = findViewById(R.id.birthDateTextView);
        logoutButton = findViewById(R.id.logoutButton);

        // Retrieve stored data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", null);
        String username = sharedPreferences.getString("username", null);
        String birthDate = sharedPreferences.getString("birthDate", null);

        // Display the data
        fullNameTextView.setText("Full Name: " + fullName);
        usernameTextView.setText("Username: " + username);
        birthDateTextView.setText("Birth Date: " + birthDate);

        // Handle logout button click
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
```
# Isi app/res/values/strings.xml
<img width="292" alt="Screenshot 2025-07-07 at 22 02 57" src="https://github.com/user-attachments/assets/1be2b70f-12a3-45fa-a296-5cc5bff1522b" />

```
<resources>
    <!-- Aplikasi Name -->
    <string name="app_name">SharedPreferences App</string>

    <!-- Label untuk aktivitas -->
    <string name="register">Register</string>
    <string name="login">Login</string>
    <string name="home">Home</string>
</resources>
```

# Modifikasi app/manifests/AndroidManifest.xml

<img width="239" alt="Screenshot 2025-07-07 at 22 07 35" src="https://github.com/user-attachments/assets/da5ea8d2-0a87-4d81-8d7e-f9576a6038d8" />

<img width="723" alt="Screenshot 2025-07-07 at 22 06 33" src="https://github.com/user-attachments/assets/4e600507-c73b-4dc3-8ac5-6753f39b5b70" />

```
android:theme="@style/Theme.MaterialComponents.DayNight.NoActionBar"
```

```
        <!-- RegisterActivity - Halaman untuk registrasi pengguna -->
        <activity android:name=".RegisterActivity" android:label="@string/register"/>
        <!-- LoginActivity - Halaman untuk login pengguna -->
        <activity android:name=".LoginActivity" android:label="@string/login"/>
        <!-- HomeActivity - Halaman utama setelah login -->
        <activity android:name=".HomeActivity" android:label="@string/home"/>
```

# Hasil

https://github.com/user-attachments/assets/1e7107b6-b034-418b-9bda-bbb911b785d7

