package com.example.matthustahli.livedata;

/**
 * Created by matthustahli on 28/09/16.
 */
public class LiveMeasure {
    private int frequency;
    private int rawData;
    private int median;
    private int peak;




    //this is my connection to get the data..

    //construct
    public LiveMeasure(int frequency,int rawData, int median, int peak){
        this.frequency = frequency;
        this.rawData = rawData;
        this.median = median;
        this.peak = peak;
    }


  //access to this class
    public int getFrequency(){
        return frequency;
    }
    public int getMedian(){
        return median;
    }
    public int getPeak(){
        return peak;
    }

}
