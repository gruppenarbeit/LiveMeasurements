package com.example.matthustahli.livedata;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.gravity;
import static android.R.attr.onClick;


public class LiveDataMainActivity extends AppCompatActivity {

    private List<LiveMeasure> measures = new ArrayList<LiveMeasure>();      //list of cars..
    private ArrayAdapter<LiveMeasure> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_main);

        adapter= new MyListAdapter();

        populateMeasurements();         //this populates the list with data
        populateListView();             // this plots the data to the layout
        registerClickCallback();        //this activates the listener

    }




    private void registerClickCallback() {
        final ListView listView = (ListView) findViewById(R.id.MeasureListView);

        //short click on item- go to specific plot
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override   //display choosen frequency in toast..
            //from here, go to other activity which shows smaller plot..
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveMeasure chosenFrequency = measures.get(position);
                Toast.makeText(LiveDataMainActivity.this, String.valueOf(chosenFrequency.getFrequency()), Toast.LENGTH_SHORT).show();
                //go to new activity
                Intent intent = new Intent(LiveDataMainActivity.this, ShowSpecificDataPlot.class);
                intent.putExtra("frequency" ,chosenFrequency.getFrequency());
                intent.putExtra("median" ,chosenFrequency.getMedian());
                intent.putExtra("peak" ,chosenFrequency.getPeak());
                startActivity(intent);
            }
        });

        //long click on iten- mark to be deleted
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView<?> parent, View view, int position, long id) {

            measures.remove(position);          //take from list
            adapter.notifyDataSetChanged();     //update list
                return true;        //true means, i  have handled the event and it should stop here.. if i put false, it will trigger a normal click when removing my finger.
            }
        });

        //add new element to list by clicking button
        ImageButton imageButton = (ImageButton) findViewById(R.id.add_freq_button);
        imageButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText editText = (EditText) findViewById(R.id.newFreq_textBox);
                String newFreq_string = editText.getText().toString();

                //catch if string empty
                if(newFreq_string.matches("")){
                    // send out toast, input not valid or just dont accept it.
                    Toast toast = Toast.makeText(LiveDataMainActivity.this, "invalid Frequency", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                    return;
                }

                int newFreq_int = Integer.parseInt(newFreq_string);
                //get text and save
                measures.add(new LiveMeasure(newFreq_int, 0,0,0)); //works// get the data in here!!!!!!
                adapter.notifyDataSetChanged();

                //closes keyboard again  (no idea how this works, but it does!!!!)
                InputMethodManager inputManager = (InputMethodManager) LiveDataMainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(LiveDataMainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //clear the textbox and put back the hint
                editText.getText().clear();

                //tell its added- make a toast.
                Toast toast = Toast.makeText(LiveDataMainActivity.this, "ADDED" , Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.DISPLAY_CLIP_HORIZONTAL,0,-300);
                toast.show();
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
            TextView freqText = (TextView) itemView.findViewById(R.id.item_dataFreq);
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
