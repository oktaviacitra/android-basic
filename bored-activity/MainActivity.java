package com.example.boredactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView activityText, detailsText;
    Button btnRefresh;

    String url = "https://bored-api.appbrewery.com/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityText = findViewById(R.id.activityText);
        detailsText = findViewById(R.id.detailsText);
        btnRefresh = findViewById(R.id.btnRefresh);

        fetchActivity();

        btnRefresh.setOnClickListener(view -> fetchActivity());
    }

    private void fetchActivity() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String activity = response.getString("activity");
                        String type = response.getString("type");
                        int participants = response.getInt("participants");
                        double price = response.getDouble("price");

                        activityText.setText(activity);
                        detailsText.setText("Type: " + type + "\nParticipants: " + participants + "\nPrice: " + price);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Failed to fetch activity", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}