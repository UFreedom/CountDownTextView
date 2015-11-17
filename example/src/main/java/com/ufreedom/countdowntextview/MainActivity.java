package com.ufreedom.countdowntextview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ufreedom.CountDownTextView;

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
                
        analogDatas.add(new AnalogData("http://img3.3lian.com/2013/s1/65/d/104.jpg",
                getRandomTime(CountDownTextView.TIME_SHOW_D_H_M_S),
                CountDownTextView.TIME_SHOW_D_H_M_S));
        analogDatas.add(new AnalogData("http://img3.3lian.com/2013/s1/65/d/104.jpg",
                getRandomTime(CountDownTextView.TIME_SHOW_H_M_S),
                CountDownTextView.TIME_SHOW_H_M_S));
        analogDatas.add(new AnalogData("http://img3.3lian.com/2013/s1/65/d/104.jpg",
                getRandomTime(CountDownTextView.TIME_SHOW_M_S),
                CountDownTextView.TIME_SHOW_M_S));
        analogDatas.add(new AnalogData("http://img3.3lian.com/2013/s1/65/d/104.jpg",
                getRandomTime(CountDownTextView.TIME_SHOW_S),
                CountDownTextView.TIME_SHOW_S));

        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(32,0,16,1);
        recyclerView.addItemDecoration(baseItemDecoration);

        analogAdapter = new AnalogAdapter(analogDatas);
        recyclerView.setAdapter(analogAdapter);

    }

    /**
     * Generates random time
     * @return
     */
    private long getRandomTime(int _timeFormat){

        if(random == null) {
            random = new Random();
        }

        long hour = 0;
        long minute = 0;
        long seconds = 0;

        switch (_timeFormat) {
            case CountDownTextView.TIME_SHOW_D_H_M_S:
            case CountDownTextView.TIME_SHOW_H_M_S:
                hour = 1000 * 60 * 60 * (/*random.nextInt(10)*/10 + 1);
            case CountDownTextView.TIME_SHOW_M_S:
                minute = 1000 * 60 * (random.nextInt(10) + 1);
            case CountDownTextView.TIME_SHOW_S:
                seconds = 1000 * (random.nextInt(10) + 1);
                break;
        }

        return SystemClock.elapsedRealtime() + hour + minute + seconds;
    }
    
}
