
package com.example.resturantsnearme;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantsnearme.model.Business;
import com.example.resturantsnearme.model.Restaurants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private DataAdapter adapter;
    private RecyclerView recyclerView;
    private SeekBar seekBar;
    private int seekBarValue;
    private TextView distIndicator, msg;
    private DataManipulation dataManipulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        seekBar = findViewById(R.id.seekBar);
        distIndicator = findViewById(R.id.dist_indicator);
        msg= findViewById(R.id.msg);
        display();
        onChangeRadius();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

    }
    //implementing seekbarchange listener and setting the textview
    public void onChangeRadius() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i < 100) {
                    i = 100;
                }
                seekBarValue = i;
                if (i < 1000) {
                    distIndicator.setText(String.format("%sM", i));
                } else {
                    distIndicator.setText(String.format("%sKM", (float) i / 1000));
                }
                display();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void display() {
        //Creating retrofit
        String url = "https://api.yelp.com/v3/businesses/";
        String location = "USA";
        String key = "Bearer XPFgzKwZGK1yqRxHi0d5xsARFOLpXIvccQj5jekqTnysweGyoIfVUHcH2tPfGq5Oc9kwKHPkcOjk2d1Xobn7aTjOFeop8x41IUfVvg2Y27KiINjYPADcE7Qza0RkX3Yx";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Data data = retrofit.create(Data.class);
        //calling the method to get the data
        Call<Restaurants> call = data.getData(location, key);
        call.enqueue(new Callback<Restaurants>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<Restaurants> call, @NonNull Response<Restaurants> response) {

                Log.d("Status", "Data received");
                if (response.body() != null) {
                    Log.d("Message", "Response is not null");
                    List<Business> result = response.body().getResturants();
                    //sending data to DataManipulation class for manipulation as per the radius selected.
                    dataManipulation = new DataManipulation(seekBarValue, (ArrayList<Business>) result);
                    adapter = new DataAdapter(dataManipulation.fetchData());
                    if(dataManipulation.fetchData().size()!=0){
                        msg.setVisibility(View.GONE);
                        Log.d("Under if", "Hi");
                    }
                    else{
                        msg.setVisibility(View.VISIBLE);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Restaurants> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Could not fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}