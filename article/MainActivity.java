package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView title, paragraph1, paragraph2, paragraph3;
    ImageView articleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ambil referensi dari layout
        title = findViewById(R.id.articleTitle);
        paragraph1 = findViewById(R.id.articleParagraph1);
        paragraph2 = findViewById(R.id.articleParagraph2);
        paragraph3 = findViewById(R.id.articleParagraph3);
        articleImage = findViewById(R.id.articleImage);

        // Set konten artikel dari Java
        title.setText("Pesona Swiss: Negeri di Tengah Pegunungan Alpen");

        paragraph1.setText("Swiss, negara kecil di jantung Eropa, menyimpan keindahan alam dan budaya yang luar biasa. Dikenal dengan pemandangan Pegunungan Alpen yang menakjubkan, danau yang jernih, serta kota-kota yang tertata rapi, Swiss menjadi destinasi impian bagi para pelancong dari seluruh dunia.");

        paragraph2.setText("Tak hanya alam, Swiss juga dikenal dengan kota-kotanya yang indah dan bersih. Zurich sebagai pusat ekonomi, Bern yang penuh sejarah, hingga Lucerne yang romantis di tepi danau, semuanya memiliki pesona tersendiri.");

        paragraph3.setText("Dengan kombinasi alam yang luar biasa, budaya yang kaya, dan kehidupan yang tertib, Swiss layak disebut sebagai salah satu negara paling menawan di dunia.");

        // (Opsional) Ubah gambar programmatically jika perlu
        // articleImage.setImageResource(R.drawable.gambar_lain);
    }
}
