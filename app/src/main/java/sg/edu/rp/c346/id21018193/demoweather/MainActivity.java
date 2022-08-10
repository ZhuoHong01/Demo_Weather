package sg.edu.rp.c346.id21018193.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvWeather;
    AsyncHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvWeather = findViewById(R.id.listviewWeather);

        client = new AsyncHttpClient();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("onResume", "in onResume");

        ArrayList<Weather> alWeather = new ArrayList<>();
//        ArrayAdapter<Weather> aaWeather = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, alWeather);
//        lvWeather.setAdapter(aaWeather);

        client.get("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast", new JsonHttpResponseHandler() {

            String area;
            String forecast;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts");
                    for(int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        area = jsonObjForecast.getString("area");
                        forecast = jsonObjForecast.getString("forecast");
                        Weather weather = new Weather(area, forecast);
                        alWeather.add(weather);
                        Log.d("weather", weather.getForecast());
                    }
                }
                catch(JSONException ignored){
                    Log.d("exception", ignored.toString());
                }

                //POINT X – Code to display List View
                ArrayAdapter<Weather> aaWeather = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);
                lvWeather.setAdapter(aaWeather);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("error", errorResponse.toString());
            }

//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//
//                Log.d("onSuccess", "in onSuccess");
//
//                try {
//                    JSONArray jsonArrItems = response.getJSONArray(Integer.parseInt("items"));
//                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
//                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts");
//                    for(int i = 0; i < jsonArrForecasts.length(); i++) {
//                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
//                        area = jsonObjForecast.getString("area");
//                        forecast = jsonObjForecast.getString("forecast");
//                        Weather weather = new Weather(area, forecast);
//                        alWeather.add(weather);
//                        Log.d("weather", weather.getForecast());
//                    }
//                }
//                catch(JSONException ignored){
//                    Log.d("exception", ignored.toString());
//                }
//
//                //POINT X – Code to display List View
//                ArrayAdapter<Weather> aaWeather = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);
//                lvWeather.setAdapter(aaWeather);
////                aaWeather.notifyDataSetChanged();
//        //        lvWeather.performClick();
//
//            }//end onSuccess
        });
    }//end onResume

}