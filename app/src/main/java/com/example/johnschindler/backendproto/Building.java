package com.example.johnschindler.backendproto;

import java.util.ArrayList;

/**
 * Created by johnschindler on 2/28/15.
 */
public class Building {

    private String mName;
    private ArrayList<FloorPlan> mFloorPlans = new ArrayList<FloorPlan>();

    public Building(String name, ArrayList<FloorPlan> floorPlans ){

        mName = name;
        mFloorPlans = floorPlans;

    }

    public Building(String name){
        mName = name;
    }






}
