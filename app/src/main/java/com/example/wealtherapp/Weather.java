package com.example.wealtherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tagitdev on 15/1/2016.
 */
//Weather class is used for getting Weather JSON data and pass it to view to display weather information.
public class Weather extends HashMap<String, String> implements Serializable {

    // used baseurl of local weather API to get weather JSON data
    static final String baseurl = "http://api.worldweatheronline.com/free/v2/weather.ashx?key=59b686cf6cd7a0ca96491452f869a&tp=3&format=json&q=";


    public Weather(String temp_C, String temp_F, String weatherdesc, String weathericon) {
        put("temp_C", temp_C);
        put("temp_F", temp_F);
        put("weatherdesc", weatherdesc);
        put("weathericon", weathericon);
    }

    // this method is used for fetching weather JSON data using weather API base url which support GET method by accepting city name
    // and return weather JSON data of particular city name and add it to Weather class
    public static Weather getCurrentweatherbyciti(String cityname) {

        Weather weather = null;
        try {
            //JSON parser is converted string into JSOn object or JSON array
            JSONObject jsonobject = JSONParser.getJSONFromUrl(baseurl+cityname);

            JSONObject data = jsonobject.getJSONObject("data");
            JSONArray  currentcondition = data.getJSONArray("current_condition");
            JSONObject currentconditionobject = currentcondition.getJSONObject(0);
            String temp_C = currentconditionobject.getString("temp_C");
            String temp_F = currentconditionobject.getString("temp_F");
            JSONArray weatherdesc = currentconditionobject.getJSONArray("weatherDesc");
            JSONObject weathervalobj = weatherdesc.getJSONObject(0);
            String weatherval = weathervalobj.getString("value");

            JSONArray weathericonurl = currentconditionobject.getJSONArray("weatherIconUrl");
            JSONObject weathericonobj = weathericonurl.getJSONObject(0);
            String weathericon = weathericonobj.getString("value");

            weather = new Weather(temp_C,temp_F,weatherval,weathericon);



        } catch (Exception e) {
            Log.e("list", "JSON error");
        }
        return weather;
    }



}