package com.example.contacts;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);

        loadContactsFromCSV();
        ContactAdapter adapter = new ContactAdapter(this, contacts);
        listView.setAdapter(adapter);
    }

    private void loadContactsFromCSV() {
        try {
            InputStream is = getAssets().open("contacts.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    contacts.add(new Contact(tokens[0].trim(), tokens[1].trim()));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}