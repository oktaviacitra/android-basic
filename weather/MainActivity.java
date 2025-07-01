package com.example.weather;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtTemperature, txtWind, txtTime;
    String url = "https://api.open-meteo.com/v1/forecast?latitude=-6.2&longitude=106.8&current_weather=true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTemperature = findViewById(R.id.txtTemperature);
        txtWind = findViewById(R.id.txtWind);
        txtTime = findViewById(R.id.txtTime);

        fetchWeather();
    }

    private void fetchWeather() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject current = response.getJSONObject("current_weather");
                            double temperature = current.getDouble("temperature");
                            double windspeed = current.getDouble("windspeed");
                            String time = current.getString("time");

                            txtTemperature.setText("Temperature: " + temperature + "°C");
                            txtWind.setText("Wind Speed: " + windspeed + " km/h");
                            txtTime.setText("Time: " + time);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching weather", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }
}