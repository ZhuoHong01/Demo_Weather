package sg.edu.rp.c346.id21018193.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;

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
}