package com.ufreedom.countdowntextview;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AnalogData> analogDatas =  new ArrayList<>();
        List<String> pics = Arrays.asList(Analog.getPics());
        Random random = new Random();
                
        for (String pic: pics){
            AnalogData  analogData = new AnalogData();
            analogData.pic = pic;
            analogData.schedlueTime = 1000 * 60 * 60 * (random.nextInt(6) + 1) + SystemClock.elapsedRealtime();
            analogDatas.add(analogData);
        }
        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(32,0,16,1);
        recyclerView.addItemDecoration(baseItemDecoration);

        analogAdapter = new AnalogAdapter(analogDatas);
        recyclerView.setAdapter(analogAdapter);

    }
    
}
