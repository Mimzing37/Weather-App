package com.example.weather3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView country_yt, city_yt, temp_yt,time , Latitude , sunrise , sunset , wind , pressure , lon , humi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        country_yt = findViewById(R.id.country);
        city_yt = findViewById(R.id.city);
        temp_yt = findViewById(R.id.textView5);
        time = findViewById(R.id.time_yt);

        //imageView = findViewById(R.id.imageButton2);

        Latitude = findViewById(R.id.Latitude);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        lon = findViewById(R.id.Longitude);
        wind = findViewById(R.id.WindeSpeed);
        pressure = findViewById(R.id.Pressure);
        humi = findViewById(R.id.Humidity);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findweather();
            }
        });


    }

    public void findweather() {
        String city = editText.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=bcefa9e02e7b7f1c8a7a73033fe480f4&units=metric";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //call api

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object1 = jsonObject.getJSONObject("sys");

                            //find country
                            String country_find = object1.getString("country");
                            country_yt.setText(country_find + ":");

                            //find city
                            String city_find = jsonObject.getString("name");
                            city_yt.setText(city_find);

                            //find temp
                            JSONObject object2 = jsonObject.getJSONObject("main");
                            String temp_find = object2.getString("temp");
                            temp_yt.setText(temp_find + "°C");


                            //find Latitude
                            JSONObject object3 = jsonObject.getJSONObject("coord");
                            double lat_find = object3.getDouble("lat");
                            Latitude.setText(lat_find+"°  N");

                            //find Lon
                            JSONObject object4 = jsonObject.getJSONObject("coord");
                            double lon_find = object3.getDouble("lon");
                            lon.setText(lon_find+"°  E");

                            //find Humi
                            JSONObject object5 = jsonObject.getJSONObject("main");
                            int hum_find = object5.getInt("humidity");
                            humi.setText(hum_find+"  %");

                            //find sunrise
                            JSONObject object6 = jsonObject.getJSONObject("sys");
                            String  sun_find = object6.getString("sunrise");
                            sunrise.setText(sun_find);


                            //find sunset
                            JSONObject object7 = jsonObject.getJSONObject("sys");
                            String  sunr_find = object7.getString("sunset");
                            sunset.setText(sunr_find);

                            //find Press
                            JSONObject object8 = jsonObject.getJSONObject("main");
                            String  pre_find = object8.getString("pressure");
                            pressure.setText(pre_find+ " hPa");

                            //find wind
                            JSONObject object9 = jsonObject.getJSONObject("wind");
                            String  wind_find = object9.getString("speed");
                            wind.setText(wind_find+" km/h");

                            //find time and date
                            Calendar calendar= Calendar.getInstance();
                            SimpleDateFormat std = new SimpleDateFormat("dd/mm/yyyy\n HH:mm:ss");
                            String date = std.format(calendar.getTime());
                            time.setText(date);


                            // grid image icon
                            //  JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            //  JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            //  String img = jsonObject1.getString("icon");
                            //  Picasso.get().load("https://openweathermap.org/img/wn/"
                            //     +img+"@2x.png").into(imageView);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashScreen.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
        requestQueue.add(stringRequest);

    }

    //Broad CAST
    public void boradcastIntent(View v) {
        Intent intent = new Intent();
        intent.setAction("com.codingpursiuts.myBroadcastMessage");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        //Broad CAST
    }
}

