package com.example.resturantsnearme;

import com.example.resturantsnearme.model.Business;

import java.util.ArrayList;

public class DataManipulation {
    int seekBarValue;
    ArrayList<Business> al1, al2;
    int distance;

    public DataManipulation(int seekBarValue, ArrayList<Business> al) {
        this.seekBarValue = seekBarValue;
        this.al1 = al;
    }
    //Manipulating the data as per radius selected.
    public ArrayList<Business> fetchData() {
        al2 = new ArrayList<>();
        for (int i = 0; i < al1.size(); i++) {
            distance = (int) Math.round(al1.get(i).getDistance());
            if (distance <= seekBarValue) {
                al2.add(al1.get(i));
            }
        }
        return al2;
    }

}
