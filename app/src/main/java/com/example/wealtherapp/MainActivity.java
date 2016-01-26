package com.example.wealtherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {


    Spinner cityspinner;

    // Recycler view is used for binding cardview in it
   static RecyclerView wRecyclerView;

    // getting the string for selected citiy from spinner
    static String citysearch;

    // we bind cardview in weatheradapter
    static WeatherAdapter weatherAdapter;

    //weather class for getting json from weather API
    static Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityspinner = (Spinner) findViewById(R.id.spinner);

        //hard-coded the cities to string array and bind it to spinner
        ArrayAdapter<String> citiArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.cities));
        cityspinner.setAdapter(citiArrayAdapter);

        wRecyclerView = (RecyclerView) findViewById(R.id.wRecyclerView);
        wRecyclerView.setHasFixedSize(true);
        wRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                citysearch = (String) parent.getItemAtPosition(pos);
                Log.i("searchNameeee", citysearch);

                new AsyncTask<Void, Void, Weather>() {
                    @Override
                    protected Weather doInBackground(Void... params) {
                        // getting city string value from spinner and pass it to weather class to get weather information
                        weather = Weather.getCurrentweatherbyciti(citysearch);
                        return weather;
                    }

                    @Override
                    protected void onPostExecute(Weather weather) {
                        // after getting weather info, show this info to cardview using recycler view and weatheradapter
                        weatherAdapter = new WeatherAdapter(weather);
                        wRecyclerView.setAdapter(weatherAdapter);
                        Log.i("Adapter","rrr");

                    }
                }.execute();

            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });


    }

    // method for refreshing weather info when user click on cardview(weather info of android screen)
    //it will call getCurrentweatherbyciti() from weather calss to get updated weather JSON data.
    public static void Refresh()
    {
        new AsyncTask<Void, Void, Weather>()
        {
            @Override
            protected Weather doInBackground(Void... params) {
                weather = Weather.getCurrentweatherbyciti(citysearch);
                return weather;
            }

            @Override
            protected void onPostExecute(Weather weather) {
                // after getting weather info, show this info to cardview using recycler view and weatheradapter
                weatherAdapter = new WeatherAdapter(weather);
                wRecyclerView.setAdapter(weatherAdapter);
                // if fetching weather information is finished, loading of progress bar also stop.
                WeatherAdapter.progressBar.setVisibility(View.GONE);
                Log.i("Adapter","Refresh");

            }

        }.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
