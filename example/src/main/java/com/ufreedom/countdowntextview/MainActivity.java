package com.ufreedom.countdowntextview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnalogAdapter analogAdapter;
    private Random random;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Tool Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        // Set RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AnalogData> analogDatas = new ArrayList<>();
        List<String> pics = Arrays.asList(Analog.getPics());
                
        for (String pic: pics){
            analogDatas.add(new AnalogData(pic, getRandomTime()));
        }
        
        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(32,0,16,1);
        recyclerView.addItemDecoration(baseItemDecoration);

        analogAdapter = new AnalogAdapter(analogDatas);
        recyclerView.setAdapter(analogAdapter);

    }

    /**
     * Generates random time
     * @return
     */
    private long getRandomTime(){

        if(random == null) {
            random = new Random();
        }

        long seconds = 1000 * (random.nextInt(10) + 1);
        long minute = 1000 * 60 * (random.nextInt(10) + 1); 
        long hour = 1000 * 60 * 60 * (/*random.nextInt(10)*/10 + 1);

        return SystemClock.elapsedRealtime() + hour + minute + seconds;
    }
    
}
