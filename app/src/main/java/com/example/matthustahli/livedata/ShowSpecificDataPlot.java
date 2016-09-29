package com.example.matthustahli.livedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by matthustahli on 29/09/16.
 */
public class ShowSpecificDataPlot extends AppCompatActivity {
    public LiveMeasure thisFreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_specific_data_plot);

        //get the intent!
        Intent intent = getIntent();
        thisFreq = initialize(intent);
        String message = String.valueOf(thisFreq.getFrequency());
        TextView textView = (TextView) findViewById(R.id.specificDataPlot_frequency);
        textView.setTextSize(40);
        textView.setText(message);

        ImageView imageView = (ImageView) findViewById(R.id.specificDataPlot_plot);
        imageView.setImageResource(R.drawable.plot);
    }

    //copies values of intent to LiveMeasure element.
    public LiveMeasure initialize(Intent intent){
        return new LiveMeasure(intent.getIntExtra("frequency",0),0,intent.getIntExtra("median",0),intent.getIntExtra("peak",0));
    }

}
