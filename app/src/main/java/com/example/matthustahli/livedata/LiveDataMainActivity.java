package com.example.matthustahli.livedata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class LiveDataMainActivity extends AppCompatActivity {

    private List<LiveMeasure> measures = new ArrayList<LiveMeasure>();      //list of cars..
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_main);

        populateMeasurements();         //this populates the list with data
        populateListView();             // this plots the data to the layout
        registerClickCallback();        //this activates the listener

    }

    private void registerClickCallback() {
        ListView listView = (ListView) findViewById(R.id.MeasureListView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override   //display choosen frequency in toast..
            //from here, go to other activity which shows smaller plot..
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LiveMeasure chosenFrequency = measures.get(position);
                Toast.makeText(LiveDataMainActivity.this, String.valueOf(chosenFrequency.getFrequency()), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    //here is where i get my data in
    private void populateMeasurements(){
        measures.add(new LiveMeasure(1,2,3,4));
        measures.add(new LiveMeasure(5,6,7,4));
        measures.add(new LiveMeasure(1, 2, 3, 4));
        measures.add(new LiveMeasure(3, 2, 5, 4));
        measures.add(new LiveMeasure(1, 2, 34, 4));
        measures.add(new LiveMeasure(19, 2, 30, 4));
    }

    private void populateListView(){
        ArrayAdapter<LiveMeasure> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.MeasureListView);
        list.setAdapter(adapter);
    }

    //inner class.. nur für diese classe LiveDataMain...
    private class MyListAdapter extends ArrayAdapter<LiveMeasure>{

        public MyListAdapter() {
            //super-because i need to call the base class constructor..general constructor to be an instance of this class, and then, we need to tell in witch view we are in..
            //.this gives me the pointer to the class..  then tell how each thing should look like (done with item_view.) and then give the values..
            super(LiveDataMainActivity.this, R.layout.item_view, measures); //as i am in inner class, i have a reverence to my outer class... so i dont need to populate the list...
        }

        @Override   //position in array,
        public View getView(int position, View convertView, ViewGroup parent) {
            //make shure there is a view to work with
            View itemView = convertView;
            if(convertView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);          //??????????
            }

            //HERE I POPULATE THE UI OF THE LIST
            //find measurement to work with. the object at certain position
            LiveMeasure currentMeasure= measures.get(position);

            //Fill the view, connect elements to layout item_view..
            ImageView listImage = (ImageView) itemView.findViewById(R.id.list_icon);    //findview on this specific itemView..is new for every new list layer..
            listImage.setImageResource(R.drawable.wakeboard);

            //Fill the text views
            //set frequency
            TextView freqText = (TextView) itemView.findViewById(R.id.item_txtFreq);
            freqText.setText(String.valueOf(currentMeasure.getFrequency()));

            //set median
            TextView medianText = (TextView) itemView.findViewById(R.id.item_txtMedian);
            medianText.setText(String.valueOf(currentMeasure.getMedian()));

            //set peak
            TextView peakText = (TextView) itemView.findViewById(R.id.item_txtPeak);
            peakText.setText(String.valueOf(currentMeasure.getPeak()));


            return itemView;
        }
    }
}
