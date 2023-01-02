package com.example.resturantsnearme.model;

import com.example.resturantsnearme.model.Business;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurants {
    @SerializedName("businesses")
    @Expose
    private List<Business> resturants;

    public List<Business> getResturants() {
        return resturants;
    }

    public void setResturants(List<Business> resturants) {
        this.resturants = resturants;
    }

}
