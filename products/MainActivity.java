package com.example.products;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button btnAdd;
    ArrayList<Product> productList;
    ProductAdapter adapter;

    final int REQUEST_CODE_ADD = 1;
    final int REQUEST_CODE_EDIT = 2;
    int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        productList = new ArrayList<>();

        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = productList.get(position);
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            editPosition = position;
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            productList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Produk dihapus", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String price = data.getStringExtra("price");

            if (requestCode == REQUEST_CODE_ADD) {
                productList.add(new Product(name, price));
            } else if (requestCode == REQUEST_CODE_EDIT && editPosition != -1) {
                productList.set(editPosition, new Product(name, price));
            }

            adapter.notifyDataSetChanged();
        }
    }
}
