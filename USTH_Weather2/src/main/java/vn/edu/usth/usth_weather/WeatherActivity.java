package vn.edu.usth.usth_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import org.w3c.dom.Text;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("onCreate", "weather activity is being created");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i ("onStart", "weather activity is starting");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i ("onStart", "weather activity is stopping");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i ("onDestroy", "weather activity is destroyed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i ("onResume", "weather activity is resuming");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i( "onPause", "weather activity is pausing");
    }
}
