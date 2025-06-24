package com.example.products;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {
    EditText etName, etPrice;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        if (intent != null) {
            etName.setText(intent.getStringExtra("name"));
            etPrice.setText(intent.getStringExtra("price"));
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String price = etPrice.getText().toString();

            if (!name.isEmpty() && !price.isEmpty()) {
                Intent result = new Intent();
                result.putExtra("name", name);
                result.putExtra("price", price);
                setResult(RESULT_OK, result);
                finish();
            } else {
                Toast.makeText(this, "Lengkapi data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
