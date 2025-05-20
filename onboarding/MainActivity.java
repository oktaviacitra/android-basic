package com.example.onboarding;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button buttonNext;
    private OnboardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        buttonNext = findViewById(R.id.buttonNext);

        setupOnboardingItems();

        buttonNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                // lanjut ke halaman utama atau tutup onboarding
                finish(); // atau: startActivity(new Intent(...))
            }
        });
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(R.drawable.laundry, "Temukan Cerita yang Menginspirasi", "Jelajahi kisah nyata, imajinatif, dan personal dari pengguna lain yang suka berbagi cerita setiap hari."));
        items.add(new OnboardingItem(R.drawable.cleaner, "Bagikan Ceritamu Tanpa Batas", "Tulis cerita, pengalaman, atau perasaanmu dengan bebas. Di sini, semua orang punya suara."));
        items.add(new OnboardingItem(R.drawable.machine, "Gabung dengan Komunitas Cerita", "Dapatkan dukungan, komentar, dan teman baru dari sesama penulis dan pembaca. Ayo mulai kisahmu hari ini!"));

        adapter = new OnboardingAdapter(items);
        viewPager.setAdapter(adapter);
    }
}
