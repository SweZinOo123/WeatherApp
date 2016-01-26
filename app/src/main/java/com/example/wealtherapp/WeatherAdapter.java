package com.example.wealtherapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by tagitdev on 15/1/2016.
 */
// weather adapter is used for showing weather information on cardview
class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CardViewHolder> {

   // progress bar is for loading of fetching weather information when user click on weather information of android screen(card view)
   // for updated information of weather so that user know the view is getting updated information
    static public ProgressBar progressBar;
    static Weather weather;
    public WeatherAdapter(Weather weather) {
        this.weather = weather;

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //setting the card_view layout for showing weather info.
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        //bind the card_view layout to carview holder.
        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int position) {
        //set each of weather information to cardview from Weather class after getting Weather JSON data which run in background using AsyncTask
        Picasso.with(cardViewHolder.imageView.getContext())
                .load(weather.get("weathericon"))
                .fit()
                .error(R.drawable.placeholder_card_view)
                .placeholder(R.drawable.placeholder_card_view)
                .into(cardViewHolder.imageView);
        cardViewHolder.tempC.setText(weather.get("temp_C"));
        cardViewHolder.tempF.setText(weather.get("temp_F"));
        cardViewHolder.weatherdesc.setText(weather.get("weatherdesc"));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView tempC,tempF,weatherdesc;

        public CardViewHolder(RelativeLayout itemView) {
            super(itemView);
            //declaring components of cardview
            imageView = (ImageView) itemView.findViewById(R.id.weathericon);
            tempC      = (TextView)  itemView.findViewById(R.id.tempC);
            tempF      = (TextView)  itemView.findViewById(R.id.tempF);
            weatherdesc      = (TextView)  itemView.findViewById(R.id.weatherdesc);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
            //set visible of progress to false unitl user click cardview which display weather info: on android screen.
            progressBar.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        // when user click on cardview which display weather information of Android screen, Refresh() method from MainActivty is called to run
        // getCurrentweatherbyciti() from Weather class agian for updated weather infromation.
        @Override
        public void onClick(View v)
        {
            // loading of progress bar start.
            load(itemView);
            MainActivity.Refresh();
        }

        //this method is for when user click on weather information of android screen, loading of progress bar starts.
        public void load(View view){
            Log.i("calll", "loadd");
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
